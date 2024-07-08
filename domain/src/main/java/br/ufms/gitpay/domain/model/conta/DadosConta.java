package br.ufms.gitpay.domain.model.conta;

/**
 * Record que define os dados públicos necessários para a identificação de uma conta bancária
 *
 * @param tipo    tipo da conta (corrente, poupança ou conta de pagamento)
 * @param banco   código do banco
 * @param agencia número da agência (sem dígito)
 * @param numero  número da conta (com dígito)
 */
public record DadosConta(TipoConta tipo, String banco, int agencia, NumeroConta numero) {

    public static DadosConta of(ContaBancaria conta) {
        return new DadosConta(conta.getTipoConta(), conta.getBanco(), conta.getAgencia(), conta.getNumeroDigito());
    }
}
