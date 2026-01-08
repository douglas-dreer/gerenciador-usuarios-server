package io.github.gabitxt.gerenciamentousuario.controller;

import io.github.gabitxt.gerenciamentousuario.controller.request.BuscarEnderecoPorTermoRequest;
import io.github.gabitxt.gerenciamentousuario.controller.request.CriarEnderecoRequest;
import io.github.gabitxt.gerenciamentousuario.model.EnderecoDTO;
import io.github.gabitxt.gerenciamentousuario.model.ErroResponse;
import io.github.gabitxt.gerenciamentousuario.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@Tag(name = "Endereços", description = "Endpoints para gerenciamento de endereços com integração ViaCEP")
public class EnderecoController {

    private final EnderecoService service;

    public EnderecoController(EnderecoService service) {
        this.service = service;
    }

    @Operation(
            summary = "Criar novo endereço",
            description = """
                    Cria um novo endereço no sistema com preenchimento automático via ViaCEP.
                    
                    ### Funcionamento
                    
                    1. Informe o CEP (obrigatório), número e complemento (opcionais)
                    2. O sistema consulta a API ViaCEP para obter os dados completos
                    3. Os dados do ViaCEP (logradouro, bairro, cidade, estado, região) são preenchidos automaticamente
                    4. Número e complemento informados são preservados
                    
                    ### Formato do CEP
                    
                    - Aceita com ou sem hífen: `01310100` ou `01310-100`
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Endereço criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EnderecoDTO.class),
                            examples = @ExampleObject(
                                    name = "Resposta de sucesso",
                                    value = """
                                            {
                                              "id": 1,
                                              "cep": "01310-100",
                                              "numero": 123,
                                              "logradouro": "Avenida Paulista",
                                              "complemento": "Apto 45",
                                              "bairro": "Bela Vista",
                                              "localidade": "São Paulo",
                                              "estado": "SP",
                                              "regiao": "Sudeste"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "CEP inválido ou não informado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "503",
                    description = "Serviço ViaCEP indisponível",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<EnderecoDTO> criarEndereco(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do endereço a ser criado",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CriarEnderecoRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Endereço completo",
                                            value = """
                                                    {
                                                      "cep": "01310100",
                                                      "numero": 123,
                                                      "complemento": "Apto 45"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Apenas CEP",
                                            value = """
                                                    {
                                                      "cep": "01310100"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            @Valid @RequestBody CriarEnderecoRequest request) {
        EnderecoDTO enderecoCriado = service.criarEndereco(request);
        return ResponseEntity.ok(enderecoCriado);
    }

    @Operation(
            summary = "Buscar endereços por termo",
            description = """
                    Busca endereços na API ViaCEP por estado, cidade e termo de busca.
                    
                    ### Parâmetros
                    
                    - **uf**: Sigla do estado (ex: SP, RJ, MG)
                    - **cidade**: Nome da cidade
                    - **logradouro**: Termo de busca (nome da rua, avenida, etc.)
                    
                    ### Observação
                    
                    Esta consulta é feita diretamente na API ViaCEP e retorna múltiplos resultados.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de endereços encontrados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EnderecoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            )
    })
    @GetMapping("/buscar/parametros")
    public ResponseEntity<List<EnderecoDTO>> buscarPorEstadoCidadeETermo(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Parâmetros de busca",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BuscarEnderecoPorTermoRequest.class),
                            examples = @ExampleObject(
                                    name = "Buscar na Paulista",
                                    value = """
                                            {
                                              "uf": "SP",
                                              "cidade": "São Paulo",
                                              "logradouro": "Paulista"
                                            }
                                            """
                            )
                    )
            )
            @RequestBody BuscarEnderecoPorTermoRequest buscarEnderecoPorTermo) {
        List<EnderecoDTO> enderecoList = service.obterEnderecoPorTermo(buscarEnderecoPorTermo);
        return ResponseEntity.ok(enderecoList);
    }
}
