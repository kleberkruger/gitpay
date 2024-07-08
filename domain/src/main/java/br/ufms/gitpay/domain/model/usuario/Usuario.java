package br.ufms.gitpay.domain.model.usuario;

import br.ufms.gitpay.domain.util.Validar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class Usuario<P extends Pessoa> {

    private final P dados;
    private String senha;
    private final LocalDateTime dataHoraCadastro;

    private static final LocalDate DATA_MINIMA_CADASTRO = LocalDate.parse("2024-01-01");

    public Usuario(P dados, String senha) {
        this(dados, senha, null);
    }

    public Usuario(P dados, String senha, LocalDateTime dataHoraCadastro) {
        this.dados = Objects.requireNonNull(dados, "Dados de pessoa física ou jurídica nulo");
        this.senha = Validar.senha(senha);

        // Estou permitindo data de cadastro um pouco a frente por questões de sincronia de horário com o servidor
        this.dataHoraCadastro = Validar.estaEntrePeriodo("Data de cadastro", DATA_MINIMA_CADASTRO.atStartOfDay(),
                dataHoraCadastro, LocalDateTime.now().plusDays(1), false);
    }

    public P getDados() {
        return dados;
    }

    public String getDocumento() {
        return switch (dados.getTipo()) {
            case PESSOA_FISICA -> ((PessoaFisica) dados).getCpf();
            case PESSOA_JURIDICA -> ((PessoaJuridica) dados).getCnpj();
        };
    }

    public String getLogin() {
        return getDocumento();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = Validar.senha(senha);
    }

    public Optional<LocalDateTime> getDataHoraCadastro() {
        return Optional.ofNullable(dataHoraCadastro);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario<?> usuario)) return false;
        return dados.equals(usuario.dados);
    }

    @Override
    public int hashCode() {
        return dados.hashCode();
    }

    /**
     * Retorna um builder para construir um objeto Usuario.
     * <p>
     * Este design pattern é comumente utilizado para construir objetos compostos
     * por uma quantidade excessiva de atributos, substituindo construtores repletos
     * de parâmetros por uma sintaxe mais "amigável" de construção.
     *
     * @return o builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String nome;
        private String telefone;
        private String email;
        private String senha;
        private LocalDateTime dataCadastro;

        private Builder() {
            nome = telefone = email = senha = "";
            dataCadastro = null;
        }

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setContato(String telefone, String email) {
            this.telefone = telefone;
            this.email = email;
            return this;
        }

        public Builder setSenha(String senha) {
            this.senha = senha;
            return this;
        }

        public Builder cadastradoEm(LocalDateTime dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        /**
         * Cria um objeto Usuario<PessoaFisica> conforme os parâmetros recebidos
         *
         * @param cpf            cpf
         * @param dataNascimento data de nascimento
         * @return um objeto Usuario<PessoaFisica>
         */
        public Usuario<PessoaFisica> buildPessoaFisica(String cpf, LocalDate dataNascimento) {
            return new Usuario<>(new PessoaFisica(nome, cpf, telefone, email, dataNascimento),
                    senha, dataCadastro);
        }

        /**
         * Cria um objeto Usuario<PessoaJuridica> conforme os parâmetros recebidos
         *
         * @param cnpj        cnpj
         * @param razaoSocial razão social
         * @return um objeto Usuario<PessoaJuridica>
         */
        public Usuario<PessoaJuridica> buildPessoaJuridica(String cnpj, String razaoSocial) {
            return new Usuario<>(new PessoaJuridica(nome, razaoSocial, cnpj, telefone, email),
                    senha, dataCadastro);
        }
    }
}
