package br.ufms.gitpay.data.repository;

import br.ufms.gitpay.data.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
}
