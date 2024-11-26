package br.ufms.gitpay.data.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "banco")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonPropertyOrder({"codigo", "nome", "razaoSocial"})
public class BancoEntity {

    @Id
    @Column(nullable = false, length = 3, unique = true)
    private String codigo;

    @Column(nullable = false, length = 60)
    private String nome;

    @Column(nullable = false, length = 60)
    private String razaoSocial;
}
