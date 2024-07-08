package br.ufms.gitpay.domain.model.transacao;

import br.ufms.gitpay.domain.model.conta.ContaBancaria;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transferencia extends Transacao {

    private final ContaBancaria origem;
    private final ContaBancaria destino;

    /**
     * Cria um objeto Transferencia.
     *
     * @param origem  conta de origem
     * @param destino conta de destino
     * @param valor   valor da transferência
     */
    public Transferencia(ContaBancaria origem, ContaBancaria destino, double valor) {
        this(null, origem, destino, valor, null);
    }

    /**
     * Cria um objeto Transferencia.
     *
     * @param id       id da transação
     * @param origem   conta de origem
     * @param destino  conta de destino
     * @param valor    valor da transferência
     * @param dataHora data e hora da transação
     */
    public Transferencia(String id, ContaBancaria origem, ContaBancaria destino, double valor, LocalDateTime dataHora) {
        super(id, valor, dataHora);

        this.origem = Objects.requireNonNull(origem, "A conta de origem não pode ser nula");
        this.destino = Objects.requireNonNull(destino, "A conta de destino não pode ser nula");
    }

    /**
     * @return a conta de origem
     */
    public ContaBancaria getOrigem() {
        return origem;
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
        return TipoTransacao.TRANSFERENCIA;
    }
}
