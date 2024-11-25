package br.ufms.gitpay.data.file;

import br.ufms.gitpay.data.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public abstract class FileRepository<Entity, Id extends Serializable> implements Repository<Entity, Id>, AutoCloseable {

    private final Path repositoryPath;
    protected final Map<Id, Entity> repository;

    public FileRepository(Path path) {
        this.repositoryPath = path;
        this.repository = load();
    }

    protected Path getRepositoryPath() throws IOException {
        Files.createDirectories(repositoryPath.getParent());
        return repositoryPath;
    }

    protected Map<Id, Entity> load() {
        Map<Id, Entity> map = new HashMap<>();
        if (Files.exists(repositoryPath)) {
            try (BufferedReader reader = Files.newBufferedReader(getRepositoryPath())) {
                while (reader.ready()) {
                    Entity e = textToEntity(reader.readLine());
                    map.put(getId(e), e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    protected void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(getRepositoryPath(),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (var entity : repository.values()) {
                writer.write(entityToText(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        save();
    }

    @Override
    public CompletableFuture<Entity> save(Entity entity) {
        repository.put(getId(entity), entity);
        save();
        return CompletableFuture.completedFuture(entity);
    }

    @Override
    public CompletableFuture<Void> delete(Id id) {
        repository.remove(id);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Optional<Entity>> get(Id id) {
        return CompletableFuture.completedFuture(Optional.ofNullable(repository.get(id)));
    }

    @Override
    public CompletableFuture<Collection<Entity>> getAll() {
        return CompletableFuture.completedFuture(repository.values());
    }

    protected abstract Id getId(Entity entity);

    protected abstract String entityToText(Entity entity);

    protected abstract Entity textToEntity(String line);
}
