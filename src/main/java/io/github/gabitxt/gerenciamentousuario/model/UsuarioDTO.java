package io.github.gabitxt.gerenciamentousuario.model;

import io.github.gabitxt.gerenciamentousuario.enums.TipoDocumento;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private EnderecoDTO endereco;
    private LocalDate dataNascimento;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
}
