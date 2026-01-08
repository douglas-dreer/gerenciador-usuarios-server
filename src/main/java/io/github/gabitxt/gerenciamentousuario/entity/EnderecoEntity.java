package io.github.gabitxt.gerenciamentousuario.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "enderecos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EnderecoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private UsuarioEntity usuario;

    @Builder.Default
    private Integer numero = 0;
    private String cep;

    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String estado;
    private String regiao;

}
