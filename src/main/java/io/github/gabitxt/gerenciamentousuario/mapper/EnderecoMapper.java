package io.github.gabitxt.gerenciamentousuario.mapper;

import io.github.gabitxt.gerenciamentousuario.controller.request.CriarEnderecoRequest;
import io.github.gabitxt.gerenciamentousuario.entity.EnderecoEntity;
import io.github.gabitxt.gerenciamentousuario.model.EnderecoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper específico para Endereco.
 */
@Slf4j
@Component
public class EnderecoMapper {

    public EnderecoDTO toDTO(EnderecoEntity entity) {
        log.debug("Mapeando EnderecoEntity(id={}) para EnderecoDTO", entity != null ? entity.getId() : null);
        return MapperUtils.convert(entity, EnderecoDTO.class);
    }

    public EnderecoDTO toDTO(CriarEnderecoRequest request) {
        log.debug("Mapeando CriarEnderecoRequest(cep={}) para EnderecoDTO", request != null ? request.cep() : null);
        return MapperUtils.convert(request, EnderecoDTO.class);
    }

    public EnderecoEntity toEntity(EnderecoDTO dto) {
        log.debug("Mapeando EnderecoDTO(id={}) para EnderecoEntity", dto != null ? dto.id() : null);
        return MapperUtils.convert(dto, EnderecoEntity.class);
    }

    public EnderecoEntity toEntity(CriarEnderecoRequest request) {
        log.debug("Mapeando CriarEnderecoRequest(cep={}) para EnderecoEntity", request != null ? request.cep() : null);
        return MapperUtils.convert(request, EnderecoEntity.class);
    }

    public EnderecoDTO merge(EnderecoDTO original, EnderecoDTO viaCep) {
        if (original == null) return viaCep;
        if (viaCep == null) return original;

        log.debug("Mesclando EnderecoDTO com dados do ViaCEP");
        return viaCep.toBuilder()
                .numero(original.numero())
                .complemento(original.complemento() != null ? original.complemento() : viaCep.complemento())
                .build();
    }
}
