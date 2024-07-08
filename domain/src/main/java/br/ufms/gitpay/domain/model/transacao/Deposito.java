package br.ufms.gitpay.domain.model.transacao;

import br.ufms.gitpay.domain.model.conta.ContaBancaria;

import java.time.LocalDateTime;
import java.util.Objects;

public class Deposito extends Transacao {

    private final ContaBancaria destino;

    /**
     * Cria um objeto Deposito.
     *
     * @param destino conta de destino
     * @param valor   valor do depósito
     */
    public Deposito(ContaBancaria destino, double valor) {
        this(null, destino, valor, null);
    }

    /**
     * Cria um objeto Deposito.
     *
     * @param id       id da transação
     * @param destino  conta de destino
     * @param valor    valor do depósito
     * @param dataHora data e hora da transação
     */
    public Deposito(String id, ContaBancaria destino, double valor, LocalDateTime dataHora) {
        super(id, valor, dataHora);
        this.destino = Objects.requireNonNull(destino, "A conta de destino não pode ser nula");
    }

    /**
     * @return a conta de destino
     */
    public ContaBancaria getDestino() {
        return destino;
    }

    /**
     * @return o tipo da transação
     */
    @Override
    public TipoTransacao getTipo() {
        return TipoTransacao.DEPOSITO;
    }
}
