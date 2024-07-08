package br.ufms.gitpay.domain.model.conta;

public enum TipoChavePix {

    ALEATORIA("Aleatória"),
    TELEFONE("Telefone"),
    EMAIL("Email"),
    CPF("CPF"),
    CNPJ("CNPJ");

    /**
     * Tipo de chaves Pix
     *
     * @param descricao descrição
     */
    TipoChavePix(String descricao) {
        this.descricao = descricao;
    }

    private final String descricao;

    /**
     * @return a descrição
     */
    public String getDescricao() {
        return descricao;
    }
}
