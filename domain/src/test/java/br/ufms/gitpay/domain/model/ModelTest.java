package br.ufms.gitpay.domain.model;

import br.ufms.gitpay.domain.model.banco.Banco;
import br.ufms.gitpay.domain.model.conta.*;
import br.ufms.gitpay.domain.model.transacao.Deposito;
import br.ufms.gitpay.domain.model.transacao.Pix;
import br.ufms.gitpay.domain.model.transacao.Saque;
import br.ufms.gitpay.domain.model.transacao.Transferencia;
import br.ufms.gitpay.domain.model.usuario.Pessoa;
import br.ufms.gitpay.domain.model.usuario.PessoaFisica;
import br.ufms.gitpay.domain.model.usuario.PessoaJuridica;
import br.ufms.gitpay.domain.model.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ModelTest {

    private static void testarEntradaInvalida(Executable executable) {
        Throwable thrown = Assertions.assertThrows(Throwable.class, executable);
        if (!(thrown instanceof NullPointerException || thrown instanceof IllegalArgumentException)) {
            Assertions.fail("Lançou uma exceção inesperada: " + thrown);
        }
    }

    private static void testarEntradaValida(Executable executable) {
        Assertions.assertDoesNotThrow(executable);
    }

    private static ContaBancaria criarContaExterna() {
        return new ContaExterna(TipoConta.CONTA_CORRENTE, "001", 552, 16942, 0,
                "Kleber Kruger", "02135730165");
    }

    private static Usuario<? extends Pessoa> criarUsuario() {
        return Usuario.builder()
                .setNome("Kleber Kruger")
                .setContato("67996122809", "kleberkruger@gmail.com")
                .setSenha("123456")
                .buildPessoaFisica("02135730165", LocalDate.parse("1988-12-08"));
    }

    private static ContaBancaria criarContaGitPay() {
        return ContaGitPay.build()
                .setUsuario(criarUsuario())
                .criadaEm(1, LocalDateTime.now())
                .build();
    }

    @Test
    public void testarNumeroConta() {
        testarEntradaInvalida(() -> new NumeroConta(null));
        testarEntradaInvalida(() -> new NumeroConta(""));
        testarEntradaInvalida(() -> new NumeroConta("16942"));
        testarEntradaInvalida(() -> new NumeroConta("16942 0"));
        testarEntradaInvalida(() -> new NumeroConta("16942 0 1"));
        testarEntradaInvalida(() -> NumeroConta.validar("169421"));
        testarEntradaInvalida(() -> NumeroConta.validar("16942-1"));
        testarEntradaInvalida(() -> NumeroConta.validar(16942, 1));

        testarEntradaValida(() -> new NumeroConta("16942-0"));
        testarEntradaValida(() -> new NumeroConta("16942-1"));
        testarEntradaValida(() -> new NumeroConta(16942, 0));
        testarEntradaValida(() -> new NumeroConta(16942, 1));
        testarEntradaValida(() -> new NumeroConta(1));
        testarEntradaValida(() -> NumeroConta.validar("16942-0"));
        testarEntradaValida(() -> NumeroConta.validar("25701-0"));
    }

    @Test
    public void testarBanco() {
        testarEntradaValida(() -> new Banco(666, "GitPay", "GitPay Pagamentos S.I."));
    }

    @Test
    public void testarContaExterna() {
        testarEntradaInvalida(() -> new ContaExterna(null, null, 552, 16942, 0,
                "Kleber Kruger", "02135730165"));
        testarEntradaInvalida(() -> new ContaExterna(null, "001", 552, 16942, 0,
                "Kleber Kruger", "02135730165"));
        testarEntradaInvalida(() -> new ContaExterna(TipoConta.CONTA_CORRENTE, null, 552, "16942-0",
                "Kleber Kruger", "02135730165"));
        testarEntradaInvalida(() -> new ContaExterna(TipoConta.CONTA_CORRENTE, "k16", 552, 16942, 0,
                "Kleber Kruger", "02135730165"));
        testarEntradaInvalida(() -> new ContaExterna(TipoConta.CONTA_CORRENTE, "001", 100000, 16942, 0,
                "Kleber Kruger", "02135730165"));
        testarEntradaInvalida(() -> new ContaExterna(TipoConta.CONTA_CORRENTE, "001", 552, 100000000, 0,
                "Kleber Kruger", "02135730165"));
        testarEntradaInvalida(() -> new ContaExterna(TipoConta.CONTA_CORRENTE, "001", 552, "16942-x",
                "Kleber Kruger", "02135730165"));
        testarEntradaInvalida(() -> new ContaExterna(TipoConta.CONTA_CORRENTE, "001", 552, "16942-x",
                "Kleber", "02135730165"));
        testarEntradaInvalida(() -> new ContaExterna(TipoConta.CONTA_CORRENTE, "001", 552, "16942-0",
                "Kleber", "02135730165"));
        testarEntradaInvalida(() -> new ContaExterna(TipoConta.CONTA_CORRENTE, "001", 552, "16942-0",
                "Kleber Kruger", "02135730100"));


        testarEntradaValida(() -> new ContaExterna(TipoConta.CONTA_CORRENTE, "001", 552, 16942, 0,
                "Kleber Kruger", "02135730165"));
        testarEntradaValida(() -> new ContaExterna(TipoConta.CONTA_CORRENTE, "001", 552, "16942-0",
                "Kleber Kruger", "02135730165"));
        testarEntradaValida(() -> new ContaExterna(TipoConta.CONTA_POUPANCA, "001", 552, 16942, 0,
                "Kleber Kruger", "02135730165"));

        testarEntradaValida(ModelTest::criarContaExterna);
    }

    @Test
    public void testarContaGitPay() {

        Usuario<? extends Pessoa> usuario = criarUsuario();
        testarEntradaValida(() -> new ContaGitPay(usuario));
        testarEntradaValida(() -> new ContaGitPay(1, usuario, LocalDateTime.now()));
        testarEntradaValida(() -> ContaGitPay.build()
                .setUsuario(usuario)
                .criadaEm(1, LocalDateTime.now())
        );

        testarEntradaValida(ModelTest::criarContaGitPay);
    }

    @Test
    public void testarUsuario() {
        testarEntradaValida(() -> new Usuario<>(
                new PessoaFisica("Kleber Kruger", "02135730165", "67996122809",
                        "kleberkruger@gmail.com", LocalDate.parse("1988-12-08")
                ), "123456", LocalDateTime.now()));

        testarEntradaValida(() -> new Usuario<>(
                new PessoaJuridica("Universidade Federal de Mato Grosso do Sul",
                        "Fundação Universidade Federal de Mato Grosso do Sul",
                        "15461510000133", "6733457000", "reitoria@ufms.br"
                ), "123456", LocalDateTime.now()));

        testarEntradaValida(() -> Usuario.builder()
                .setNome("Kleber Kruger")
                .setContato("67996122809", "kleberkruger@gmail.com")
                .setSenha("123456")
                .buildPessoaFisica("02135730165", LocalDate.parse("1988-12-08")));

        testarEntradaValida(() -> Usuario.builder()
                .setNome("Kleber Kruger")
                .setContato("67996122809", "kleberkruger@gmail.com")
                .setSenha("123456")
                .cadastradoEm(LocalDateTime.now())
                .buildPessoaFisica("02135730165", LocalDate.parse("1988-12-08")));

        testarEntradaValida(() -> Usuario.builder()
                .setNome("Universidade Federal de Mato Grosso do Sul")
                .setContato("6733457000", "reitoria@ufms.br")
                .setSenha("123456")
                .cadastradoEm(LocalDateTime.now())
                .buildPessoaJuridica("15461510000133", "Fundação Universidade Federal de Mato Grosso do Sul"));
    }

    @Test
    public void testarDeposito() {
        testarEntradaValida(() -> new Deposito(criarContaGitPay(), 500.00));
        testarEntradaValida(() -> new Deposito("abcdefghijk", criarContaGitPay(), 500.00, LocalDateTime.now()));
    }

    @Test
    public void testarSaque() {
        testarEntradaValida(() -> new Saque(criarContaGitPay(), 500.00));
        testarEntradaValida(() -> new Saque("abcdefghijk", criarContaGitPay(), 500.00, LocalDateTime.now()));
    }

    @Test
    public void testarTransferencia() {
        testarEntradaValida(() -> new Transferencia(criarContaGitPay(), criarContaExterna(), 500.00));
        testarEntradaValida(() -> new Transferencia("abcdefghijk", criarContaGitPay(), criarContaExterna(),
                500.00, LocalDateTime.now()));
    }

    @Test
    public void testarPix() {
        testarEntradaValida(() -> new Pix(criarContaGitPay(), criarContaExterna(), 500.00));
        testarEntradaValida(() -> new Pix("abcdefghijk", criarContaGitPay(), criarContaExterna(),
                500.00, LocalDateTime.now()));
    }

    @Test
    public void testarInvestimento() {

    }
}
