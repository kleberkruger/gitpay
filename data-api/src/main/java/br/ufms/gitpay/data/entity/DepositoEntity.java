package br.ufms.gitpay.data.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("DEPOSITO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"id", "valor", "destino", "tipo"})
public class DepositoEntity extends TransacaoEntity {

    @OneToOne
    @JoinColumn(nullable = false)
    private ContaRefEntity destino;
}
