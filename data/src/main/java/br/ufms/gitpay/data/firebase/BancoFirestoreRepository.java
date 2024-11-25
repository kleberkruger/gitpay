package br.ufms.gitpay.data.firebase;

import br.ufms.gitpay.data.BancoRepository;
import br.ufms.gitpay.domain.model.banco.Banco;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class BancoFirestoreRepository extends FirestoreRepository<Banco, String> implements BancoRepository {

    public BancoFirestoreRepository() {
        super(FiresforeRefs.getBancoCollection());
    }

    @Override
    protected Optional<String> getId(Banco banco) {
        return Optional.of(banco.getCodigo());
    }

    @Override
    protected Map<String, Object> entityToMap(Banco banco) {
        Map<String, Object> data = new HashMap<>();
        data.put("codigo", banco.getCodigo());
        data.put("nome", banco.getNome());
        data.put("razaoSocial", banco.getRazaoSocial());
        return data;
    }

    @Override
    protected CompletableFuture<Banco> documentToEntity(DocumentSnapshot doc) {
        return CompletableFuture.completedFuture(new Banco(Integer.parseInt(Objects.requireNonNull(
                doc.getString("codigo"))), doc.getString("nome"), doc.getString("razaoSocial"))
        );
    }
}
