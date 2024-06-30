package br.ufms.gitpay.domain.util;

import java.time.LocalDate;

public class Validar {

    /**
     * Retorna o valor do atributo validado.
     *
     * @param atributo    nome do atributo
     * @param valor       valor do atributo
     * @param min         número mínimo de caracteres
     * @param max         número máximo de caracteres
     * @param obrigatorio verdadeiro quando o valor deste atributo é obrigatório
     * @return o valor do atributo validado
     */
    private static String validarNuloTamanho(String atributo, String valor, int min, int max, boolean obrigatorio) {
        return validarNuloTamanhoCaracteres(atributo, valor, min, max, null, null, obrigatorio);
    }

    /**
     * Retorna o valor do atributo validado.
     *
     * @param atributo    nome do atributo
     * @param valor       valor do atributo
     * @param min         número mínimo de caracteres
     * @param max         número máximo de caracteres
     * @param regex       expressão regular para validar o valor deste atributo
     * @param obrigatorio verdadeiro quando o valor deste atributo é obrigatório
     * @return o valor do atributo validado
     */
    private static String validarNuloTamanhoCaracteres(String atributo, String valor, int min, int max,
                                                       String regex, boolean obrigatorio) {
        return validarNuloTamanhoCaracteres(atributo, valor, min, max, regex, null, obrigatorio);
    }

    /**
     * Retorna o valor do atributo validado.
     *
     * @param atributo     nome do atributo
     * @param valor        valor do atributo
     * @param min          número mínimo de caracteres
     * @param max          número máximo de caracteres
     * @param regex        expressão regular para validar o valor deste atributo
     * @param regexMsgErro complemento da mensagem de erro (opcional)
     * @param obrigatorio  verdadeiro quando o valor deste atributo é obrigatório
     * @return o valor do atributo validado
     */
    private static String validarNuloTamanhoCaracteres(String atributo, String valor, int min, int max,
                                                       String regex, String regexMsgErro, boolean obrigatorio) {
        valor = valor != null ? valor.trim() : "";
        if (valor.isEmpty() && (!obrigatorio || min == 0)) {
            return valor;
        }

        if (valor.isEmpty()) {
            throw new IllegalArgumentException(atributo + " nulo ou em branco");
        } else if (valor.length() < min || valor.length() > max) {
            throw new IllegalArgumentException(String.format("%s deve conter %s caracter%s", atributo,
                    min < max ? "entre " + min + " e " + max : max, max > 1 ? "es" : ""));
        } else if (regex != null && !valor.matches(regex)) {
            regexMsgErro = regexMsgErro != null ? ". " + regexMsgErro.trim() : "";
            throw new IllegalArgumentException(String.format("%s inválido: [%s]%s", atributo, valor, regexMsgErro));
        }

        return valor;
    }

    private static String validarNuloFormato(String atributo, String valor,
                                             String regex, boolean obrigatorio) {
        return validarNuloFormato(atributo, valor, regex, null, obrigatorio);
    }

    private static String validarNuloFormato(String atributo, String valor,
                                             String regex, String regexMsgErro, boolean obrigatorio) {
        valor = valor != null ? valor.trim() : "";
        if (valor.isEmpty() && !obrigatorio) {
            return valor;
        }

        if (regex != null && !valor.matches(regex)) {
            regexMsgErro = regexMsgErro != null ? ". " + regexMsgErro.trim() : "";
            throw new IllegalArgumentException(String.format("%s inválido: [%s]%s", atributo, valor, regexMsgErro));
        }

        return valor;
    }


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
        nome = validarNuloTamanhoCaracteres("Nome", nome, 3, 50, "^[a-zA-ZÀ-ÖØ-öø-ÿ -]+$",
                "Caracteres não permitidos", true);
        if (completo && nome.split(" ").length < 2) {
            throw new IllegalArgumentException("Nome incompleto. Informe o sobrenome");
        }
        return nome;
    }

    /**
     * Valida o nome de uma pessoa jurídica.
     *
     * @param nome nome
     * @return nome validado
     * @throws IllegalArgumentException caso o nome da empresa seja inválido
     */
    public static String nomeEmpresa(String nome) {
        return validarNuloTamanho("Nome", nome, 1, 50, true);
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
        return validarNuloTamanhoCaracteres("Razão social", razaoSocial, 3, 50,
                ".*[\\p{L}].*", "Informe ao menos uma letra", obrigatorio);
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
        String regex = "^(\\+55\\s\\d{2}\\s9\\d{4}-\\d{4}|\\+55\\s\\d{2}\\s[1-8]\\d{3}-\\d{4}|" +
                "\\(\\d{2}\\)\\s9\\d{4}-\\d{4}|\\(\\d{2}\\)\\s[1-8]\\d{3}-\\d{4}|\\d{2}9\\d{8}|\\d{2}[1-8]\\d{7})$";

        telefone = validarNuloFormato("Telefone", telefone, regex,
                "Use somente números ou algum padrão brasileiro válido", obrigatorio)
                .replaceAll("\\D", "");
        return telefone.substring(telefone.length() > 11 ? 2 : 0);
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
        return validarNuloTamanhoCaracteres("Email", email, 3, 50, regex, obrigatorio);
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
        return validarNuloTamanhoCaracteres("Usuário", usuario, 3, 30, regex, true);
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
        return senha.trim();
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
        if (data.isAfter(LocalDate.now()) || data.isBefore(LocalDate.now().minusYears(150))) {
            throw new IllegalArgumentException("Data de nascimento inválida");
        }
        return data;
    }

    /**
     * Valida um CPF.
     *
     * @param cpf data de nascimento
     * @return CPF validado
     * @throws IllegalArgumentException caso o CPF seja inválido
     */
    public static String cpf(String cpf) {
        cpf = validarNuloFormato("CPF", cpf, "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", true)
                .replaceAll("\\D", "");
        return cpf;
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

    public static String validarBonito(String valor, boolean obrigatorio) {
        return Validacao.builder("Atributo", valor)
                .validarNulo(obrigatorio)
                .validarTamanho(3, 50)
                .validar("");
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

            System.out.println(Validar.telefone("+55 55 93291-4816"));

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static class Validacao {

        private final String atributo;
        private final String valor;

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

        public String validar() {
            return valor != null ? valor.trim() : "";
        }

        public String validar(String regex) {
            return validar().trim().replaceAll(regex, "");
        }

        public static Validacao builder(String atributo, String valor) {
            return new Validacao(atributo, valor);
        }
    }
}
