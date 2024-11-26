package br.ufms.gitpay.data.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@DiscriminatorValue("PESSOA_FISICA")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"id", "nome", "documento", "telefone", "email", "dataNascimento", "senha", "tipo"})
public class PessoaFisicaEntity extends UsuarioEntity {

    @Column(nullable = false)
    private LocalDate dataNascimento;
}
