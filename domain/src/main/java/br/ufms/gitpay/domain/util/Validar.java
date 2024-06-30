package br.ufms.gitpay.domain.util;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class Validar {

    /**
     * Valida o nome de uma pessoa física.
     *
     * @param nome nome
     * @return nome validado
     * @throws IllegalArgumentException caso o nome da pessoa seja inválido
     */
    public static String nomePessoa(String nome) {
        return nomePessoa(nome, false);
    }

    /**
     * Valida o nome de uma pessoa física.
     *
     * @param nome     nome
     * @param completo verdadeiro quando for necessário nome e sobrenome
     * @return nome validado
     * @throws IllegalArgumentException caso o nome da pessoa seja inválido
     */
    public static String nomePessoa(String nome, boolean completo) {
        return new Validacao("Nome", nome)
                .validarNulo(true)
                .validarPorExpressao("^[a-zA-ZÀ-ÖØ-öø-ÿ -]+$", "Caracteres não permitidos")
                .validarTamanho(3, 50)
                .validar(valor -> {
                    if (completo && valor.split(" ").length < 2) {
                        throw new IllegalArgumentException("Nome incompleto. Informe o sobrenome");
                    }
                }).getValor();
    }

    /**
     * Valida o nome de uma pessoa jurídica.
     *
     * @param nome nome
     * @return nome validado
     * @throws IllegalArgumentException caso o nome da empresa seja inválido
     */
    public static String nomeEmpresa(String nome) {
        return new Validacao("Nome", nome)
                .validarNulo(true)
                .validarTamanho(1, 50)
                .getValor();
    }

    /**
     * Valida a razão social de uma pessoa jurídica.
     *
     * @param razaoSocial razão social
     * @return razão social validada
     * @throws IllegalArgumentException caso a razão social seja inválida
     */
    public static String razaoSocial(String razaoSocial) {
        return razaoSocial(razaoSocial, true);
    }

    /**
     * Valida a razão social de uma pessoa jurídica.
     *
     * @param razaoSocial razão social
     * @param obrigatorio verdadeiro caso o valor seja obrigatório
     * @return razão social validada
     * @throws IllegalArgumentException caso a razão social seja inválida
     */
    public static String razaoSocial(String razaoSocial, boolean obrigatorio) {
        return new Validacao("Razão social", razaoSocial)
                .validarNulo(obrigatorio)
                .validarTamanho(3, 50)
                .validarPorExpressao(".*[\\p{L}].*", "Informe ao menos uma letra")
                .getValor();
    }

    /**
     * Valida um número de telefone.
     *
     * @param telefone número de telefone
     * @return número de telefone validado
     * @throws IllegalArgumentException caso o número de telefone seja inválido
     */
    public static String telefone(String telefone) {
        return telefone(telefone, true);
    }

    /**
     * Valida um número de telefone.
     *
     * @param telefone    número de telefone
     * @param obrigatorio verdadeiro caso o valor seja obrigatório
     * @return número de telefone validado
     * @throws IllegalArgumentException caso o número de telefone seja inválido
     */
    public static String telefone(String telefone, boolean obrigatorio) {
//        String regex = "^(\\+55\\s\\d{2}\\s9\\d{4}-\\d{4}|\\+55\\s\\d{2}\\s[1-8]\\d{3}-\\d{4}|" +
//                "\\(\\d{2}\\)\\s9\\d{4}-\\d{4}|\\(\\d{2}\\)\\s[1-8]\\d{3}-\\d{4}|\\d{2}9\\d{8}|\\d{2}[1-8]\\d{7})$";

        String regex = new StringBuilder()
                .append("\\d{2}[1-8]\\d{7}|")                   // ##########
                .append("\\d{2}9\\d{8}|")                       // ##9########
                .append("\\(\\d{2}\\)\\s[1-8]\\d{3}-\\d{4}|")   // (##) ####-####
                .append("\\(\\d{2}\\)\\s9\\d{4}-\\d{4}|")       // (##) 9####-####
                .append("\\+55\\s\\d{2}\\s[1-8]\\d{3}-\\d{4}|") // +55 ## ####-####
                .append("\\+55\\s\\d{2}\\s9\\d{4}-\\d{4}")      // +55 ## 9####-####
                .toString();

        return new Validacao("Telefone", telefone)
                .validarNulo(obrigatorio)
                .validarPorExpressao(regex, "Use somente números ou algum formato de telefone brasileiro válido")
                .getValor(tel -> {
                    tel = tel.replaceAll("\\D", "");
                    return tel.substring(tel.length() > 11 ? 2 : 0);
                });
    }

    /**
     * Valida um email.
     *
     * @param email email
     * @return email validado
     * @throws IllegalArgumentException caso o email seja inválido
     */
    public static String email(String email) {
        return email(email, true);
    }

    /**
     * Valida um email.
     *
     * @param email       email
     * @param obrigatorio verdadeiro caso o valor seja obrigatório
     * @return email validado
     * @throws IllegalArgumentException caso o email seja inválido
     */
    public static String email(String email, boolean obrigatorio) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return new Validacao("Email", email)
                .validarNulo(obrigatorio)
                .validarTamanho(3, 50)
                .validarPorExpressao(regex)
                .getValor();
    }

    /**
     * Valida um nome de usuário.
     *
     * @param usuario nome de usuário
     * @return nome de usuário validado
     * @throws IllegalArgumentException caso o nome de usuário seja inválido
     */
    public static String usuario(String usuario) {
        String regex = "^(?!.*([._])\\1)(?!.*\\.$)(?!^\\.)[a-zA-Z0-9_]+(?:[._][a-zA-Z0-9_]+)*_?$";
        return new Validacao("Usuário", usuario)
                .validarNulo(true)
                .validarTamanho(3, 30)
                .validarPorExpressao(regex)
                .getValor();
    }

    /**
     * Valida uma senha.
     *
     * @param senha senha
     * @return senha validada
     * @throws IllegalArgumentException caso a senha seja inválida
     */
    public static String senha(String senha) {
        return senha(senha, false);
    }

    /**
     * Valida uma senha.
     *
     * @param senha    senha
     * @param numerica verdadeiro caso seja uma senha numérica
     * @return senha validada
     * @throws IllegalArgumentException caso a senha seja inválida
     */
    public static String senha(String senha, boolean numerica) {
        return new Validacao("Senha", senha)
                .validarNulo(true)
                .validarTamanho(6, 30)
                .validarPorExpressao(numerica ? "^\\d+$" : null)
                .getValor();
    }

    /**
     * Valida uma data de nascimento.
     *
     * @param dataNascimento data de nascimento
     * @return data de nascimento validada
     * @throws IllegalArgumentException caso a data de nascimento seja inválida
     */
    public static LocalDate dataNascimento(LocalDate dataNascimento) {
        return dataNascimento(dataNascimento, true);
    }

    /**
     * Valida uma data de nascimento.
     *
     * @param data        data de nascimento
     * @param obrigatorio verdadeiro caso o valor seja obrigatório
     * @return data de nascimento validada
     * @throws IllegalArgumentException caso a data de nascimento seja inválida
     */
    public static LocalDate dataNascimento(LocalDate data, boolean obrigatorio) {
        return new ValidacaoBase<>("Data de nascimento", data)
                .validarNulo(obrigatorio)
                .validar(valor -> valor.ifPresent(date -> {
                    if (date.isAfter(LocalDate.now()) || date.isBefore(LocalDate.now().minusYears(150))) {
                        throw new IllegalArgumentException("Data de nascimento inválida");
                    }
                })).getValor();
    }

    /**
     * Valida um CPF.
     *
     * @param cpf data de nascimento
     * @return CPF validado
     * @throws IllegalArgumentException caso o CPF seja inválido
     */
    public static String cpf(String cpf) {
//        cpf = validarNuloFormato("CPF", cpf, "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", true)
//                .replaceAll("\\D", "");
//        return cpf;
        return null;
    }

    /**
     * Valida um CNPJ.
     *
     * @param cnpj data de nascimento
     * @return CNPJ validado
     * @throws IllegalArgumentException caso o CNPJ seja inválido
     */
    public static String cnpj(String cnpj) {
        return null;
    }

    /**
     * Valida o código do banco.
     *
     * @param codigo código do banco
     * @return código do banco validado e formatado com três dígitos
     * @throws IllegalArgumentException caso o código do banco seja inválido
     */
    public static String codigoBanco(int codigo) {
        return null;
    }

    /**
     * Valida o código do banco.
     *
     * @param codigo código do banco
     * @return código do banco validado e formatado com três dígitos
     * @throws IllegalArgumentException caso o código do banco seja inválido
     */
    public static String codigoBanco(String codigo) {
        return codigoBanco(Integer.parseInt(codigo));
    }

    private static class ValidacaoBase<T> {

        protected final String atributo;
        protected final T valor;

        public ValidacaoBase(String atributo, T valor) {
            this.atributo = atributo;
            this.valor = valor;
        }

        public ValidacaoBase<T> validarNulo(boolean obrigatorio) {
            if (valor == null) {
                throw new IllegalArgumentException(atributo + " com valor nulo");
            }
            return this;
        }

        public ValidacaoBase<T> validar(Consumer<Optional<T>> func) {
            if (func != null) func.accept(Optional.ofNullable(valor));
            return this;
        }

        public ValidacaoBase<T> validar(BiConsumer<String, Optional<T>> func) {
            if (func != null) func.accept(atributo, Optional.ofNullable(valor));
            return this;
        }

        public T getValor() {
            return getValor(null);
        }

        public T getValor(Function<T, T> func) {
            return func != null ? func.apply(valor) : valor;
        }
    }

    private static class Validacao {

        protected final String atributo;
        protected final String valor;

        public Validacao(String atributo, String valor) {
            this.atributo = atributo;
            this.valor = valor;
        }

        public Validacao validarNulo(boolean obrigatorio) {
            if (obrigatorio && (valor == null || valor.isBlank())) {
                throw new IllegalArgumentException(atributo + (valor == null ? " nulo" : " em branco"));
            }
            return this;
        }

        public Validacao validarTamanho(int min, int max) {
            int len = valor != null ? this.valor.trim().length() : 0;
            if (len < min || len > max) {
                throw new IllegalArgumentException(String.format("%s deve conter %s caracter%s", atributo,
                        min < max ? "entre " + min + " e " + max : max, max > 1 ? "es" : ""));
            }
            return this;
        }

        public Validacao validarPorExpressao(String regex) {
            return validarPorExpressao(regex, null);
        }

        public Validacao validarPorExpressao(String regex, String msgErro) {
            if (regex != null && !valor.matches(regex)) {
                msgErro = msgErro != null ? "\n" + msgErro.trim() : "";
                throw new IllegalArgumentException(String.format("%s inválido: [%s]%s", atributo, valor, msgErro));
            }
            return this;
        }

        public Validacao validar(Consumer<String> func) {
            if (func != null) func.accept(valor);
            return this;
        }

        public Validacao validar(BiConsumer<String, String> func) {
            if (func != null) func.accept(atributo, valor);
            return this;
        }

        public String getValor() {
            return getValor(null);
        }

        public String getValor(Function<String, String> func) {
            String valor = this.valor != null ? this.valor.trim() : "";
            return func != null ? func.apply(valor) : valor;
        }
    }

    public static void main(String[] args) {
        try {
            // correto
//        System.out.println(validarNuloTamanhoCaracteres("Razão social", "", 3, 50, null, null, false));
            // erro: len < min
//        System.out.println(validarNuloTamanhoCaracteres("Razão social", "ba", 3, 50, null, null, false));
            // erro: nulo
//        System.out.println(validarNuloTamanhoCaracteres("Razão social", "", 3, 50, null, null, true));
            // erro: len < min
//        System.out.println(validarNuloTamanhoCaracteres("Razão social", "ba", 3, 50, null, null, true));

            System.out.println(Validar.telefone("6732914816"));
            System.out.println(Validar.telefone("67996122809"));
            System.out.println(Validar.telefone("(67) 3291-4816"));
            System.out.println(Validar.telefone("(67) 99612-2809"));
            System.out.println(Validar.telefone("+55 67 3291-4816"));
            System.out.println(Validar.telefone("+55 67 99612-2809"));

            dataNascimento(null);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
