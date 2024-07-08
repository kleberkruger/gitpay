package br.ufms.gitpay.domain.model.conta;

import br.ufms.gitpay.domain.model.usuario.TipoPessoa;
import br.ufms.gitpay.domain.util.Validar;

import java.util.Objects;

public class ContaExterna implements ContaBancaria {

    private final String banco;
    private final int agencia;
    private final NumeroConta numero;
    private final TipoConta tipoConta;
    private final String nomeTitular;
    private final String documentoTitular;
    private final TipoPessoa tipoPessoa;

    /**
     * Cria um objeto ContaExterna. Este objeto representa as contas externas de outros bancos.
     * Portanto, ela é composta apenas das informações públicas de uma conta bancária.
     *
     * @param tipo             tipo da conta
     * @param banco            código do banco
     * @param agencia          número da agência
     * @param numero           número da conta (sem dígito)
     * @param digito           dígito
     * @param nomeTitular      nome do titular
     * @param documentoTitular documento do titular
     */
    public ContaExterna(TipoConta tipo, String banco, int agencia, int numero, int digito,
                        String nomeTitular, String documentoTitular) {

        this(tipo, banco, agencia, NumeroConta.formatar(numero, digito), nomeTitular, documentoTitular);
    }

    /**
     * Cria um objeto ContaExterna. Este objeto representa as contas externas de outros bancos.
     * Portanto, ela é composta apenas das informações públicas de uma conta bancária.
     *
     * @param tipo             tipo da conta
     * @param banco            código do banco
     * @param agencia          número da agência
     * @param numeroDigito     número da conta (com dígito)
     * @param nomeTitular      nome do titular
     * @param documentoTitular documento do titular
     */
    public ContaExterna(TipoConta tipo, String banco, int agencia, String numeroDigito,
                        String nomeTitular, String documentoTitular) {

        this.banco = Validar.codigoBanco(banco);
        this.agencia = Validar.numeroAgencia(agencia);
        this.numero = new NumeroConta(numeroDigito);
        this.tipoConta = Objects.requireNonNull(tipo, "Tipo da conta nulo");
        this.tipoPessoa = TipoPessoa.valueOfDocumento(documentoTitular);
        this.nomeTitular = switch (tipoPessoa) {
            case PESSOA_FISICA -> Validar.nomePessoa(nomeTitular, true);
            case PESSOA_JURIDICA -> Validar.nomeEmpresa(nomeTitular);
        };
        this.documentoTitular = switch (tipoPessoa) {
            case PESSOA_FISICA -> Validar.cpf(documentoTitular);
            case PESSOA_JURIDICA -> Validar.cnpj(documentoTitular);
        };
    }

    /**
     * @return o tipo da conta
     */
    @Override
    public TipoConta getTipoConta() {
        return tipoConta;
    }

    /**
     * @return o código do banco
     */
    @Override
    public String getBanco() {
        return banco;
    }

    /**
     * @return o número da agência
     */
    @Override
    public int getAgencia() {
        return agencia;
    }

    /**
     * @return o número da conta (com dígito)
     */
    @Override
    public NumeroConta getNumeroDigito() {
        return numero;
    }

    /**
     * @return o nome do titular da conta (seja pessoa física ou jurídica)
     */
    @Override
    public String getNomeTitular() {
        return nomeTitular;
    }

    /**
     * @return o cpf (pessoa física) ou o cnpj (pessoa jurídica) do titular da conta
     */
    @Override
    public String getDocumentoTitular() {
        return documentoTitular;
    }

    /**
     * @return o tipo de pessoa (se física ou jurídica)
     */
    @Override
    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }
}
