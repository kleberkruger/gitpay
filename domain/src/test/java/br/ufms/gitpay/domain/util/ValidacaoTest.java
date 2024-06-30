package br.ufms.gitpay.domain.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.util.function.Function;

public class ValidacaoTest {

    private static void testarEntradaInvalida(Executable executable) {
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    private static void testarEntradaValida(Executable executable) {
        Assertions.assertDoesNotThrow(executable);
    }

    private static void testarValor(String valorEsperado, Function<String, String> executable) {
//        Assertions.assertEquals(valorEsperado, executable.apply());
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
        testarEntradaInvalida(() -> Validar.nomePessoa("  Kleber  ", true));

        testarEntradaValida(() -> Validar.nomePessoa("Kleber"));
        testarEntradaValida(() -> Validar.nomePessoa("Jean-Claude Van Damme"));
        testarEntradaValida(() -> Validar.nomePessoa("Kleber", false));
        testarEntradaValida(() -> Validar.nomePessoa("Kleber ", false));
        testarEntradaValida(() -> Validar.nomePessoa(" Kleber", false));
        testarEntradaValida(() -> Validar.nomePessoa(" Kleber ", false));
        testarEntradaValida(() -> Validar.nomePessoa("Kleber Kruger", true));
        testarEntradaValida(() -> Validar.nomePessoa("   Kleber   Kruger  ", true));

        Assertions.assertEquals("Kleber", Validar.nomePessoa("  Kleber    "));
        Assertions.assertEquals("Kleber Kruger", Validar.nomePessoa("  Kleber   Kruger "));
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
        testarEntradaInvalida(() -> Validar.razaoSocial("   "));
        testarEntradaInvalida(() -> Validar.razaoSocial("", true));
        testarEntradaInvalida(() -> Validar.razaoSocial("A", false));
        testarEntradaInvalida(() -> Validar.razaoSocial("A", true));

        testarEntradaValida(() -> Validar.razaoSocial(null, false));
        testarEntradaValida(() -> Validar.razaoSocial("", false));
        testarEntradaValida(() -> Validar.razaoSocial("GitPay Pagamentos S.I."));

        Assertions.assertEquals("", Validar.razaoSocial(null, false));
    }

    @Test
    public void telefone() {
        testarEntradaInvalida(() -> Validar.telefone("99312356789"));
        testarEntradaInvalida(() -> Validar.telefone("999123456789"));
        testarEntradaInvalida(() -> Validar.telefone("(99) 3123-56789"));
        testarEntradaInvalida(() -> Validar.telefone("(99) 91234-56789"));

        testarEntradaValida(() -> Validar.telefone("6732910200"));
        testarEntradaValida(() -> Validar.telefone("67996122809"));
        testarEntradaValida(() -> Validar.telefone("  67996122809 "));
        testarEntradaValida(() -> Validar.telefone(" (67) 3291-0200   "));
        testarEntradaValida(() -> Validar.telefone("  (67) 99612-2809 "));
        testarEntradaValida(() -> Validar.telefone("(99) 3291-0200"));
        testarEntradaValida(() -> Validar.telefone("(99) 91234-5678"));
        testarEntradaValida(() -> Validar.telefone("+55 67 3291-0200"));
        testarEntradaValida(() -> Validar.telefone("  +55 67 99612-2809  "));
        testarEntradaValida(() -> Validar.telefone("+55 55 5123-4567   "));
        testarEntradaValida(() -> Validar.telefone("  +55 55 91234-5678  "));

        testarEntradaValida(() -> Validar.telefone("5532914816"));
        testarEntradaValida(() -> Validar.telefone("69996122809"));
        testarEntradaValida(() -> Validar.telefone("(69) 3291-4816"));
        testarEntradaValida(() -> Validar.telefone("(69) 99612-2809"));
        testarEntradaValida(() -> Validar.telefone("+55 55 3291-4816"));
        testarEntradaValida(() -> Validar.telefone("+55 55 99612-2809"));
    }

    @Test
    public void email() {
        testarEntradaInvalida(() -> Validar.email(null));
        testarEntradaInvalida(() -> Validar.email(""));
        testarEntradaInvalida(() -> Validar.email("kleberkruger"));
        testarEntradaInvalida(() -> Validar.email("kleber kruger@gmail.com"));

        testarEntradaValida(() -> Validar.email("kleberkruger@gmail.com"));
    }

    @Test
    public void usuario() {
        testarEntradaValida(() -> Validar.usuario("kleber_kruger"));
    }

    @Test
    public void senha() {
        testarEntradaInvalida(() -> Validar.senha(null));
        testarEntradaInvalida(() -> Validar.senha(""));
        testarEntradaInvalida(() -> Validar.senha("12345"));

        // Senha pode ter espaço. Valide o valor: "123456 "
        testarEntradaValida(() -> Validar.senha("123456"));
        testarEntradaValida(() -> Validar.senha(" 123456 "));
        testarEntradaValida(() -> Validar.senha("      "));

        testarEntradaValida(() -> Validar.senha("123456", true));
    }

    @Test
    public void dataNascimento() {
        testarEntradaInvalida(() -> Validar.dataNascimento(LocalDate.parse("1800-12-08")));

        testarEntradaValida(() -> Validar.dataNascimento(LocalDate.parse("1988-12-08")));
    }

    @Test
    public void cpf() {
        testarEntradaInvalida(() -> Validar.cpf("021357301 65"));

        testarEntradaValida(() -> Validar.cpf("02135730165"));
        testarEntradaValida(() -> Validar.cpf("  02135730165 "));
    }

    @Test
    public void cnpj() {
        testarEntradaInvalida(() -> Validar.cnpj("02135730165"));
    }

    @Test
    public void codigoBanco() {
        testarEntradaInvalida(() -> Validar.codigoBanco(null));
        testarEntradaInvalida(() -> Validar.codigoBanco(""));
        testarEntradaInvalida(() -> Validar.codigoBanco("abc"));
        testarEntradaInvalida(() -> Validar.codigoBanco("m99"));
        testarEntradaInvalida(() -> Validar.codigoBanco("0"));
        testarEntradaInvalida(() -> Validar.codigoBanco("-1"));
        testarEntradaInvalida(() -> Validar.codigoBanco("1000"));
        testarEntradaInvalida(() -> Validar.codigoBanco("001-0"));
        testarEntradaInvalida(() -> Validar.codigoBanco("55."));
        testarEntradaInvalida(() -> Validar.codigoBanco("55.5"));

        testarEntradaValida(() -> Validar.codigoBanco("1"));
        testarEntradaValida(() -> Validar.codigoBanco("001"));
        testarEntradaValida(() -> Validar.codigoBanco("0001"));
        testarEntradaValida(() -> Validar.codigoBanco("00001"));
        testarEntradaValida(() -> Validar.codigoBanco("999"));
    }
}
