package br.ufms.gitpay.data.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("PESSOA_JURIDICA")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"id", "nome", "razaoSocial", "documento", "telefone", "email", "senha", "tipo"})
public class PessoaJuridicaEntity extends UsuarioEntity {

    @Column(nullable = false, length = 60)
    private String razaoSocial;
}
