package br.ufms.gitpay.data;

import br.ufms.gitpay.domain.model.transacao.Transacao;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface TransacaoRepository extends Repository<Transacao, String> {

    CompletableFuture<Collection<Transacao>> getAll(int numeroConta);
}
