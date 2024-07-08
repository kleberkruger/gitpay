package br.ufms.gitpay.domain.model.usuario;

import br.ufms.gitpay.domain.util.Validar;

public class PessoaJuridica extends Pessoa {

    private final String cnpj;
    private String razaoSocial;

    /**
     * Cria um objeto PessoaJuridica
     *
     * @param nome nome da empresa
     * @param cnpj cnpj
     */
    public PessoaJuridica(String nome, String cnpj) {
        super(nome);
        this.cnpj = Validar.cnpj(cnpj);
    }

    /**
     * Cria um objeto PessoaJuridica
     *
     * @param nome        nome da empresa
     * @param razaoSocial razão social
     * @param cnpj        cnpj
     * @param telefone    telefone
     * @param email       email
     */
    public PessoaJuridica(String nome, String razaoSocial, String cnpj, String telefone, String email) {
        super(nome);

        this.cnpj = Validar.cnpj(cnpj);
        this.setRazaoSocial(razaoSocial);
        this.setTelefone(telefone);
        this.setEmail(email);
    }

    /**
     * @return o cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @return a razão social
     */
    public String getRazaoSocial() {
        return razaoSocial;
    }

    /**
     * Altera a razão social
     *
     * @param razaoSocial razão social
     */
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = Validar.razaoSocial(razaoSocial);
    }

    /**
     * @return TipoPessoa.PESSOA_JURIDICA
     */
    @Override
    public TipoPessoa getTipo() {
        return TipoPessoa.PESSOA_JURIDICA;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PessoaJuridica that)) return false;
        return cnpj.equals(that.cnpj);
    }

    @Override
    public int hashCode() {
        return cnpj.hashCode();
    }
}
