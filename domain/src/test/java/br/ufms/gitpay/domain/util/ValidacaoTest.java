package br.ufms.gitpay.domain.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ValidacaoTest {

    @Test
    public void nomePessoa() {
        Validar.nomePessoa("Kleber Kruger", true);
    }

    @Test
    public void nomeEmpresa() {
        Validar.nomeEmpresa("GitPay");
    }

    @Test
    public void razaoSocial() {
        Validar.razaoSocial("GitPay Pagamentos S.I.");
    }

    @Test
    public void telefone() {
        Validar.telefone("67996122809");
    }

    @Test
    public void email() {
        Validar.email("kleberkruger@gmail.com");
    }

    @Test
    public void usuario() {
        Validar.usuario("kleber_kruger");
    }

    @Test
    public void senha() {
        Validar.senha("123456");
    }

    @Test
    public void dataNascimento() {
        Validar.dataNascimento(LocalDate.parse("1988-12-08"));
    }

    @Test
    public void cpf() {
        Validar.cpf("02135730165");
    }

    @Test
    public void cnpj() {

    }
}
