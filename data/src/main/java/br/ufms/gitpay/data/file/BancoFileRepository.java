package br.ufms.gitpay.data.file;

import br.ufms.gitpay.data.BancoRepository;
import br.ufms.gitpay.domain.model.banco.Banco;

import java.nio.file.Paths;

public class BancoFileRepository extends FileRepository<Banco, String> implements BancoRepository {

    public BancoFileRepository() {
        super(Paths.get(System.getProperty("user.home"), ".gitpay", "bancos.database"));
    }

    @Override
    protected String getId(Banco banco) {
        return banco.getCodigo();
    }

    @Override
    protected String entityToText(Banco banco) {
        return String.format("%s;%s;%s", banco.getCodigo(), banco.getNome(), banco.getRazaoSocial());
    }

    @Override
    protected Banco textToEntity(String line) {
        String[] fields = line.split(";");
        return new Banco(fields[0], fields[1], fields[2]);
    }
}
