package br.ufms.gitpay.domain.model.transacao;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public abstract class Transacao {

    private final String id;
    private final double valor;
    private final LocalDateTime dataHora;

    /**
     * Cria um objeto Transacao
     *
     * @param valor valor da transação
     */
    protected Transacao(double valor) {
        this(null, valor, null);
    }

    /**
     * Cria um objeto Transacao
     *
     * @param id       id da transação
     * @param valor    valor da transação
     * @param dataHora data e hora da transação
     */
    protected Transacao(String id, double valor, LocalDateTime dataHora) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }
        this.id = id;
        this.valor = valor;
        this.dataHora = dataHora;
    }

    /**
     * @return o id da transação
     */
    public Optional<String> getId() {
        return Optional.ofNullable(id);
    }

    /**
     * @return o valor da transação
     */
    public double getValor() {
        return valor;
    }

    /**
     * @return a data e a hora da transação
     */
    public Optional<LocalDateTime> getDataHora() {
        return Optional.ofNullable(dataHora);
    }

    /**
     * Retorna verdadeiro quando a transação foi efetivada. Ao criar uma transação, inicialmente ela só possui
     * um valor. Os campos id e dataHora são definidos somente após a transação ser persistida na base de dados.
     *
     * @return verdadeiro quando a transação foi efetivada.
     */
    public boolean isEfetuada() {
        return id != null;
    }

    /**
     * @return o tipo da transação
     */
    public abstract TipoTransacao getTipo();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transacao transacao)) return false;
        return Objects.equals(getId(), transacao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
