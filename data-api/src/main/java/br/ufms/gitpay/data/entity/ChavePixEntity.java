package br.ufms.gitpay.data.entity;

import br.ufms.gitpay.domain.model.conta.TipoChavePix;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chave_pix")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonPropertyOrder({"id"})
public class ChavePixEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 60, unique = true)
    private String chave;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ContaRefEntity conta;

    @Enumerated(EnumType.STRING)
    private TipoChavePix tipo;
}
