package br.ufms.gitpay.data;

import br.ufms.gitpay.domain.model.conta.ContaBancaria;
import br.ufms.gitpay.domain.model.conta.DadosConta;

public interface ContaRepository extends Repository<ContaBancaria, DadosConta> {
}
