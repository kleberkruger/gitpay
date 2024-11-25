package br.ufms.gitpay.data.firebase;

import br.ufms.gitpay.data.Repository;
import br.ufms.gitpay.data.firebase.config.FirebaseConfig;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public abstract class FirestoreRepository<Entity, Id extends Serializable> implements Repository<Entity, Id> {

    protected static final Firestore db = FirebaseConfig.getFirestore();

    protected final CollectionReference collection;

    protected FirestoreRepository(CollectionReference collectionRef) {
        this.collection = collectionRef;
    }

    private CompletableFuture<Entity> add(Entity entity) {
        return toCompletableFuture(collection.add(entityToMap(entity)))
                .thenCompose(docRef -> toCompletableFuture(docRef.get())
                        .thenCompose(this::documentToEntity));
    }

    private CompletableFuture<Entity> update(String id, Entity entity) {
        return toCompletableFuture(collection.document(id).set(entityToMap(entity)))
//                .thenApply(r -> entity);
                .thenCompose(r -> toCompletableFuture(collection.document(id).get())
                        .thenCompose(this::documentToEntity));
    }

    @Override
    public CompletableFuture<Entity> save(Entity entity) {
        var id = getId(entity);
        return id.isEmpty() ? add(entity) : update(id.get(), entity);
    }

    protected DocumentReference save(Transaction transaction, Entity entity) {
        var id = getId(entity);
        var entityRef = id.map(collection::document).orElseGet(collection::document);
        transaction.create(entityRef, entityToMap(entity));
        return entityRef;
    }

    @Override
    public CompletableFuture<Void> delete(Id id) {
        return toCompletableFuture(collection.document(idToStr(id)).delete())
                .thenApply(writeResult -> null);
    }

    protected void delete(Transaction transaction, Id id) {
        transaction.delete(collection.document(idToStr(id)));
    }

    protected CompletableFuture<Optional<Entity>> get(DocumentReference docRef) {
        return toCompletableFuture(docRef.get()).thenCompose(document -> document.exists() ?
                documentToEntity(document).thenApply(Optional::of) :
                CompletableFuture.completedFuture(Optional.empty()));
    }

    @Override
    public CompletableFuture<Optional<Entity>> get(Id id) {
        return get(collection.document(idToStr(id)));
    }

    @Override
    public CompletableFuture<Collection<Entity>> getAll() {
        return toCompletableFuture(collection.get())
                .thenCompose(queryDocumentSnapshots -> {
                    List<CompletableFuture<Entity>> futures = queryDocumentSnapshots.getDocuments().stream()
                            .map(this::documentToEntity)
                            .toList();
                    return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                            .thenApply(v -> futures.stream()
                                    .map(CompletableFuture::join)
                                    .collect(Collectors.toList()));
                });
    }

    protected <T> CompletableFuture<T> toCompletableFuture(ApiFuture<T> apiFuture) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        ApiFutures.addCallback(apiFuture, new ApiFutureCallback<>() {
            @Override
            public void onSuccess(T result) {
                completableFuture.complete(result);
            }

            @Override
            public void onFailure(Throwable t) {
                completableFuture.completeExceptionally(t);
            }
        }, ForkJoinPool.commonPool());
        return completableFuture;
    }

    protected abstract Optional<String> getId(Entity entity);

    protected String idToStr(Id id) {
        return String.valueOf(id);
    }

    protected abstract Map<String, Object> entityToMap(Entity entity);

    protected abstract CompletableFuture<Entity> documentToEntity(DocumentSnapshot document);
}
