package br.ufms.gitpay.domain.util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

class Validacao2<T> {

    protected final String campo;
    protected final T valor;
    protected final boolean obrigatorio;

    Validacao2(String campo, T valor) {
        this(campo, valor, true);
    }

    Validacao2(String campo, T valor, boolean obrigatorio) {
        Objects.requireNonNull(campo, "Nome do campo nulo");

        this.campo = campo;
        this.valor = valor;
        this.obrigatorio = obrigatorio;

        System.out.println("validando " + campo + ": [" + valor + "]");
        validarNulo();
    }

    @SuppressWarnings("unchecked")
    private <V extends Validacao2<T>> V self() {
        return (V) this;
    }

    protected void validarNulo() {
        if (obrigatorio && valor == null) {
            throw new IllegalArgumentException(campo + " com valor nulo");
        }
    }

    public <V extends Validacao2<T>> V validar(Consumer<Optional<T>> func) {
        if (func != null) func.accept(Optional.ofNullable(valor));
        return self();
    }

    public <V extends Validacao2<T>> V validar(BiConsumer<String, Optional<T>> func) {
        if (func != null) func.accept(campo, Optional.ofNullable(valor));
        return self();
    }

    public T getValor() {
        return valor;
    }
}
