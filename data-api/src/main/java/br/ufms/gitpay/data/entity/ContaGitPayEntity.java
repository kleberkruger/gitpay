package br.ufms.gitpay.data.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "conta_gitpay")
public class ContaGitPayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long numero;

    @Column(nullable = false)
    private Short digito;

    @Column(nullable = false, precision = 24, scale = 2)
    private BigDecimal saldo;

    @Column(nullable = false, precision = 24, scale = 2)
    private BigDecimal limite;

    @ManyToOne
    private UsuarioEntity usuario;

    @OneToOne(cascade = CascadeType.ALL)
    private ContaRefEntity referencia;
}
