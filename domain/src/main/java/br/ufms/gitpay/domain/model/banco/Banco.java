package br.ufms.gitpay.domain.model.banco;

import br.ufms.gitpay.domain.util.Validar;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Banco {

    public static final Banco GitPay = new Banco(666, "GitPay", "GitPay Pagamentos S.I.");

    private final String codigo;
    private final String nome;
    private final String razaoSocial;

    public Banco() {
        this(666, "GitPay", "GitPay Pagamentos S.I.");
    }

    /**
     * Cria um objeto Banco.
     *
     * @param codigo      código do banco
     * @param nome        nome do banco
     * @param razaoSocial razão social
     */
    public Banco(int codigo, String nome, String razaoSocial) {
        this(String.valueOf(codigo), nome, razaoSocial);
    }

    /**
     * Cria um objeto Banco.
     *
     * @param codigo      código do banco
     * @param nome        nome do banco
     * @param razaoSocial razão social
     */
    public Banco(String codigo, String nome, String razaoSocial) {
//    @JsonCreator
//    public Banco(
//            @JsonProperty("codigo") String codigo,
//            @JsonProperty("nome") String nome,
//            @JsonProperty("razaoSocial") String razaoSocial
//    ) {
        this.codigo = Validar.codigoBanco(codigo);
        this.nome = Validar.nomeEmpresa(nome);
        this.razaoSocial = Validar.razaoSocial(razaoSocial);
    }

    /**
     * @return o código do banco formatado com três dígitos
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return o nome do banco
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return a razão social
     */
    public String getRazaoSocial() {
        return razaoSocial;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Banco banco)) return false;
        return codigo.equals(banco.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
