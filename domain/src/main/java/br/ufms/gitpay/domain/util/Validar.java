package br.ufms.gitpay.domain.util;

import java.time.LocalDate;
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
        return new ValidacaoString("Nome", nome)
                .validarNuloEmBranco(true)
                .validarPorExpressao("^[a-zA-ZÀ-ÖØ-öø-ÿ -]+$", "Caracteres não permitidos")
                .validarTamanho(3, 50)
                .validar(valor -> valor.ifPresent(nomeCompleto -> {
                    if (completo && nomeCompleto.split(" ").length < 2) {
                        throw new IllegalArgumentException("Nome incompleto. Informe o sobrenome");
                    }
                })).getValor();
    }

    /**
     * Valida o nome de uma pessoa jurídica.
     *
     * @param nome nome
     * @return nome validado
     * @throws IllegalArgumentException caso o nome da empresa seja inválido
     */
    public static String nomeEmpresa(String nome) {
        return new ValidacaoString("Nome", nome)
                .validarNuloEmBranco(true)
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
        return new ValidacaoString("Razão social", razaoSocial)
                .validarNuloEmBranco(obrigatorio)
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

        return new ValidacaoString("Telefone", telefone)
                .validarNuloEmBranco(obrigatorio)
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
        return new ValidacaoString("Email", email)
                .validarNuloEmBranco(obrigatorio)
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
        return new ValidacaoString("Usuário", usuario)
                .validarNuloEmBranco(true)
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
        return new ValidacaoString("Senha", senha)
                .validarNuloEmBranco(true)
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
        return new Validacao<>("Data de nascimento", data)
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

    private static class Validacao<T> {

        protected final String atributo;
        protected final T valor;

        public Validacao(String atributo, T valor) {
            this.atributo = atributo;
            this.valor = valor;
        }

        public Validacao<T> validarNulo(boolean obrigatorio) {
            if (valor == null) {
                throw new IllegalArgumentException(atributo + " com valor nulo");
            }
            return this;
        }

        public Validacao<T> validar(Consumer<Optional<T>> func) {
            if (func != null) func.accept(Optional.ofNullable(valor));
            return this;
        }

        public Validacao<T> validar(BiConsumer<String, Optional<T>> func) {
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

    private static class ValidacaoString extends Validacao<String> {

        public ValidacaoString(String atributo, String valor) {
            super(atributo, valor);
        }

        public ValidacaoString validarNuloEmBranco(boolean obrigatorio) {
//            super.validarNulo(obrigatorio);
//            if (obrigatorio && valor.isBlank()) {
//                throw new IllegalArgumentException(atributo + " em branco");
//            }
//            return this;

            return (ValidacaoString) super.validarNulo(obrigatorio).validar(opt -> opt.ifPresent(valor -> {
                if (obrigatorio && valor.isBlank()) {
                    throw new IllegalArgumentException(atributo + " em branco");
                }
            }));
        }

        public ValidacaoString validarTamanho(int min, int max) {
            int len = valor != null ? this.valor.trim().length() : 0;
            if (len < min || len > max) {
                throw new IllegalArgumentException(String.format("%s deve conter %s caracter%s", atributo,
                        min < max ? "entre " + min + " e " + max : max, max > 1 ? "es" : ""));
            }
            return this;
        }

        public ValidacaoString validarPorExpressao(String regex) {
            return validarPorExpressao(regex, null);
        }

        public ValidacaoString validarPorExpressao(String regex, String msgErro) {
            if (regex != null && !valor.matches(regex)) {
                msgErro = msgErro != null ? "\n" + msgErro.trim() : "";
                throw new IllegalArgumentException(String.format("%s inválido: [%s]%s", atributo, valor, msgErro));
            }
            return this;
        }

        @Override
        public String getValor(Function<String, String> func) {
            String valor = this.valor != null ? this.valor.trim() : "";
            return func != null ? func.apply(valor) : valor;
        }
    }

    public static void main(String[] args) {
        try {
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
