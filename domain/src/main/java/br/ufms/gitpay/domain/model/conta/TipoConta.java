package br.ufms.gitpay.domain.model.conta;

public enum TipoConta {

    CONTA_CORRENTE("Conta Corrente", "cc"),
    CONTA_POUPANCA("Conta Poupança", "cp"),
    CONTA_PAGAMENTO("Conta de Pagamento", "pg");

    /**
     * Tipo de Conta Bancária
     *
     * @param descricao  descrição
     * @param abreviacao abreviação
     */
    TipoConta(String descricao, String abreviacao) {
        this.descricao = descricao;
        this.abreviacao = abreviacao;
    }

    private final String descricao;
    private final String abreviacao;

    /**
     * @return a descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @return a abreviação (cc, cp, cp)
     */
    public String getAbreviacao() {
        return abreviacao;
    }
}
