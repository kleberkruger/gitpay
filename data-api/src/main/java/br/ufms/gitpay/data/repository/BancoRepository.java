package br.ufms.gitpay.data.repository;

import br.ufms.gitpay.data.entity.BancoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoRepository extends JpaRepository<BancoEntity, Short> {
}
