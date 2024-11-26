package br.ufms.gitpay.data.dto;

public record ContaGitPayDTO(
        long numero,
        int digito,
        double saldo,
        double limite,
        TitularDTO titular
) {
}
