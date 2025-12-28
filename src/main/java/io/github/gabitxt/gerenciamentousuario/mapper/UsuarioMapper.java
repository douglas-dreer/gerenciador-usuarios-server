package io.github.gabitxt.gerenciamentousuario.mapper;

import io.github.gabitxt.gerenciamentousuario.entity.UsuarioEntity;
import io.github.gabitxt.gerenciamentousuario.model.UsuarioDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    /**
     * Converte uma entidade UsuarioEntity para um DTO UsuarioDTO
     * @param entity
     * @return UsuarioDTO
     */
    public UsuarioDTO convertToDomain(@NotNull UsuarioEntity entity) {
       return UsuarioDTO.builder()
               .id(entity.getId())
               .nome(entity.getNome())
                .email(entity.getEmail())
               .dataNascimento(entity.getDataNascimento())
               .tipoDocumento(entity.getTipoDocumento())
               .numeroDocumento(entity.getNumeroDocumento())
               .build();
    }

    /**
     * Converte um DTO UsuarioDTO para uma entidade UsuarioEntity
     * @param dto
     * @return UsuarioEntity
     */
    public UsuarioEntity convertToEntity(@NotNull UsuarioDTO dto) {
        return UsuarioEntity.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .email(dto.getEmail())
                .dataNascimento(dto.getDataNascimento())
                .tipoDocumento(dto.getTipoDocumento())
                .numeroDocumento(dto.getNumeroDocumento())
                .build();
    }

}
