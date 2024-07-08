package br.ufms.gitpay.domain.model.conta;

import br.ufms.gitpay.domain.model.usuario.TipoPessoa;

public interface ContaBancaria {

    TipoConta getTipoConta();

    String getBanco();

    int getAgencia();

    NumeroConta getNumeroDigito();

    String getNomeTitular();

    String getDocumentoTitular();

    TipoPessoa getTipoPessoa();

    default String getAgenciaStr() {
        return String.format("%04d", getAgencia());
    }

    default int getNumero() {
        return getNumeroDigito().getNumero();
    }

    default String getNumeroStr() {
        return getNumeroDigito().getNumeroDigitoStr();
    }

    default int getDigito() {
        return getNumeroDigito().getDigito();
    }
}
