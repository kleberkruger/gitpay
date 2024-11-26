package br.ufms.gitpay.domain.model.conta;

import java.io.Serializable;

/**
 * Record que define os dados públicos necessários para a identificação de uma conta bancária
 *
 * @param tipo    tipo da conta (corrente, poupança ou conta de pagamento)
 * @param banco   código do banco
 * @param agencia número da agência (sem dígito)
 * @param numero  número da conta (com dígito)
 */
public record DadosConta(TipoConta tipo, String banco, int agencia, NumeroConta numero) implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DadosConta(TipoConta t, String bc, int ag, NumeroConta c))) return false;
        return agencia == ag && banco.equals(bc) && tipo == t && numero.equals(c);
    }

    @Override
    public int hashCode() {
        int result = tipo.hashCode();
        result = 31 * result + banco.hashCode();
        result = 31 * result + agencia;
        result = 31 * result + numero.hashCode();
        return result;
    }

    /**
     * Retorna um objeto com os dados públicos desta conta. Este objeto costuma ser usado
     * como identificador único, pois contém as informações do banco, agência, número e
     * tipo da conta
     *
     * @param conta conta bancária
     * @return o objeto com os dados públicos de identificação desta conta
     */
    public static DadosConta of(ContaBancaria conta) {
        return new DadosConta(conta.getTipoConta(), conta.getBanco(), conta.getAgencia(), conta.getNumeroDigito());
    }
}
