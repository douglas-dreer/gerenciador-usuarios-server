package io.github.gabitxt.gerenciamentousuario.model;

import io.github.gabitxt.gerenciamentousuario.enums.TipoDocumento;
import lombok.*;
// classe responsavel pela convercao de usuario DTO em usuario entiy
//neste caso essa clase seria responsavel pela
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
}
