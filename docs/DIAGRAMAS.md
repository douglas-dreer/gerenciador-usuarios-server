# 📊 Diagramas

## 📋 Índice

- [Diagrama de Classes](#diagrama-de-classes)
- [Diagrama de Sequência](#diagramas-de-sequência)
- [Diagrama de Estados](#diagrama-de-estados)
- [Diagrama de Pacotes](#diagrama-de-pacotes)

---

## Diagrama de Classes

### Visão Geral do Domínio

```mermaid
classDiagram
    class UsuarioEntity {
        -Long id
        -String nome
        -String email
        -LocalDate dataNascimento
        -TipoDocumento tipoDocumento
        -String numeroDocumento
        -EnderecoEntity endereco
        +getId() Long
        +getNome() String
        +getEmail() String
        +getDataNascimento() LocalDate
        +getTipoDocumento() TipoDocumento
        +getNumeroDocumento() String
        +getEndereco() EnderecoEntity
        +setEndereco(EnderecoEntity)
    }
    
    class EnderecoEntity {
        -Long id
        -Integer numero
        -String cep
        -String logradouro
        -String complemento
        -String bairro
        -String localidade
        -String estado
        -String regiao
        -UsuarioEntity usuario
        +getId() Long
        +getNumero() Integer
        +getCep() String
        +getLogradouro() String
        +getComplemento() String
        +getBairro() String
        +getLocalidade() String
        +getEstado() String
        +getRegiao() String
    }
    
    class TipoDocumento {
        <<enumeration>>
        CPF
        CNPJ
    }
    
    UsuarioEntity "1" --> "0..1" EnderecoEntity : possui
    UsuarioEntity --> TipoDocumento : usa
```

### DTOs e Requests

```mermaid
classDiagram
    class UsuarioDTO {
        <<class>>
        -Long id
        -String nome
        -String email
        -EnderecoDTO endereco
        -LocalDate dataNascimento
        -TipoDocumento tipoDocumento
        -String numeroDocumento
    }
    
    class EnderecoDTO {
        <<record>>
        +Long id
        +Integer numero
        +String cep
        +String logradouro
        +String complemento
        +String bairro
        +String localidade
        +String estado
        +String regiao
        +toBuilder() EnderecoDTOBuilder
    }
    
    class CriarUsuarioRequest {
        <<record>>
        +String nome
        +String email
        +LocalDate dataNascimento
        +TipoDocumento tipoDocumento
        +String numeroDocumento
        +CriarEnderecoRequest endereco
    }
    
    class AtualizarUsuarioRequest {
        <<record>>
        +String nome
        +String email
        +LocalDate dataNascimento
        +TipoDocumento tipoDocumento
        +String numeroDocumento
    }
    
    class CriarEnderecoRequest {
        <<record>>
        +String cep
        +Integer numero
        +String complemento
    }
    
    UsuarioDTO --> EnderecoDTO
    CriarUsuarioRequest --> CriarEnderecoRequest
```

### Controllers

```mermaid
classDiagram
    class UsuarioController {
        -UsuarioService usuarioService
        +obterUsuarioPorId(Long id) ResponseEntity~UsuarioDTO~
        +listarTodosUsuarios() ResponseEntity~List~UsuarioDTO~~
        +criarUsuario(CriarUsuarioRequest) ResponseEntity~UsuarioDTO~
        +atualizarUsuario(Long id, AtualizarUsuarioRequest) ResponseEntity~UsuarioDTO~
        +deletarUsuario(Long id) ResponseEntity~Void~
    }
    
    class EnderecoController {
        -EnderecoService service
        +criarEndereco(CriarEnderecoRequest) ResponseEntity~EnderecoDTO~
    }
    
    UsuarioController --> UsuarioService
    EnderecoController --> EnderecoService
```

### Services

```mermaid
classDiagram
    class UsuarioService {
        <<interface>>
        +criarUsuario(CriarUsuarioRequest) UsuarioDTO
        +listarUsuarios() List~UsuarioDTO~
        +buscarUsuarioPorId(Long) UsuarioDTO
        +atualizarUsuario(Long, AtualizarUsuarioRequest) UsuarioDTO
        +deletarUsuario(Long)
    }
    
    class UsuarioServiceImpl {
        -UsuarioRepository usuarioRepository
        -ViaCepService viaCepService
        -UsuarioMapper usuarioMapper
        -EnderecoMapper enderecoMapper
        +criarUsuario(CriarUsuarioRequest) UsuarioDTO
        +listarUsuarios() List~UsuarioDTO~
        +buscarUsuarioPorId(Long) UsuarioDTO
        +atualizarUsuario(Long, AtualizarUsuarioRequest) UsuarioDTO
        +deletarUsuario(Long)
        -buscarInformacoesEndereco(UsuarioEntity) UsuarioEntity
    }
    
    class EnderecoService {
        <<interface>>
        +criarEndereco(CriarEnderecoRequest) EnderecoDTO
        +buscarEnderecoPorId(Long) EnderecoDTO
        +atualizarEndereco(Long, CriarEnderecoRequest) EnderecoDTO
        +deletarEndereco(Long)
    }
    
    class EnderecoServiceImpl {
        -ViaCepService viaCepService
        -EnderecoRepository enderecoRepository
        -EnderecoMapper enderecoMapper
        +criarEndereco(CriarEnderecoRequest) EnderecoDTO
        +buscarEnderecoPorId(Long) EnderecoDTO
        +atualizarEndereco(Long, CriarEnderecoRequest) EnderecoDTO
        +deletarEndereco(Long)
    }
    
    UsuarioService <|.. UsuarioServiceImpl
    EnderecoService <|.. EnderecoServiceImpl
```

### Mappers

```mermaid
classDiagram
    class MapperUtils {
        <<utility>>
        -Logger log$
        +convert(Object source, Class~T~ targetClass)$ T
        -processRecordFields(Object, Class, Object, Class)$
        -processClassFields(Object, Class, Object, Class)$
        -processField(String, Object, Class, Object, Class)$
        -hasBuilder(Class)$ boolean
        -findFieldType(Class, String)$ Class
        -findBuilderMethod(Class, String, Object, Class)$ Method
    }
    
    class MappingException {
        <<exception>>
        +MappingException(String message, Throwable cause)
    }
    
    class UsuarioMapper {
        <<Component>>
        -Logger log
        +toDTO(UsuarioEntity) UsuarioDTO
        +toDTO(CriarUsuarioRequest) UsuarioDTO
        +toDTO(AtualizarUsuarioRequest) UsuarioDTO
        +toEntity(UsuarioDTO) UsuarioEntity
        +toEntity(CriarUsuarioRequest) UsuarioEntity
        +toEntity(AtualizarUsuarioRequest) UsuarioEntity
    }
    
    class EnderecoMapper {
        <<Component>>
        -Logger log
        +toDTO(EnderecoEntity) EnderecoDTO
        +toDTO(CriarEnderecoRequest) EnderecoDTO
        +toEntity(EnderecoDTO) EnderecoEntity
        +toEntity(CriarEnderecoRequest) EnderecoEntity
        +merge(EnderecoDTO, EnderecoDTO) EnderecoDTO
    }
    
    UsuarioMapper ..> MapperUtils : usa
    EnderecoMapper ..> MapperUtils : usa
    MapperUtils ..> MappingException : lança
```

### Repositories

```mermaid
classDiagram
    class JpaRepository~T, ID~ {
        <<interface>>
        +findAll() List~T~
        +findById(ID) Optional~T~
        +save(T) T
        +deleteById(ID)
        +existsById(ID) boolean
    }
    
    class UsuarioRepository {
        <<interface>>
        +findByEmail(String email) Optional~UsuarioEntity~
    }
    
    class EnderecoRepository {
        <<interface>>
    }
    
    JpaRepository <|-- UsuarioRepository
    JpaRepository <|-- EnderecoRepository
```

### Integração ViaCEP

```mermaid
classDiagram
    class ViaCepClient {
        <<FeignClient>>
        +buscarEnderecoPorCep(String cep) EnderecoApiResponse
    }
    
    class ViaCepService {
        <<Service>>
        -ViaCepClient viaCepClient
        -EnderecoMapper enderecoMapper
        +buscarEnderecoPorCep(String cep) EnderecoDTO
    }
    
    class EnderecoApiResponse {
        <<record>>
        +String cep
        +String logradouro
        +String complemento
        +String bairro
        +String localidade
        +String uf
        +String estado
        +String regiao
    }
    
    ViaCepService --> ViaCepClient : usa
    ViaCepClient --> EnderecoApiResponse : retorna
```

---

## Diagramas de Sequência

### Criar Usuário

```mermaid
sequenceDiagram
    autonumber
    participant C as Client
    participant UC as UsuarioController
    participant US as UsuarioService
    participant UM as UsuarioMapper
    participant EM as EnderecoMapper
    participant VCS as ViaCepService
    participant VCC as ViaCepClient
    participant UR as UsuarioRepository
    participant DB as PostgreSQL
    
    C->>+UC: POST /usuarios (CriarUsuarioRequest)
    UC->>+US: criarUsuario(request)
    
    US->>+UM: toEntity(request)
    UM->>UM: MapperUtils.convert()
    UM-->>-US: UsuarioEntity
    
    alt Endereço informado
        US->>+VCS: buscarEnderecoPorCep(cep)
        VCS->>+VCC: buscarEnderecoPorCep(cep)
        VCC->>VCC: HTTP GET viacep.com.br
        VCC-->>-VCS: EnderecoApiResponse
        VCS->>VCS: converter para EnderecoDTO
        VCS-->>-US: EnderecoDTO (dados completos)
        
        US->>+EM: toDTO(enderecoOriginal)
        EM-->>-US: EnderecoDTO
        
        US->>+EM: merge(original, viaCep)
        EM-->>-US: EnderecoDTO (mesclado)
        
        US->>+EM: toEntity(enderecoDTO)
        EM-->>-US: EnderecoEntity
        
        US->>US: usuario.setEndereco(entity)
    end
    
    US->>+UR: save(usuarioEntity)
    UR->>+DB: INSERT INTO usuarios...
    DB-->>-UR: usuario salvo
    UR-->>-US: UsuarioEntity (com ID)
    
    US->>+UM: toDTO(entity)
    UM-->>-US: UsuarioDTO
    
    US-->>-UC: UsuarioDTO
    UC-->>-C: 201 Created + UsuarioDTO
```

### Buscar Usuário por ID

```mermaid
sequenceDiagram
    autonumber
    participant C as Client
    participant UC as UsuarioController
    participant US as UsuarioService
    participant UM as UsuarioMapper
    participant UR as UsuarioRepository
    participant DB as PostgreSQL
    
    C->>+UC: GET /usuarios/{id}
    UC->>+US: buscarUsuarioPorId(id)
    
    US->>+UR: findById(id)
    UR->>+DB: SELECT * FROM usuarios WHERE id = ?
    DB-->>-UR: ResultSet
    UR-->>-US: Optional<UsuarioEntity>
    
    alt Usuário encontrado
        US->>+UM: toDTO(entity)
        UM->>UM: MapperUtils.convert()
        UM-->>-US: UsuarioDTO
        US-->>-UC: UsuarioDTO
        UC-->>-C: 200 OK + UsuarioDTO
    else Usuário não encontrado
        US-->>UC: null
        UC-->>C: 404 Not Found
    end
```

### Atualizar Usuário

```mermaid
sequenceDiagram
    autonumber
    participant C as Client
    participant UC as UsuarioController
    participant US as UsuarioService
    participant UM as UsuarioMapper
    participant UR as UsuarioRepository
    participant DB as PostgreSQL
    
    C->>+UC: PATCH /usuarios/{id} (AtualizarUsuarioRequest)
    UC->>+US: atualizarUsuario(id, request)
    
    US->>+UR: existsById(id)
    UR->>+DB: SELECT EXISTS...
    DB-->>-UR: boolean
    UR-->>-US: exists
    
    alt Usuário não existe
        US-->>UC: throw Exception("Usuário não encontrado")
        UC-->>C: 404 Not Found
    end
    
    US->>+UR: findByEmail(email)
    UR->>+DB: SELECT * FROM usuarios WHERE email = ?
    DB-->>-UR: Optional<UsuarioEntity>
    UR-->>-US: Optional<UsuarioEntity>
    
    alt Email pertence a outro usuário
        US-->>UC: throw Exception("Email já cadastrado")
        UC-->>C: 409 Conflict
    end
    
    US->>+UM: toEntity(request)
    UM-->>-US: UsuarioEntity
    US->>US: entity.setId(id)
    
    US->>+UR: save(entity)
    UR->>+DB: UPDATE usuarios SET...
    DB-->>-UR: usuario atualizado
    UR-->>-US: UsuarioEntity
    
    US->>+UM: toDTO(entity)
    UM-->>-US: UsuarioDTO
    
    US-->>-UC: UsuarioDTO
    UC-->>-C: 200 OK + UsuarioDTO
```

### Deletar Usuário

```mermaid
sequenceDiagram
    autonumber
    participant C as Client
    participant UC as UsuarioController
    participant US as UsuarioService
    participant UR as UsuarioRepository
    participant DB as PostgreSQL
    
    C->>+UC: DELETE /usuarios/{id}
    UC->>+US: deletarUsuario(id)
    
    US->>+UR: existsById(id)
    UR->>+DB: SELECT EXISTS...
    DB-->>-UR: boolean
    UR-->>-US: exists
    
    alt Usuário não existe
        US-->>UC: throw Exception("Usuário não encontrado")
        UC-->>C: 404 Not Found
    else Usuário existe
        US->>+UR: deleteById(id)
        UR->>+DB: DELETE FROM usuarios WHERE id = ?
        DB-->>-UR: deleted
        UR-->>-US: void
        US-->>-UC: void
        UC-->>-C: 204 No Content
    end
```

### Criar Endereço

```mermaid
sequenceDiagram
    autonumber
    participant C as Client
    participant EC as EnderecoController
    participant ES as EnderecoService
    participant EM as EnderecoMapper
    participant VCS as ViaCepService
    participant VCC as ViaCepClient
    participant ER as EnderecoRepository
    participant DB as PostgreSQL
    
    C->>+EC: POST /enderecos (CriarEnderecoRequest)
    EC->>+ES: criarEndereco(request)
    
    ES->>+VCS: buscarEnderecoPorCep(cep)
    VCS->>+VCC: buscarEnderecoPorCep(cep)
    VCC->>VCC: HTTP GET viacep.com.br/{cep}/json
    VCC-->>-VCS: EnderecoApiResponse
    VCS-->>-ES: EnderecoDTO (dados ViaCEP)
    
    ES->>+EM: toDTO(request)
    EM-->>-ES: EnderecoDTO (dados request)
    
    ES->>+EM: merge(original, viaCep)
    Note over EM: Preserva numero e complemento<br/>do request original
    EM-->>-ES: EnderecoDTO (mesclado)
    
    ES->>+EM: toEntity(enderecoDTO)
    EM-->>-ES: EnderecoEntity
    
    ES->>+ER: save(entity)
    ER->>+DB: INSERT INTO enderecos...
    DB-->>-ER: endereco salvo
    ER-->>-ES: EnderecoEntity (com ID)
    
    ES->>+EM: toDTO(entity)
    EM-->>-ES: EnderecoDTO
    
    ES-->>-EC: EnderecoDTO
    EC-->>-C: 200 OK + EnderecoDTO
```

---

## Diagrama de Estados

### Estado do Usuário

```mermaid
stateDiagram-v2
    [*] --> Criando: POST /usuarios
    Criando --> Ativo: Salvo com sucesso
    Criando --> Erro: Falha na validação
    
    Ativo --> Atualizando: PATCH /usuarios/{id}
    Atualizando --> Ativo: Atualização OK
    Atualizando --> Erro: Falha na validação
    
    Ativo --> Deletando: DELETE /usuarios/{id}
    Deletando --> [*]: Deletado
    
    Erro --> [*]: Rollback
```

### Estado do Endereço

```mermaid
stateDiagram-v2
    [*] --> Consultando_CEP: Informado CEP
    Consultando_CEP --> CEP_Encontrado: ViaCEP OK
    Consultando_CEP --> CEP_Nao_Encontrado: ViaCEP 404
    
    CEP_Encontrado --> Mesclando: Merge dados
    Mesclando --> Salvando: Entity criada
    
    CEP_Nao_Encontrado --> Erro_Validacao: CEP inválido
    
    Salvando --> Salvo: INSERT OK
    Salvo --> [*]
    
    Erro_Validacao --> [*]
```

---

## Diagrama de Pacotes

```mermaid
graph TB
    subgraph "io.github.gabitxt.gerenciamentousuario"
        subgraph "api"
            api_client[client]
            api_dto[dto]
            api_service[service]
        end
        
        subgraph "config"
            config_handler[GlobalExceptionHandler]
        end
        
        subgraph "controller"
            ctrl[Controllers]
            ctrl_request[request]
        end
        
        subgraph "entity"
            entities[Entities]
        end
        
        subgraph "enums"
            enums[Enumerations]
        end
        
        subgraph "mapper"
            mappers[Mappers]
        end
        
        subgraph "model"
            models[DTOs]
        end
        
        subgraph "repository"
            repos[Repositories]
        end
        
        subgraph "service"
            services[Services]
        end
    end
    
    ctrl --> services
    services --> mappers
    services --> repos
    services --> api_service
    api_service --> api_client
    repos --> entities
    mappers --> entities
    mappers --> models
    ctrl --> ctrl_request
    ctrl --> models
    entities --> enums
    
    style api fill:#DDA0DD
    style controller fill:#FF6B6B
    style service fill:#4ECDC4
    style mapper fill:#45B7D1
    style repository fill:#96CEB4
    style entity fill:#FFEAA7
    style model fill:#FFA07A
```

---

<p align="center">
  <a href="./README.md">← Voltar ao Índice</a>
</p>

