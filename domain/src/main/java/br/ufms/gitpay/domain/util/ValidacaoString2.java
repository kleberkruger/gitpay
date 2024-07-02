package br.ufms.gitpay.domain.util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

class ValidacaoString2 extends Validacao2<String> {

    ValidacaoString2(String campo, String valor) {
        this(campo, valor, true, true);
    }

    ValidacaoString2(String campo, String valor, boolean obrigatorio) {
        this(campo, valor, obrigatorio, true);
    }

    ValidacaoString2(String campo, String valor, boolean obrigatorio, boolean trim) {
        super(campo, valor != null ? trim ? valor.trim() : valor : "", obrigatorio);
    }

    @Override
    protected void validarNulo() {
        if (obrigatorio && valor.isEmpty()) {
            throw new IllegalArgumentException(campo + " com valor nulo ou em branco");
        }
    }

    /*
    // null -> "": nomePessoa, nomeEmpresa, razaoSocial, telefone, email, usuario, senha, cpf, cnpj, codigoBanco
    // trim(): nomePessoa, nomeEmpresa, razaoSocial, telefone, email, usuario, cpf, cnpj, codigoBanco || senha
    // evitarEspacosAdicionais: nomePessoa, nomeEmpresa, razaoSocial || telefone, email, usuario, cpf, cnpj, codigoBanco

    // valor e getValor()?
     */

    public ValidacaoString2 validarTamanho(int min, int max) {
        int len = this.valor.length();
        if ((obrigatorio || len > 0) && (len < min || len > max)) {
            throw new IllegalArgumentException(String.format("%s deve conter %s caracter%s", campo,
                    min < max ? "entre " + min + " e " + max : max, max > 1 ? "es" : ""));
        }
        return this;
    }

    public ValidacaoString2 validarPorExpressao(String regex) {
        return validarPorExpressao(regex, null);
    }

    public ValidacaoString2 validarPorExpressao(String regex, String msgErro) {
        if (obrigatorio && regex != null && !valor.matches(regex)) {
            msgErro = msgErro != null ? "\n" + msgErro.trim() : "";
            throw new IllegalArgumentException(String.format("%s inválido: [%s]%s", campo, valor, msgErro));
        }
        return this;
    }

    public ValidacaoString2 validarValor(Consumer<String> func) {
        return super.validar(s -> func.accept(getValor()));
    }

    public ValidacaoString2 validarValor(BiConsumer<String, String> func) {
        return super.validar(s -> func.accept(campo, getValor()));
    }

    public String getValor(Function<String, String> func) {
        return func != null ? func.apply(valor) : valor;
    }
}
