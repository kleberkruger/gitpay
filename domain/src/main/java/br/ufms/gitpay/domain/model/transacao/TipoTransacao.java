package br.ufms.gitpay.domain.model.transacao;

public enum TipoTransacao {

    DEPOSITO("Depósito"),
    SAQUE("Saque"),
    TRANSFERENCIA("Transferência"),
    PIX("Pix"),
    INVESTIMENTO("Investimento");

    /**
     * Tipo de Transação
     *
     * @param descricao
     */
    TipoTransacao(String descricao) {
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
