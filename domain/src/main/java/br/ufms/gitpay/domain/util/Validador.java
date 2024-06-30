package br.ufms.gitpay.domain.util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

class Validador<T, V extends Validador<T, V>> {

    protected final String campo;
    protected final T valor;
    protected final boolean obrigatorio;

    Validador(String campo, T valor) {
        this(campo, valor, true);
    }

    Validador(String campo, T valor, boolean obrigatorio) {
        Objects.requireNonNull(campo, "Nome do campo nulo");

        this.campo = campo;
        this.valor = valor;
        this.obrigatorio = obrigatorio;
    }

    @SuppressWarnings("unchecked")
    private V self() {
        return (V) this;
    }

    public V validarNulo() {
        if (obrigatorio && valor == null) {
            throw new IllegalArgumentException(campo + " com valor nulo");
        }
        return self();
    }

    public V validar(Consumer<Optional<T>> func) {
        if (func != null) func.accept(Optional.ofNullable(valor));
        return self();
    }

    public V validar(BiConsumer<String, Optional<T>> func) {
        if (func != null) func.accept(campo, Optional.ofNullable(valor));
        return self();
    }

    public T getValor() {
        return valor;
    }
}
