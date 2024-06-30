package br.ufms.gitpay.domain.util;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

class Validacao<T> {

    protected final String atributo;
    protected final T valor;

    public Validacao(String atributo, T valor) {
        this.atributo = atributo;
        this.valor = valor;
    }

    @SuppressWarnings("unchecked")
    public <V extends Validacao<T>> V self() {
        return (V) this;
    }

    public <V extends Validacao<T>> V validarNulo(boolean obrigatorio) {
        if (valor == null) {
            throw new IllegalArgumentException(atributo + " com valor nulo");
        }
        return self();
    }

    public <V extends Validacao<T>> V validar(Consumer<Optional<T>> func) {
        if (func != null) func.accept(Optional.ofNullable(valor));
        return self();
    }

    public <V extends Validacao<T>> V validar(BiConsumer<String, Optional<T>> func) {
        if (func != null) func.accept(atributo, Optional.ofNullable(valor));
        return self();
    }

    public T getValor() {
        return valor;
    }
}
