package br.ufms.gitpay.domain.util;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

class ValidadorString extends Validador<String, ValidadorString> {

    private final String valorTratado;

    /**
     * Cria um objeto ValidadorString.
     * Esta classe possui métodos comumente utilizados para implementar validações
     *
     * @param campo nome do campo
     * @param valor valor
     */
    ValidadorString(String campo, String valor) {
        this(campo, valor, true);
    }

    /**
     * Cria um objeto ValidadorString.
     * Esta classe possui métodos comumente utilzados para implementar validações.
     * Por padrão, o valor de entrada tem os espaços em branco retirados do início e final
     * da string por meio da função trim(). Para manter os espaços em branco use:
     * ValidadorString(campo, valor, null)
     *
     * @param campo       nome do campo
     * @param valor       valor
     * @param obrigatorio verdadeiro quando o campo é obrigatório
     */
    ValidadorString(String campo, String valor, boolean obrigatorio) {
        this(campo, valor, obrigatorio, v -> Optional.ofNullable(v).map(String::trim).orElse(null));
    }

//    // Assim com o Optional não fica melhor???
//    ValidadorString(String campo, String valor, Function<Optional<String>, String> converterEntrada);

    ValidadorString(String campo, String valor, Function<String, String> converterEntrada) {
        this(campo, valor, true, converterEntrada);
    }

    /**
     * Cria um objeto ValidadorString.
     * Esta classe possui métodos comumente utilzados para implementar validações.
     * Por padrão, o valor de entrada tem os espaços em branco retirados do início e final
     * da string por meio da função trim(). Para manter os espaços em branco use:
     * ValidadorString(campo, valor, null)
     *
     * @param campo            nome do campo
     * @param valor            valor
     * @param obrigatorio      verdadeiro quando o campo é obrigatório
     * @param converterEntrada função de conversão da entrada. Pode ser útil, por exemplo, ao validar um CPF:
     *                         021.357.301-65 -> 02135730165. Dessa forma, o validador receberá o valor já convertido.
     */
    ValidadorString(String campo, String valor, boolean obrigatorio, Function<String, String> converterEntrada) {
        super(campo, Optional.ofNullable(converterEntrada).map(f -> f.apply(valor)).orElse(valor), obrigatorio);

        this.valorTratado = Optional.ofNullable(this.valor).orElse("");
    }

    @Override
    public ValidadorString validarNulo() {
        if (obrigatorio && valor == null) {
            throw new IllegalArgumentException(campo + " com valor nulo");
        } else if (obrigatorio && valor.isEmpty()) {
            throw new IllegalArgumentException(campo + " com valor em branco");
        }
        return this;
    }

    public ValidadorString validarTamanho(int min, int max) {
        int len = getValor().length();
        if ((obrigatorio || len > 0) && (len < min || len > max)) {
            throw new IllegalArgumentException(String.format("%s deve conter %s caracter%s", campo,
                    min < max ? "entre " + min + " e " + max : max, max > 1 ? "es" : ""));
        }
        return this;
    }

    public ValidadorString validarPorExpressao(String regex) {
        return validarPorExpressao(regex, null);
    }

    public ValidadorString validarPorExpressao(String regex, String msgErro) {
        if (obrigatorio && regex != null && !getValor().matches(regex)) {
            msgErro = msgErro != null ? "\n" + msgErro.trim() : "";
            throw new IllegalArgumentException(String.format("%s inválido: [%s]%s", campo, valor, msgErro));
        }
        return this;
    }

    public ValidadorString validarValor(Consumer<String> func) {
        return super.validar(s -> func.accept(getValor()));
    }

    public ValidadorString validarValor(BiConsumer<String, String> func) {
        return super.validar(s -> func.accept(campo, getValor()));
    }

    @Override
    public String getValor() {
        return valorTratado;
    }

    public String getValor(Function<String, String> func) {
        return func != null ? func.apply(valorTratado) : valorTratado;
    }

    public static String manterSomenteNumeros(String valor) {
        return valor != null ? valor.replaceAll("\\D", "") : "";
    }

    public static String removerEspacosAdicionais(String valor) {
        return valor != null ? valor.replaceAll("\\s+", " ") : "";
    }
}
