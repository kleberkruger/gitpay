package br.ufms.gitpay.data.dto;

public record ContaDTO(
        String banco,
        int agencia,
        long numero,
        int digito,
        String tipoConta,
        String nomeTitular,
        String docTitular,
        String tipoPessoa
) {
}
