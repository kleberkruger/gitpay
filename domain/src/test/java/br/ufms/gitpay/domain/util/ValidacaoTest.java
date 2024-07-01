package br.ufms.gitpay.domain.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

public class ValidacaoTest {

    private static void testarEntradaInvalida(Executable executable) {
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    private static void testarEntradaValida(Executable executable) {
        Assertions.assertDoesNotThrow(executable);
    }

    @Test
    public void testarNomePessoa() {
        testarEntradaInvalida(() -> Validar.nomePessoa(null));
        testarEntradaInvalida(() -> Validar.nomePessoa(""));
        testarEntradaInvalida(() -> Validar.nomePessoa("      "));
        testarEntradaInvalida(() -> Validar.nomePessoa("ab"));
        testarEntradaInvalida(() -> Validar.nomePessoa("Abcdefghijklmnopqrstuvwxyz Abcdefghijklmnopqrstuvwxyz"));
        testarEntradaInvalida(() -> Validar.nomePessoa("Kleber 1"));
        testarEntradaInvalida(() -> Validar.nomePessoa("Kleber_"));
        testarEntradaInvalida(() -> Validar.nomePessoa("Kleber ?", false));
        testarEntradaInvalida(() -> Validar.nomePessoa("Kleber !", true));
        testarEntradaInvalida(() -> Validar.nomePessoa("Kleber", true));

        testarEntradaValida(() -> Validar.nomePessoa("Kleber"));
        testarEntradaValida(() -> Validar.nomePessoa("Jean-Claude Van Damme"));
        testarEntradaValida(() -> Validar.nomePessoa("Kleber", false));
        testarEntradaValida(() -> Validar.nomePessoa("Kleber Kruger", true));

        System.out.printf("[%s]\n", Validar.nomePessoa("  Kleber Kruger "));
    }

    @Test
    public void nomeEmpresa() {
        testarEntradaInvalida(() -> Validar.nomeEmpresa(null));
        testarEntradaInvalida(() -> Validar.nomeEmpresa(""));
        testarEntradaInvalida(() -> Validar.nomeEmpresa("      "));

        testarEntradaValida(() -> Validar.nomeEmpresa("?"));
        testarEntradaValida(() -> Validar.nomeEmpresa("GitPay"));
    }

    @Test
    public void razaoSocial() {
        testarEntradaInvalida(() -> Validar.razaoSocial(null));
        testarEntradaInvalida(() -> Validar.razaoSocial(""));
        testarEntradaInvalida(() -> Validar.razaoSocial("   "));

        testarEntradaValida(() -> Validar.razaoSocial("GitPay Pagamentos S.I."));
        testarEntradaValida(() -> Validar.razaoSocial(null, false));
//        testarEntradaValida(() -> Validar.razaoSocial("", false));
    }

    @Test
    public void telefone() {
        testarEntradaValida(() -> Validar.telefone("67996122809"));
    }

    @Test
    public void email() {
        testarEntradaValida(() -> Validar.email("kleberkruger@gmail.com"));
    }

    @Test
    public void usuario() {
        testarEntradaValida(() -> Validar.usuario("kleber_kruger"));
    }

    @Test
    public void senha() {
        testarEntradaValida(() -> Validar.senha("123456"));
    }

    @Test
    public void dataNascimento() {
        testarEntradaValida(() -> Validar.dataNascimento(LocalDate.parse("1988-12-08")));
    }

    @Test
    public void cpf() {
        testarEntradaValida(() -> Validar.cpf("02135730165"));
    }

    @Test
    public void cnpj() {
        testarEntradaInvalida(() -> Validar.cnpj("02135730165"));
    }
}
