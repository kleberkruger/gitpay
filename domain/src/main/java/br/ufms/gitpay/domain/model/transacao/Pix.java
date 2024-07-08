package br.ufms.gitpay.domain.model.transacao;

import br.ufms.gitpay.domain.model.conta.ContaBancaria;

import java.time.LocalDateTime;

public class Pix extends Transferencia {

    /**
     * Cria um objeto Pix
     *
     * @param origem  conta de origem
     * @param destino conta de destino
     * @param valor   valor da transferência
     */
    public Pix(ContaBancaria origem, ContaBancaria destino, double valor) {
        super(origem, destino, valor);
    }

    /**
     * Cria um objeto Pix
     *
     * @param id       id da transação
     * @param origem   conta de origem
     * @param destino  conta de destino
     * @param valor    valor da transferência
     * @param dataHora data e hora da transação
     */
    public Pix(String id, ContaBancaria origem, ContaBancaria destino, double valor, LocalDateTime dataHora) {
        super(id, origem, destino, valor, dataHora);
    }

    /**
     * @return TipoTransacao.PIX
     */
    @Override
    public TipoTransacao getTipo() {
        return TipoTransacao.PIX;
    }
}
