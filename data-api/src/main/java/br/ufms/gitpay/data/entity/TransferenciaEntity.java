package br.ufms.gitpay.data.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("TRANSFERENCIA")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"id", "valor", "origem", "destino", "tipo"})
public class TransferenciaEntity extends TransacaoEntity {

    @OneToOne
    @JoinColumn(nullable = false)
    private ContaRefEntity origem;

    @OneToOne
    @JoinColumn(nullable = false)
    private ContaRefEntity destino;
}
