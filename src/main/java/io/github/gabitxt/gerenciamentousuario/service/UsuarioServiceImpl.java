package io.github.gabitxt.gerenciamentousuario.service;

import io.github.gabitxt.gerenciamentousuario.api.service.ViaCepService;
import io.github.gabitxt.gerenciamentousuario.controller.request.AtualizarUsuarioRequest;
import io.github.gabitxt.gerenciamentousuario.controller.request.CriarUsuarioRequest;
import io.github.gabitxt.gerenciamentousuario.entity.UsuarioEntity;
import io.github.gabitxt.gerenciamentousuario.mapper.EnderecoMapper;
import io.github.gabitxt.gerenciamentousuario.mapper.UsuarioMapper;
import io.github.gabitxt.gerenciamentousuario.model.EnderecoDTO;
import io.github.gabitxt.gerenciamentousuario.model.UsuarioDTO;
import io.github.gabitxt.gerenciamentousuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ViaCepService viaCepService;
    private final UsuarioMapper usuarioMapper;
    private final EnderecoMapper enderecoMapper;

    /**
     * Cria um novo usuário.
     * @param request
     * @return UsuarioDTO criado
     */
    @Override
    public UsuarioDTO criarUsuario(CriarUsuarioRequest request) {
        UsuarioEntity usuarioEntity = buscarInformacoesEndereco(usuarioMapper.toEntity(request));
        usuarioEntity = usuarioRepository.save(usuarioEntity);
        return usuarioMapper.toDTO(usuarioEntity);
    }

    /**
     * Lista todos os usuários.
     * @return lista de UsuarioDTO
     */
    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    /**
     * Busca um usuário por ID.
     * @param id
     * @return
     */
    @Override
    public UsuarioDTO buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO)
                .orElse(null);
    }

    @Override
    public UsuarioDTO atualizarUsuario(Long id, AtualizarUsuarioRequest request) throws Exception {
        // REGRA 01: Verificar se o usuário existe
        if (!usuarioRepository.existsById(id)) {
            throw new Exception("Usuário não encontrado");
        }

        // REGRA 02: O email do usuário solicitado já está cadastrado para outro usuário?
        Optional<UsuarioEntity> usuarioComEmail = usuarioRepository.findByEmail(request.email());
        if (usuarioComEmail.isPresent() && !usuarioComEmail.get().getId().equals(id)) {
            throw new Exception("E-mail do usuário já está cadastrado para outro usuário");
        }

        UsuarioEntity entity = usuarioMapper.toEntity(request);
        entity.setId(id);
        return usuarioMapper.toDTO(usuarioRepository.save(entity));
    }

    @Override
    public void deletarUsuario(Long id) throws Exception {
        // REGRA 01: Verificar se o usuário existe
        if (!usuarioRepository.existsById(id)) {
            throw new Exception("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioEntity buscarInformacoesEndereco(UsuarioEntity usuario) {
        if (usuario.getEndereco() == null || usuario.getEndereco().getCep() == null) {
            return usuario;
        }

        EnderecoDTO enderecoOriginal = enderecoMapper.toDTO(usuario.getEndereco());
        EnderecoDTO enderecoViaCep = viaCepService.buscarEnderecoPorCep(enderecoOriginal.cep());
        EnderecoDTO enderecoFinal = enderecoMapper.merge(enderecoOriginal, enderecoViaCep);

        usuario.setEndereco(enderecoMapper.toEntity(enderecoFinal));
        return usuario;
    }
}
