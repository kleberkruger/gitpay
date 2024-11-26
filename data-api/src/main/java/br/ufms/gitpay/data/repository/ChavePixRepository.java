package br.ufms.gitpay.data.repository;

import br.ufms.gitpay.data.entity.ChavePixEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChavePixRepository extends JpaRepository<ChavePixEntity, Long> {
}
