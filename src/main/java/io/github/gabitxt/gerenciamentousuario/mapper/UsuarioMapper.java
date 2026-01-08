package io.github.gabitxt.gerenciamentousuario.mapper;

import io.github.gabitxt.gerenciamentousuario.controller.request.AtualizarUsuarioRequest;
import io.github.gabitxt.gerenciamentousuario.controller.request.CriarUsuarioRequest;
import io.github.gabitxt.gerenciamentousuario.entity.UsuarioEntity;
import io.github.gabitxt.gerenciamentousuario.model.UsuarioDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper específico para Usuario.
 */
@Slf4j
@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(UsuarioEntity entity) {
        log.debug("Mapeando UsuarioEntity(id={}) para UsuarioDTO", entity != null ? entity.getId() : null);
        return MapperUtils.convert(entity, UsuarioDTO.class);
    }

    public UsuarioDTO toDTO(CriarUsuarioRequest request) {
        log.debug("Mapeando CriarUsuarioRequest para UsuarioDTO");
        return MapperUtils.convert(request, UsuarioDTO.class);
    }

    public UsuarioDTO toDTO(AtualizarUsuarioRequest request) {
        log.debug("Mapeando AtualizarUsuarioRequest para UsuarioDTO");
        return MapperUtils.convert(request, UsuarioDTO.class);
    }

    public UsuarioEntity toEntity(UsuarioDTO dto) {
        log.debug("Mapeando UsuarioDTO(id={}) para UsuarioEntity", dto != null ? dto.getId() : null);
        return MapperUtils.convert(dto, UsuarioEntity.class);
    }

    public UsuarioEntity toEntity(CriarUsuarioRequest request) {
        log.debug("Mapeando CriarUsuarioRequest para UsuarioEntity");
        return MapperUtils.convert(request, UsuarioEntity.class);
    }

    public UsuarioEntity toEntity(AtualizarUsuarioRequest request) {
        log.debug("Mapeando AtualizarUsuarioRequest para UsuarioEntity");
        return MapperUtils.convert(request, UsuarioEntity.class);
    }
}
