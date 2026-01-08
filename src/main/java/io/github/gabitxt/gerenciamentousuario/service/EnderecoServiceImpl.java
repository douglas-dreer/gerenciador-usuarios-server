package io.github.gabitxt.gerenciamentousuario.service;

import io.github.gabitxt.gerenciamentousuario.api.service.ViaCepService;
import io.github.gabitxt.gerenciamentousuario.controller.request.CriarEnderecoRequest;
import io.github.gabitxt.gerenciamentousuario.entity.EnderecoEntity;
import io.github.gabitxt.gerenciamentousuario.mapper.EnderecoMapper;
import io.github.gabitxt.gerenciamentousuario.model.EnderecoDTO;
import io.github.gabitxt.gerenciamentousuario.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final ViaCepService viaCepService;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    @Override
    public EnderecoDTO criarEndereco(CriarEnderecoRequest request) {
        EnderecoDTO enderecoViaCep = viaCepService.buscarEnderecoPorCep(request.cep());
        EnderecoDTO enderecoOriginal = enderecoMapper.toDTO(request);
        EnderecoDTO enderecoFinal = enderecoMapper.merge(enderecoOriginal, enderecoViaCep);

        EnderecoEntity enderecoSalvo = enderecoRepository.save(enderecoMapper.toEntity(enderecoFinal));
        return enderecoMapper.toDTO(enderecoSalvo);
    }

    @Override
    public EnderecoDTO buscarEnderecoPorId(Long id) {
        return enderecoRepository.findById(id)
                .map(enderecoMapper::toDTO)
                .orElse(null);
    }

    @Override
    public EnderecoDTO atualizarEndereco(Long id, CriarEnderecoRequest request) {
        return null;
    }

    @Override
    public void deletarEndereco(Long id) {
        enderecoRepository.deleteById(id);
    }
}