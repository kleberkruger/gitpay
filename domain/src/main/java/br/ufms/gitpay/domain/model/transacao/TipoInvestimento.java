package br.ufms.gitpay.domain.model.transacao;

public enum TipoInvestimento {

    RENDA_FIXA("Renda fixa"),
    FUNDOS_DE_INVESTIMENTO("Fundos de investimento"),
    TESOURO_DIRETO("Tesouro direto"),
    BOLSA_DE_VALORES("Bolsa de valores");

    /**
     * Tipo de Investimento
     *
     * @param descricao
     */
    TipoInvestimento(String descricao) {
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
