package io.github.gabitxt.gerenciamentousuario.controller;

import io.github.gabitxt.gerenciamentousuario.controller.request.AtualizarUsuarioRequest;
import io.github.gabitxt.gerenciamentousuario.controller.request.CriarUsuarioRequest;
import io.github.gabitxt.gerenciamentousuario.model.ErroResponse;
import io.github.gabitxt.gerenciamentousuario.model.UsuarioDTO;
import io.github.gabitxt.gerenciamentousuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna um usuário específico baseado no ID informado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterUsuarioPorId(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            @PathVariable Long id) {
        UsuarioDTO usuarioDTO = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuarioDTO);
    }

    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista com todos os usuários cadastrados no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodosUsuarios() {
        List<UsuarioDTO> resultadoList = usuarioService.listarUsuarios();
        return ResponseEntity.ok(resultadoList);
    }

    @Operation(
            summary = "Criar novo usuário",
            description = """
                    Cria um novo usuário no sistema.
                    
                    Se um endereço com CEP válido for informado, os dados serão automaticamente 
                    preenchidos via integração com a API ViaCEP (logradouro, bairro, cidade, estado, região).
                    
                    O número e complemento informados pelo usuário são preservados.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email já cadastrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do usuário a ser criado",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CriarUsuarioRequest.class),
                            examples = @ExampleObject(
                                    name = "Exemplo completo",
                                    value = """
                                            {
                                              "nome": "João Silva",
                                              "email": "joao@email.com",
                                              "dataNascimento": "1990-05-15",
                                              "tipoDocumento": "CPF",
                                              "numeroDocumento": "12345678901",
                                              "endereco": {
                                                "cep": "01310100",
                                                "numero": 123,
                                                "complemento": "Apto 45"
                                              }
                                            }
                                            """
                            )
                    )
            )
            @RequestBody CriarUsuarioRequest request) {
        UsuarioDTO usuarioSalvo = usuarioService.criarUsuario(request);
        URI location = URI.create(String.format("/usuarios/%s", usuarioSalvo.getId()));
        return ResponseEntity.created(location).body(usuarioSalvo);
    }

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza parcialmente os dados de um usuário existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email já pertence a outro usuário",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados a serem atualizados",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AtualizarUsuarioRequest.class),
                            examples = @ExampleObject(
                                    name = "Atualizar nome e email",
                                    value = """
                                            {
                                              "nome": "João Silva Santos",
                                              "email": "joao.santos@email.com"
                                            }
                                            """
                            )
                    )
            )
            @RequestBody AtualizarUsuarioRequest request) throws Exception {
        UsuarioDTO usuarioAtualizado = usuarioService.atualizarUsuario(id, request);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @Operation(
            summary = "Deletar usuário",
            description = "Remove um usuário do sistema. O endereço associado também será removido."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário deletado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            @PathVariable Long id) throws Exception {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
