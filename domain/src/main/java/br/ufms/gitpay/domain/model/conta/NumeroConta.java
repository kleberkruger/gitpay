package br.ufms.gitpay.domain.model.conta;

import br.ufms.gitpay.domain.util.Validar;

import java.io.Serial;
import java.io.Serializable;
import java.util.function.Function;
import java.util.stream.IntStream;

public class NumeroConta implements Comparable<NumeroConta>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final int numero;
    private final int digito;

    /**
     * Cria um número de conta usando o algoritmo Módulo 11 para definir o dígito
     *
     * @param numero número da conta
     */
    public NumeroConta(int numero) {
        this(numero, NumeroConta::modulo11);
    }

    /**
     * Cria um número de conta usando a função verificador para definir o dígito
     *
     * @param numero      número da conta
     * @param verificador função de verificação de dígito
     */
    public NumeroConta(int numero, Function<Integer, Integer> verificador) {
        this(numero, verificador.apply(numero));
    }

    /**
     * Cria um número de conta
     *
     * @param numero número
     * @param digito dígito
     */
    public NumeroConta(int numero, int digito) {
        this(formatar(numero, digito));
    }

    /**
     * Cria um número de conta
     *
     * @param numeroDigito número da conta com dígito
     */
    public NumeroConta(String numeroDigito) {
        String[] valor = Validar.numeroConta(numeroDigito).split("-");
        this.numero = Integer.parseInt(valor[0]);
        this.digito = Integer.parseInt(valor[1]);
    }

    /**
     * Cria um número de conta validando o dígito informado pelo algoritmo Módulo 11
     *
     * @param numeroDigito número com dígito
     * @return um objeto NumeroConta
     */
    public static NumeroConta validar(String numeroDigito) {
        return validar(numeroDigito, NumeroConta::modulo11);
    }

    /**
     * Cria um número de conta validando o dígito informado pelo algoritmo Módulo 11
     *
     * @param numeroDigito número com dígito
     * @param verificador  algoritmo verificador de dígito
     * @return um objeto NumeroConta
     */
    public static NumeroConta validar(String numeroDigito, Function<Integer, Integer> verificador) {
        return new NumeroConta(Validar.numeroConta(numeroDigito, verificador));
    }

    /**
     * Cria um número de conta validando o dígito informado pelo algoritmo Módulo 11
     *
     * @param numero número
     * @param digito dígito
     * @return um objeto NumeroConta
     */
    public static NumeroConta validar(int numero, int digito) {
        return validar(numero, digito, NumeroConta::modulo11);
    }

    /**
     * Cria um número de conta validando o dígito informado pelo algoritmo Módulo 11
     *
     * @param numero      número
     * @param digito      dígito
     * @param verificador algoritmo verificador de dígito
     * @return um objeto NumeroConta
     */
    public static NumeroConta validar(int numero, int digito, Function<Integer, Integer> verificador) {
        return validar(Validar.numeroConta(numero, digito, verificador));
    }

    /**
     * @return o número
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @return o dígito
     */
    public int getDigito() {
        return digito;
    }

    /**
     * @return o número com o dígito verificador no seguinte formato: #####-#
     */
    public String getNumeroDigitoStr() {
        return formatar(numero, digito);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumeroConta that)) return false;

        return numero == that.numero && digito == that.digito;
    }

    @Override
    public int hashCode() {
        int result = numero;
        result = 31 * result + digito;
        return result;
    }

    @Override
    public int compareTo(NumeroConta other) {
        return this.numero - other.numero;
    }

    @Override
    public String toString() {
        return getNumeroDigitoStr();
    }

    /**
     * Algoritmo verificador de dígito usado em números bancários como mecanismo de autenticação
     * autenticar um valor numérico, evitando fraudes e erros de transmissão ou digitação.
     * Algoritmo: Módulo 10.
     *
     * @param numero numero a ser verificado.
     * @return o dígito verificador
     */
    public static int modulo10(int numero) {
        int[] digitos = new StringBuilder(String.valueOf(numero))
                .reverse().chars()
                .map(Character::getNumericValue).toArray();
        int soma = IntStream.range(0, digitos.length)
                .map(i -> i % 2 == 0 ? digitos[i] : digitos[i] * 2 > 9 ? digitos[i] * 2 - 9 : digitos[i] * 2)
                .sum();
        int resto = 10 - (soma % 10);
        return resto == 10 ? 0 : resto;
    }

    /**
     * Algoritmo verificador de dígito usado em números bancários como mecanismo de autenticação
     * autenticar um valor numérico, evitando fraudes e erros de transmissão ou digitação.
     * Algoritmo: Módulo 11.
     *
     * @param numero numero a ser verificado.
     * @return o dígito verificador
     */
    public static int modulo11(int numero) {
        int[] digitos = new StringBuilder(String.valueOf(numero))
                .reverse().chars()
                .map(Character::getNumericValue).toArray();
        int soma = IntStream.range(0, digitos.length)
                .map(i -> digitos[i] * ((i % 8) + 2))
                .sum();
        int resto = soma % 11;
        return (resto == 0 || resto == 1) ? 0 : 11 - resto;
    }

    public static String formatar(int numero, int digito) {
        return String.format("%05d-%1d", numero, digito);
    }
}
