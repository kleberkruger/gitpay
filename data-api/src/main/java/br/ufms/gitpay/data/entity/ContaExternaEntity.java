package br.ufms.gitpay.data.entity;

import br.ufms.gitpay.domain.model.conta.TipoConta;
import jakarta.persistence.*;

@Entity
@Table(name = "conta_externa", uniqueConstraints = {
        @UniqueConstraint(name = "conta_uq", columnNames = {"banco", "agencia", "numero"})
})
public class ContaExternaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 3)
    private String banco;

    @Column(nullable = false)
    private Integer agencia;

    @Column(nullable = false)
    private Long numero;

    @Column(nullable = false)
    private Short digito;

    @Column(nullable = false, length = 14)
    private String docTitular;

    @Column(nullable = false, length = 60)
    private String nomeTitular;

    @Enumerated(EnumType.STRING)
    private TipoConta email;

    @OneToOne(cascade = CascadeType.ALL)
    private ContaRefEntity conta;
}
