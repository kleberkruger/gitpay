package br.ufms.gitpay.domain.util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

class ValidacaoString extends Validacao<String> {

    private final Function<String, String> valueFunction;

    public ValidacaoString(String atributo, String valor) {
        this(atributo, valor, val -> val);
    }

    public ValidacaoString(String atributo, String valor, Function<String, String> valueFunction) {
        super(atributo, valor);
        this.valueFunction = valueFunction;
    }

    public ValidacaoString validarNuloEmBranco(boolean obrigatorio) {
        super.validarNulo(obrigatorio);
        if (obrigatorio && valor.isBlank()) {
            throw new IllegalArgumentException(atributo + " em branco");
        }
        return this;
    }

    public ValidacaoString validarTamanho(int min, int max) {
        int len = valor != null ? this.valor.trim().length() : 0;
        if (len < min || len > max) {
            throw new IllegalArgumentException(String.format("%s deve conter %s caracter%s", atributo,
                    min < max ? "entre " + min + " e " + max : max, max > 1 ? "es" : ""));
        }
        return this;
    }

    public ValidacaoString validarPorExpressao(String regex) {
        return validarPorExpressao(regex, null);
    }

    public ValidacaoString validarPorExpressao(String regex, String msgErro) {
        if (regex != null && !valor.matches(regex)) {
            msgErro = msgErro != null ? "\n" + msgErro.trim() : "";
            throw new IllegalArgumentException(String.format("%s inválido: [%s]%s", atributo, valor, msgErro));
        }
        return this;
    }

    public ValidacaoString validarValor(Consumer<String> func) {
        return super.validar(s -> func.accept(getValor()));
    }

    public ValidacaoString validarValor(BiConsumer<String, String> func) {
        return super.validar(s -> func.accept(atributo, getValor()));
    }

    @Override
    public String getValor() {
        return valueFunction.apply(this.valor != null ? this.valor.trim() : "");
    }
}
