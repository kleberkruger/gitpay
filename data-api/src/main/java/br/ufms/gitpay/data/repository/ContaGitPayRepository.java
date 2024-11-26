package br.ufms.gitpay.data.repository;

import br.ufms.gitpay.data.entity.ContaGitPayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaGitPayRepository extends JpaRepository<ContaGitPayEntity, Long> {
}
