package br.ufms.gitpay.domain.util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

class Validacao<T> {

    protected final String atributo;
    protected final T valor;

//    public Validacao(String atributo, T valor, boolean obrigatorio); ja validar se é obrigatório no início ???

    public Validacao(String atributo, T valor) {
        Objects.requireNonNull(atributo, "Nome do atributo nulo");

        if (valor instanceof String val) {
            System.out.println(atributo + ": " + val.trim().replaceAll("\\s+", " "));
        }

        this.atributo = atributo;
        this.valor = valor;
    }

    @SuppressWarnings("unchecked")
    private <V extends Validacao<T>> V self() {
        return (V) this;
    }

    public <V extends Validacao<T>> V validarNulo(boolean obrigatorio) {
        if (obrigatorio && valor == null) {
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
