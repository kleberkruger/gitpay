package br.ufms.gitpay.domain.model.transacao;

import br.ufms.gitpay.domain.model.conta.ContaBancaria;

import java.time.LocalDateTime;
import java.util.Objects;

public class Saque extends Transacao {

    private final ContaBancaria origem;

    /**
     * Cria um objeto Saque.
     *
     * @param origem conta de origem
     * @param valor  valor do saque
     */
    public Saque(ContaBancaria origem, double valor) {
        this(null, origem, valor, null);
    }

    /**
     * Cria um objeto Saque.
     *
     * @param id       id da transação
     * @param origem   conta de origem
     * @param valor    valor do saque
     * @param dataHora data e hora da transação
     */
    public Saque(String id, ContaBancaria origem, double valor, LocalDateTime dataHora) {
        super(id, valor, dataHora);
        this.origem = Objects.requireNonNull(origem, "A conta de origem não pode ser nula");
    }

    /**
     * @return a conta de origem
     */
    public ContaBancaria getOrigem() {
        return origem;
    }

    /**
     * @return o tipo da transação
     */
    @Override
    public TipoTransacao getTipo() {
        return TipoTransacao.SAQUE;
    }
}
