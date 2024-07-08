package br.ufms.gitpay.domain;

import br.ufms.gitpay.domain.model.usuario.PessoaFisica;
import br.ufms.gitpay.domain.model.usuario.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DoMain {

    public static void main(String[] args) {
        try {
            Usuario<PessoaFisica> usuario = new Usuario<>(new PessoaFisica(
                    "Kleber Kruger", "02135730165", "67996122809", "kleberkruger@gmail.com",
                    LocalDate.parse("1988-08-12")),
                    "123456");
            System.out.println(usuario.getDados().getNome());

            var pf = Usuario.builder()
                    .setNome("Kleber Kruger")
                    .setContato("67996122809", "kleberkruger@gmail.com")
                    .setSenha("123456")
                    .cadastradoEm(LocalDateTime.now())
                    .buildPessoaFisica("02135730165", LocalDate.parse("1988-08-12"));

            System.out.println(pf.getDados().getNome());

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
