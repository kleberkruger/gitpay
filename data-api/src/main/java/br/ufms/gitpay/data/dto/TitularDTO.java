package br.ufms.gitpay.data.dto;

public record TitularDTO(
        String nome,
        String cpf, // ou CNPJ dependendo do tipo
        String telefone,
        String email,
        String dataNascimento,
        String razaoSocial,
        String senha,
        String tipo
) {
}
