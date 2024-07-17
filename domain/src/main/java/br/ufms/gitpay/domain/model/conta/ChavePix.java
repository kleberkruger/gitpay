package br.ufms.gitpay.domain.model.conta;

import br.ufms.gitpay.domain.util.Validar;

import java.util.Objects;

public class ChavePix {

    private final TipoChavePix tipo;
    private final String chave;
    private final DadosConta conta;

    /**
     * Cria um objeto ChavePix
     *
     * @param tipo  tipo da chave pix
     * @param chave chave pix
     * @param conta dados da conta
     */
    public ChavePix(TipoChavePix tipo, String chave, DadosConta conta) {
        this.tipo = Objects.requireNonNull(tipo, "Tipo de chave pix nulo");
        this.chave = switch (tipo) {
            case TELEFONE -> Validar.telefone(chave, true);
            case EMAIL -> Validar.email(chave, true);
            case CPF -> Validar.cpf(chave);
            case CNPJ -> Validar.cnpj(chave);
            case ALEATORIA -> Validar.chaveAleatoriaPix(chave);
        };
        this.conta = Objects.requireNonNull(conta, "Dados da conta nulo");
    }

    /**
     * @return o tipo da chave pix
     */
    public TipoChavePix getTipo() {
        return tipo;
    }

    /**
     * @return a chave pix
     */
    public String getChave() {
        return chave;
    }

    /**
     * @return a conta associada a esta chave pix
     */
    public DadosConta getConta() {
        return conta;
    }
}
