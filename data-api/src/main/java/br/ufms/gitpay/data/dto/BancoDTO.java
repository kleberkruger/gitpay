package br.ufms.gitpay.data.dto;

import br.ufms.gitpay.domain.model.banco.Banco;

public record BancoDTO(
        String codigo,
        String nome,
        String razaoSocial
) {
    public Banco toDomain() {
        return new Banco(codigo, nome, razaoSocial);
    }
}
