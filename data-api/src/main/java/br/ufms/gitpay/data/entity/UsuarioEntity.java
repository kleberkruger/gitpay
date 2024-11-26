package br.ufms.gitpay.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false, length = 60)
    protected String nome;

    @Column(nullable = false, length = 14, unique = true)
    protected String documento;

    @Column(nullable = false, length = 11, unique = true)
    protected String telefone;

    @Column(length = 60, unique = true)
    protected String email;

    @Column(nullable = false, length = 40)
    protected String senha;
}
