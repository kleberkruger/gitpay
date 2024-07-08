package br.ufms.gitpay.domain.util;

import br.ufms.gitpay.domain.model.conta.NumeroConta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
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
        return new ValidadorString("Nome", nome)
                .validarNulo()
                .validarPorExpressao("^[a-zA-ZÀ-ÖØ-öø-ÿ -]+$", "Caracteres não permitidos")
                .validarTamanho(3, 50)
                .validarValor(nomeCompleto -> {
                    if (completo && nomeCompleto.split(" ").length < 2) {
                        throw new IllegalArgumentException("Nome incompleto. Informe o sobrenome");
                    }
                }).getValor(ValidadorString::removerEspacosAdicionais);
    }

    /**
     * Valida o nome de uma pessoa jurídica.
     *
     * @param nome nome
     * @return nome validado
     * @throws IllegalArgumentException caso o nome da empresa seja inválido
     */
    public static String nomeEmpresa(String nome) {
        return new ValidadorString("Nome", nome)
                .validarNulo()
                .validarTamanho(1, 50)
                .getValor(ValidadorString::removerEspacosAdicionais);
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
        return new ValidadorString("Razão social", razaoSocial, obrigatorio)
                .validarNulo()
                .validarTamanho(3, 80)
                .validarPorExpressao(".*[\\p{L}].*", "Informe ao menos uma letra")
                .getValor(ValidadorString::removerEspacosAdicionais);
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

        return new ValidadorString("Telefone", telefone, obrigatorio)
                .validarNulo()
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
        return new ValidadorString("Email", email, obrigatorio)
                .validarNulo()
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
        return new ValidadorString("Usuário", usuario)
                .validarNulo()
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
        return new ValidadorString("Senha", senha, null)
                .validarNulo()
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
        return estaEntrePeriodo("Data de nascimento", LocalDateTime.now().minusYears(150), data.atStartOfDay(),
                LocalDateTime.now(), obrigatorio).toLocalDate();
    }

    /**
     * Valida se uma data e hora está entre um determinado período
     *
     * @param campo           Nome do campo
     * @param dataHoraInicial data e hora inicial
     * @param dataHora        data e hora a ser validada
     * @param dataHoraFinal   data e hora final
     * @return a data e hora validada
     */
    public static LocalDateTime estaEntrePeriodo(String campo, LocalDateTime dataHoraInicial, LocalDateTime dataHora,
                                                 LocalDateTime dataHoraFinal) {
        return estaEntrePeriodo(campo, dataHoraInicial, dataHora, dataHoraFinal, true);
    }

    /**
     * Valida se uma data e hora está entre um determinado período
     *
     * @param campo           Nome do campo
     * @param dataHoraInicial data e hora inicial
     * @param dataHora        data e hora a ser validada
     * @param dataHoraFinal   data e hora final
     * @param obrigatorio     verdadeiro caso o campo seja obrigatório
     * @return a data e hora validada
     */
    public static LocalDateTime estaEntrePeriodo(String campo, LocalDateTime dataHoraInicial, LocalDateTime dataHora,
                                                 LocalDateTime dataHoraFinal, boolean obrigatorio) {
        return new Validador<>(Objects.requireNonNull(campo, "Nome do campo nulo"), dataHora, obrigatorio)
                .validar((attr, date) -> date.ifPresent(d -> {
                    if (d.isAfter(dataHoraFinal) || d.isBefore(dataHoraInicial)) {
                        throw new IllegalArgumentException(attr + " inválida");
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
        return new ValidadorString("CPF", cpf)
                .validarNulo()
                .validarPorExpressao("\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
                .validarValor(Validar::validarCPF)
                .getValor(ValidadorString::manterSomenteNumeros);
    }

    private static void validarCPF(String cpf) {
        if (cpf.matches("^(\\d)\\1*$")) {
            throw new IllegalArgumentException("CPF inválido: " + cpf);
        }

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
        return new ValidadorString("CNPJ", cnpj)
                .validarNulo()
                .validarPorExpressao("\\d{14}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")
                .validarValor(Validar::validarCNPJ)
                .getValor(ValidadorString::manterSomenteNumeros);
    }

    private static void validarCNPJ(String cnpj) {
        if (cnpj.matches("^(\\d)\\1*$")) {
            throw new IllegalArgumentException("CNPJ inválido: " + cnpj);
        }

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

    public static String chaveAleatoriaPix(String chave) {
        return new ValidadorString("Chave Pix", chave)
                .validarNulo()
                .validarPorExpressao("^[a-zA-Z0-9]$")
                .validarTamanho(32)
                .getValor();
    }

    /**
     * Valida o formato de uma chave pix
     *
     * @param chave chave pix
     * @return a chave pix validada
     */
    public static String chavePix(String chave) {
        String regex = new StringBuilder()
                .append("\\d{11}|\\d{14}|")                                     // CPF e CNPJ
                .append("\\d{2}[1-8]\\d{7}|")                                   // Telefone: ##########
                .append("\\d{2}9\\d{8}|")                                       // Telefone: ##9########
                .append("\\+55\\d{2}[1-8]\\d{7}|")                              // Telefone: +55##########
                .append("\\+55\\d{2}9\\d{8}|")                                  // Telefone: +55##9########
                .append("^\\+(?!55)\\d{2}\\d{6,}$|")                            // Telefone internacional: +########
                .append("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$|")  // Email
                .append("^[a-zA-Z0-9]{32}$")                                    // Aleatória
                .toString();

        return new ValidadorString("Chave Pix", chave)
                .validarNulo()
                .validarPorExpressao(regex)
                .getValor();
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
        return codigoBanco(Integer.parseInt(new ValidadorString("Código do banco", codigo)
                .validarNulo()
                .validarPorExpressao("^[+-]?\\d+$")
                .getValor()));
    }

    /**
     * Valida o número da agência
     *
     * @param numero número da agência
     * @return o número da agência
     */
    public static int numeroAgencia(int numero) {
        if (numero < 1 || numero > 99999) {
            throw new IllegalArgumentException("Número de agência inválido: [" + numero + "]");
        }
        return numero;
    }

    /**
     * Valida o número da conta
     *
     * @param numeroDigito número da conta com dígito
     * @return o número da conta com dígito
     */
    public static String numeroConta(String numeroDigito) {
        return numeroConta(numeroDigito, null);
    }

    /**
     * Valida o número da conta
     *
     * @param numeroDigito número da conta com dígito
     * @param verificador  função de verificação
     * @return o número da conta com dígito
     */
    public static String numeroConta(String numeroDigito, Function<Integer, Integer> verificador) {
        return new ValidadorString("Número da conta", numeroDigito)
                .validarNulo()
                .validarPorExpressao("\\d+-\\d")
                .validarValor(valor -> {
                    int[] num = Arrays.stream(valor.split("-")).mapToInt(Integer::parseInt).toArray();
                    numeroConta(num[0], num[1], verificador);
                }).getValor();
    }

    /**
     * Valida o número da conta
     *
     * @param numero número
     * @param digito dígito
     * @return o número da conta com dígito
     */
    public static String numeroConta(int numero, int digito) {
        return numeroConta(numero, digito, null);
    }

    /**
     * Valida o número da conta
     *
     * @param numero      número
     * @param digito      dígito
     * @param verificador função de verificação de dígito
     * @return o número da conta com dígito
     */
    public static String numeroConta(int numero, int digito, Function<Integer, Integer> verificador) {
        String numeroDigito = NumeroConta.formatar(numero, digito);
        if (numero < 1 || numero > 99999999) {
            throw new IllegalArgumentException("Número inválido: [" + numeroDigito + "]");
        } else if ((digito < 0 || digito > 9) || (verificador != null && verificador.apply(numero) != digito)) {
            throw new IllegalArgumentException("Dígito inválido: [" + numeroDigito + "]");
        }
        return numeroDigito;
    }
}
