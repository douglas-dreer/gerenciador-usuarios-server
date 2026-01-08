package io.github.gabitxt.gerenciamentousuario.model;

import io.github.gabitxt.gerenciamentousuario.enums.TipoDocumento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados do usuário")
public class UsuarioDTO {

    @Schema(description = "ID do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String nome;

    @Schema(description = "Email do usuário", example = "joao@email.com")
    private String email;

    @Schema(description = "Endereço do usuário")
    private EnderecoDTO endereco;

    @Schema(description = "Data de nascimento", example = "1990-05-15")
    private LocalDate dataNascimento;

    @Schema(description = "Tipo do documento", example = "CPF")
    private TipoDocumento tipoDocumento;

    @Schema(description = "Número do documento", example = "12345678901")
    private String numeroDocumento;
}
