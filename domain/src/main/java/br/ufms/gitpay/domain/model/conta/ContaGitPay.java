package br.ufms.gitpay.domain.model.conta;

import br.ufms.gitpay.domain.model.banco.Banco;
import br.ufms.gitpay.domain.model.usuario.Pessoa;
import br.ufms.gitpay.domain.model.usuario.TipoPessoa;
import br.ufms.gitpay.domain.model.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.Objects;

public class ContaGitPay implements ContaBancaria {

    private static final int AGENCIA_VIRTUAL = 1;

    private final NumeroConta numero;
    private double saldo;
    private double limite;
    private final Usuario<? extends Pessoa> usuario;
    private final LocalDateTime dataHoraCriacao;

    /**
     * Cria um objeto ContaGitPay a ser persistida no repositório.
     * Esta conta ainda não está persistida. Portanto, não possui número e
     * data e hora de cadastro.
     *
     * @param usuario usuário
     */
    public ContaGitPay(Usuario<? extends Pessoa> usuario) {
        this(null, usuario, null);
    }

    /**
     * Cria um objeto ContaGitPay. Este objeto representa uma conta inserida
     * no repositório. Sendo assim, possui número e data e hora de criação.
     *
     * @param numero          número da conta
     * @param usuario         usuário
     * @param dataHoraCriacao data e hora de criação
     */
    public ContaGitPay(Integer numero, Usuario<? extends Pessoa> usuario, LocalDateTime dataHoraCriacao) {
        this.usuario = Objects.requireNonNull(usuario, "Usuário nulo");
        this.numero = numero != null ? new NumeroConta(numero) : null;
        this.saldo = 0.0;
        this.limite = 0.0;
        this.dataHoraCriacao = dataHoraCriacao;
    }

    @Override
    public String getBanco() {
        return Banco.GitPay.getCodigo();
    }

    @Override
    public int getAgencia() {
        return AGENCIA_VIRTUAL;
    }

    @Override
    public NumeroConta getNumeroDigito() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        if (limite < 0.0) {
            throw new IllegalArgumentException("Limite inválido");
        }
        this.limite = limite;
    }

    @Override
    public String getNomeTitular() {
        return usuario.getDados().getNome();
    }

    @Override
    public String getDocumentoTitular() {
        return usuario.getDocumento();
    }

    public Usuario<? extends Pessoa> getUsuario() {
        return usuario;
    }

    @Override
    public TipoConta getTipoConta() {
        return TipoConta.CONTA_PAGAMENTO;
    }

    @Override
    public TipoPessoa getTipoPessoa() {
        return usuario.getDados().getTipo();
    }

    public boolean isAtiva() {
        return numero != null;
    }

    public static Build build() {
        return new Build();
    }

    public static class Build {

        private Usuario<? extends Pessoa> usuario;
        private Integer numero;
        private LocalDateTime dataHoraCriacao;

        private Build() {
            this.numero = null;
            this.dataHoraCriacao = null;
        }

        public Build setUsuario(Usuario<? extends Pessoa> usuario) {
            this.usuario = usuario;
            return this;
        }

        public Build criadaEm(int numero, LocalDateTime dataHoraCriacao) {
            this.numero = numero;
            this.dataHoraCriacao = dataHoraCriacao;
            return this;
        }

        public ContaGitPay build() {
            return new ContaGitPay(numero, usuario, dataHoraCriacao);
        }
    }
}
