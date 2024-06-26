package br.ufms.gitpay.domain.util;

import java.time.LocalDate;

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
//        return new ValidacaoString("Nome", nome, v -> v.trim().replaceAll("\\s+", " "))
//                .validarNuloEmBranco(true)
//                .validarPorExpressao("^[a-zA-ZÀ-ÖØ-öø-ÿ -]+$", "Caracteres não permitidos")
//                .validarTamanho(3, 50)
//                .validarValor(nomeCompleto -> {
//                    if (completo && nomeCompleto.split(" ").length < 2) {
//                        throw new IllegalArgumentException("Nome incompleto. Informe o sobrenome");
//                    }
//                }).getValor();

        return new ValidacaoString2("Nome", nome)
                .validarPorExpressao("^[a-zA-ZÀ-ÖØ-öø-ÿ -]+$", "Caracteres não permitidos")
                .validarTamanho(3, 50)
                .validarValor(nomeCompleto -> {
                    if (completo && nomeCompleto.split(" ").length < 2) {
                        throw new IllegalArgumentException("Nome incompleto. Informe o sobrenome");
                    }
                }).getValor(v -> v.replaceAll("\\s+", " "));
    }

    /**
     * Valida o nome de uma pessoa jurídica.
     *
     * @param nome nome
     * @return nome validado
     * @throws IllegalArgumentException caso o nome da empresa seja inválido
     */
    public static String nomeEmpresa(String nome) {
//        return new ValidacaoString("Nome", nome, v -> v.trim().replaceAll("\\s+", " "))
//                .validarNuloEmBranco(true)
//                .validarTamanho(1, 50)
//                .getValor();

        return new ValidacaoString2("Nome", nome)
                .validarTamanho(1, 50)
                .getValor(v -> v.replaceAll("\\s+", " "));
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
//        return new ValidacaoString("Razão social", razaoSocial, v -> v.trim().replaceAll("\\s+", " "))
//                .validarNuloEmBranco(obrigatorio)
//                .validarTamanho(obrigatorio, 3, 50)
//                .validarPorExpressao(".*[\\p{L}].*", "Informe ao menos uma letra")
//                .getValor();

        return new ValidacaoString2("Razão social", razaoSocial, obrigatorio)
                .validarTamanho(3, 50)
                .validarPorExpressao(".*[\\p{L}].*", "Informe ao menos uma letra")
                .getValor(v -> v.replaceAll("\\s+", " "));
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
        String regex = new StringBuilder()
                .append("\\d{2}[1-8]\\d{7}|")                   // ##########
                .append("\\d{2}9\\d{8}|")                       // ##9########
                .append("\\(\\d{2}\\)\\s[1-8]\\d{3}-\\d{4}|")   // (##) ####-####
                .append("\\(\\d{2}\\)\\s9\\d{4}-\\d{4}|")       // (##) 9####-####
                .append("\\+55\\s\\d{2}\\s[1-8]\\d{3}-\\d{4}|") // +55 ## ####-####
                .append("\\+55\\s\\d{2}\\s9\\d{4}-\\d{4}")      // +55 ## 9####-####
                .toString();

        // .trim()?
//        return new ValidacaoString("Telefone", telefone, t -> t.replaceAll("\\+55|\\D", ""))
//                .validarNuloEmBranco(obrigatorio)
//                .validarPorExpressao(regex, "Use somente números ou algum formato de telefone brasileiro válido")
//                .getValor();

        return new ValidacaoString2("Telefone", telefone, obrigatorio)
                .validarPorExpressao(regex, "Use somente números ou algum formato de telefone brasileiro válido")
                .getValor(t -> t.replaceAll("\\+55|\\D", ""));
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
//        return new ValidacaoString("Email", email, v -> v.trim())
//                .validarNuloEmBranco(obrigatorio)
//                .validarTamanho(3, 50)
//                .validarPorExpressao(regex)
//                .getValor();

        return new ValidacaoString2("Email", email, obrigatorio)
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
//        return new ValidacaoString("Usuário", usuario, v -> v.trim())
//                .validarNuloEmBranco(true)
//                .validarTamanho(3, 30)
//                .validarPorExpressao(regex)
//                .getValor();

        return new ValidacaoString2("Usuário", usuario)
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
//        return new ValidacaoString("Senha", senha)
//                .validarNuloEmBranco(true)
//                .validarTamanho(6, 30)
//                .validarPorExpressao(numerica ? "^\\d+$" : null)
//                .getValor();

        return new ValidacaoString2("Senha", senha, true, false)
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
//        return new Validacao<>("Data de nascimento", data)
//                .validarNulo(obrigatorio)
//                .validar(valor -> valor.ifPresent(date -> {
//                    if (date.isAfter(LocalDate.now()) || date.isBefore(LocalDate.now().minusYears(150))) {
//                        throw new IllegalArgumentException("Data de nascimento inválida");
//                    }
//                })).getValor();

        return new Validacao2<>("Data de nascimento", data, obrigatorio)
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
//        return new ValidacaoString("CPF", cpf, v -> v.replaceAll("\\D", ""))
//                .validarNuloEmBranco(true)
//                .validarPorExpressao("\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
//                .validarValor(Validar::validarCPF)
//                .getValor();

        return new ValidacaoString2("CPF", cpf)
                .validarPorExpressao("\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
                .validarValor(Validar::validarCPF)
                .getValor(v -> v.replaceAll("\\D", ""));
    }

    private static void validarCPF(String cpf) {
        int[] digitos = cpf.chars().map(Character::getNumericValue).toArray();

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += digitos[i] * (10 - i);
        }

        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : 11 - resto;

        if (digitos[9] != digitoVerificador1) {
            throw new IllegalArgumentException("CPF inválido: " + cpf);
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += digitos[i] * (11 - i);
        }

        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : 11 - resto;

        if (digitos[10] != digitoVerificador2) {
            throw new IllegalArgumentException("CPF inválido");
        }
    }

    /**
     * Valida um CNPJ.
     *
     * @param cnpj data de nascimento
     * @return CNPJ validado
     * @throws IllegalArgumentException caso o CNPJ seja inválido
     */
    public static String cnpj(String cnpj) {
//        return new ValidacaoString("CNPJ", cnpj, v -> v.replaceAll("\\D", ""))
//                .validarNuloEmBranco(true)
//                .validarPorExpressao("\\d{14}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")
//                .validarValor(Validar::validarCNPJ)
//                .getValor();

        return new ValidacaoString2("CNPJ", cnpj)
                .validarPorExpressao("\\d{14}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")
                .validarValor(Validar::validarCNPJ)
                .getValor(v -> v.replaceAll("\\D", ""));
    }

    private static void validarCNPJ(String cnpj) {
        int[] digitos = cnpj.chars().map(Character::getNumericValue).toArray();

        int soma = 0;
        int peso = 2;
        for (int i = 11; i >= 0; i--) {
            soma += digitos[i] * peso;
            peso++;
            if (peso == 10) peso = 2;
        }

        int digitoVerificador1 = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
        if (digitos[12] != digitoVerificador1) {
            throw new IllegalArgumentException("CNPJ inválido: " + cnpj);
        }

        soma = 0;
        peso = 2;
        for (int i = 12; i >= 0; i--) {
            soma += digitos[i] * peso;
            peso++;
            if (peso == 10) peso = 2;
        }

        int digitoVerificador2 = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
        if (digitos[13] != digitoVerificador2) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
    }

    /**
     * Valida o código do banco.
     *
     * @param codigo código do banco
     * @return código do banco validado e formatado com três dígitos
     * @throws IllegalArgumentException caso o código do banco seja inválido
     */
    public static String codigoBanco(int codigo) {
        if (codigo < 1 || codigo > 999) {
            throw new IllegalArgumentException("Código do banco inválido: [" + codigo + "]");
        }
        return String.format("%03d", codigo);
    }

    /**
     * Valida o código do banco.
     *
     * @param codigo código do banco
     * @return código do banco validado e formatado com três dígitos
     * @throws IllegalArgumentException caso o código do banco seja inválido
     */
    public static String codigoBanco(String codigo) {
//        return codigoBanco(Integer.parseInt(new ValidacaoString("Código do banco", codigo)
//                .validarNuloEmBranco(true)
//                .validarPorExpressao("^[+-]?\\d+$")
//                .getValor()));

        return codigoBanco(Integer.parseInt(new ValidacaoString2("Código do banco", codigo)
                .validarPorExpressao("^[+-]?\\d+$")
                .getValor()));
    }

    public static void main(String[] args) {
        try {
            System.out.println(Validar.nomePessoa("  kleber   kruger"));

            System.out.println(Validar.telefone("5532914816"));
            System.out.println(Validar.telefone("69996122809"));
            System.out.println(Validar.telefone("(69) 3291-4816"));
            System.out.println(Validar.telefone("(69) 99612-2809"));
            System.out.println(Validar.telefone("+55 55 3291-4816"));
            System.out.println(Validar.telefone("+55 55 99612-2809"));

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
