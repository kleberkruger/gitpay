package br.ufms.gitpay.domain.model.usuario;

import java.util.Objects;

public enum TipoPessoa {

    PESSOA_FISICA("Pessoa Física"),
    PESSOA_JURIDICA("Pessoa Jurídica");

    /**
     * Tipo de Pessoa (Física ou Jurídica)
     *
     * @param descricao descrição
     */
    TipoPessoa(String descricao) {
        this.descricao = descricao;
    }

    private final String descricao;

    /**
     * @return a descrição
     */
    public String getDescricao() {
        return descricao;
    }

    public static TipoPessoa valueOfDocumento(String cpfOuCnpj) {
        Objects.requireNonNull(cpfOuCnpj, "Número de documento nulo");

        return switch (cpfOuCnpj.replaceAll("\\D", "").length()) {
            case 11 -> TipoPessoa.PESSOA_FISICA;
            case 14 -> TipoPessoa.PESSOA_JURIDICA;
            default -> throw new IllegalArgumentException("Documento inválido. Use somente CPF ou CNPJ");
        };
    }
}
