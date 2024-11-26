package br.ufms.gitpay.data.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("SAQUE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"id", "valor", "origem", "tipo"})
public class SaqueEntity extends TransacaoEntity {

    @OneToOne
    @JoinColumn(nullable = false)
    private ContaRefEntity origem;
}
