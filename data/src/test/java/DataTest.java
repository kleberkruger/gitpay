import br.ufms.gitpay.data.BancoRepository;
import br.ufms.gitpay.data.Repository;
import br.ufms.gitpay.data.firebase.BancoFirestoreRepository;
import br.ufms.gitpay.domain.model.banco.Banco;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.function.Function;

public class DataTest {

    public <Entity, Id extends Serializable> void add(Repository<Entity, Id> repository, Entity entity,
                                                      Function<Entity, String> printer) {
        repository.save(entity)
                .thenAccept(e -> System.out.println("save (add): " + printer.apply(e) + " inserido com sucesso."))
                .exceptionally(ex -> {
                    System.err.println("save: " + ex.getMessage());
                    return null;
                }).join();
    }

    public <Entity, Id extends Serializable> void update(Repository<Entity, Id> repository, Entity entity,
                                                         Function<Entity, String> printer) {
        repository.save(entity)
                .thenAccept(e -> System.out.println("save (update): " + printer.apply(e) + " atualizado com sucesso."))
                .exceptionally(ex -> {
                    System.err.println("save: " + ex.getMessage());
                    return null;
                }).join();
    }

    public <Entity, Id extends Serializable> void get(Repository<Entity, Id> repository, Id id,
                                                      Function<Entity, String> printer) {
        repository.get(id)
                .thenAccept(opt -> opt.ifPresentOrElse(e -> System.out.println("get: " + printer.apply(e) + " encontrado."),
                        () -> System.out.println("get: " + id + " objeto não encontrado.")))
                .exceptionally(ex -> {
                    System.err.println("get:\n" + ex.getMessage());
                    return null;
                }).join();
    }

    public <Entity, Id extends Serializable> void getAll(Repository<Entity, Id> repository,
                                                         Function<Entity, String> printer) {
        repository.getAll()
                .thenAccept(list -> {
                    System.out.print("getAll: [");
                    var elements = list.stream().toList();
                    for (int i = 0; i < elements.size(); i++) {
                        System.out.printf("%s%s", i > 0 ? ", " : "", printer.apply(elements.get(i)));
                    }
                    System.out.println("]");
                })
                .exceptionally(ex -> {
                    System.err.println("getAll: " + ex.getMessage());
                    return null;
                }).join();
    }

    public <Entity, Id extends Serializable> void delete(Repository<Entity, Id> repository, Id id) {
        repository.delete(id)
                .thenAccept(e -> System.out.println("delete: " + id + " deletado com sucesso."))
                .exceptionally(ex -> {
                    System.err.println("delete: " + ex.getMessage());
                    return null;
                }).join();
    }

    @Test
    public void bancoCRUD() {
        Banco entity = new Banco("07", "Banco do Zero Zero Sete!", "Banco 007 S.A.");
        BancoRepository repository = new BancoFirestoreRepository();

        add(repository, entity, Banco::getNome);
        get(repository, "007", Banco::getNome);
        get(repository, "7", Banco::getNome);
        getAll(repository, Banco::getNome);
        delete(repository, "7");
    }

    @Test
    public void usuarioPessoaFisicaCRUD() {
    }

    @Test
    public void usuarioPessoaJuridicaCRUD() {
    }

    @Test
    public void contaExternaCRUD() {
    }

    @Test
    public void contaGitPayCRUD() {
    }

    @Test
    public void depositoCRUD() {
    }

    @Test
    public void saqueCRUD() {
    }

    @Test
    public void transferenciaCRUD() {
    }

    @Test
    public void pixCRUD() {
    }

    @Test
    public void chavePixCRUD() {
    }
}
