package br.ufms.gitpay.domain.model.usuario;

import br.ufms.gitpay.domain.util.Validar;

public abstract class Pessoa {

    private String nome;
    private String telefone;
    private String email;

    protected Pessoa(String nome) {
        this.setNome(nome);
    }

    /**
     * @return o nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Altera o nome
     *
     * @param nome nome
     */
    public void setNome(String nome) {
        this.nome = switch (getTipo()) {
            case PESSOA_FISICA -> Validar.nomePessoa(nome, true);
            case PESSOA_JURIDICA -> Validar.nomeEmpresa(nome);
        };
    }

    /**
     * @return o telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Altera o telefone
     *
     * @param telefone telefone
     */
    public void setTelefone(String telefone) {
        this.telefone = Validar.telefone(telefone);
    }

    /**
     * @return o email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Altera o email
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = Validar.email(email);
    }

    /**
     * @return o tipo (se pessoa física ou jurídica)
     */
    public abstract TipoPessoa getTipo();
}
