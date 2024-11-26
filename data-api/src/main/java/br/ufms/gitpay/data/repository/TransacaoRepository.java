package br.ufms.gitpay.data.repository;

import br.ufms.gitpay.data.entity.TransacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, Long> {
}
