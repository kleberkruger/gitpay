package br.ufms.gitpay.domain.model.usuario;

import br.ufms.gitpay.domain.util.Validar;

import java.time.LocalDate;

public class PessoaFisica extends Pessoa {

    private final String cpf;
    private LocalDate dataNascimento;

    /**
     * Cria um objeto PessoaFisica
     *
     * @param nome nome da pessoa
     * @param cpf  cpf
     */
    public PessoaFisica(String nome, String cpf) {
        super(nome);
        this.cpf = Validar.cpf(cpf);
    }

    /**
     * Cria um objeto PessoaFisica
     *
     * @param nome           nome da pessoa
     * @param cpf            cpf
     * @param telefone       telefone
     * @param email          email
     * @param dataNascimento data de nascimento
     */
    public PessoaFisica(String nome, String cpf, String telefone, String email, LocalDate dataNascimento) {
        super(nome);

        this.cpf = Validar.cpf(cpf);
        this.setTelefone(telefone);
        this.setEmail(email);
        this.setDataNascimento(dataNascimento);
    }

    /**
     * @return o cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @return a data de nascimento
     */
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    /**
     * Altera a data de nascimento
     *
     * @param dataNascimento nova data de nascimento
     */
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = Validar.dataNascimento(dataNascimento);
    }

    /**
     * @return TipoPessoa.PessoaFisica
     */
    @Override
    public TipoPessoa getTipo() {
        return TipoPessoa.PESSOA_FISICA;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PessoaFisica that)) return false;
        return cpf.equals(that.cpf);
    }

    @Override
    public int hashCode() {
        return cpf.hashCode();
    }
}
