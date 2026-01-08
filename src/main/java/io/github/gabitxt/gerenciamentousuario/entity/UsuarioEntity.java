package io.github.gabitxt.gerenciamentousuario.entity;

import io.github.gabitxt.gerenciamentousuario.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;

    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @OneToOne(cascade = CascadeType.ALL)
    private EnderecoEntity endereco;

    @Column(nullable = false)
    private String numeroDocumento;
}
