package io.github.gabitxt.gerenciamentousuario.service;

import io.github.gabitxt.gerenciamentousuario.entity.UsuarioEntity;
import io.github.gabitxt.gerenciamentousuario.mapper.UsuarioMapper;
import io.github.gabitxt.gerenciamentousuario.model.UsuarioDTO;
import io.github.gabitxt.gerenciamentousuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper mapper;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    /**
     * Cria um novo usuário.
     * @param usuarioDTO
     * @return UsuarioDTO criado
     */
    @Override
    public UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO) {
        UsuarioEntity entity = mapper.convertToEntity(usuarioDTO);
        entity = usuarioRepository.save(entity);
        UsuarioDTO resultado = mapper.convertToDomain(entity);
        return resultado;
    }

    /**
     * Lista todos os usuários.
     * @return lista de UsuarioDTO
     */
    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(mapper::convertToDomain)
                .toList();
    }

    /**
     * Busca um usuário por ID.
     * @param id
     * @return
     */
    @Override
    public UsuarioDTO buscarUsuarioPorId(Long id) {
        UsuarioEntity entity = usuarioRepository.findById(id).orElse(null);
        if (entity != null) {
            return mapper.convertToDomain(entity);
        } else {
            return null;
        }
    }

    @Override
    public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioDTO) throws Exception {
        // REGRA 01: Verificar se o usuário existe
        if (!usuarioRepository.existsById(id)) {
            throw new Exception("Usuário não encontrado");
        }

        // REGRA 02: O email do usuário solicitado já está cadastrado para outro usuário?
        String emailSolicitado = usuarioDTO.getEmail();
        Optional<UsuarioEntity> usuarioComEmail = usuarioRepository.findByEmail(emailSolicitado);

        if (usuarioComEmail.isPresent()) {
            throw new Exception("E-mail do usuário já está cadastrado para outro usuário");
        }

        UsuarioEntity entity = mapper.convertToEntity(usuarioDTO);
        UsuarioDTO resultado = mapper.convertToDomain(usuarioRepository.save(entity));
        return resultado;
    }

    @Override
    public void deletarUsuario(Long id) throws Exception {
        // REGRA 01: Verificar se o usuário existe
        if (!usuarioRepository.existsById(id)) {
            throw new Exception("Usuário não encontrado");
        }

        usuarioRepository.deleteById(id);
    }
}
