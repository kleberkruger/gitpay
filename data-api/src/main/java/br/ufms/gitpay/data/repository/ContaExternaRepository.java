package br.ufms.gitpay.data.repository;

import br.ufms.gitpay.data.entity.ContaExternaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaExternaRepository extends JpaRepository<ContaExternaEntity, Long> {
}
