package br.ufms.gitpay.data;

import br.ufms.gitpay.data.file.BancoFileRepository;
import br.ufms.gitpay.domain.model.banco.Banco;

public class DataMain {

    public static void main(String[] args) {
        try (var bancoRepository = new BancoFileRepository()) {
            Banco b = new Banco("07", "Kleber Banco", "Kleber S.A.");
            System.out.println(b.getCodigo());
            bancoRepository.save(Banco.GitPay);
            bancoRepository.getAll().thenAccept(bancos -> {
                for (Banco banco : bancos) {
                    System.out.println(banco.getNome());
                }
            });
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
