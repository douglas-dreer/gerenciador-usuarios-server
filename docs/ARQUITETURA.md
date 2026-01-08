# 🏗️ Arquitetura do Sistema

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Arquitetura em Camadas](#arquitetura-em-camadas)
- [Diagrama de Componentes](#diagrama-de-componentes)
- [Diagrama de Implantação](#diagrama-de-implantação)
- [Padrões Arquiteturais](#padrões-arquiteturais)
- [Decisões de Arquitetura](#decisões-de-arquitetura)

---

## Visão Geral

O sistema **Gerenciamento de Usuários** é uma aplicação backend desenvolvida seguindo os princípios de **Clean Architecture** e **Domain-Driven Design (DDD)** simplificado, utilizando uma arquitetura em camadas bem definida.

### Características Principais

- **Arquitetura em Camadas**: Separação clara de responsabilidades
- **RESTful API**: Interface HTTP seguindo padrões REST
- **Inversão de Dependência**: Services dependem de abstrações
- **Mappers Genéricos**: Conversão de objetos com SOLID

---

## Arquitetura em Camadas

```mermaid
graph TB
    subgraph "🌐 Presentation Layer"
        direction LR
        UC[UsuarioController]
        EC[EnderecoController]
    end
    
    subgraph "📦 Request/Response DTOs"
        direction LR
        CUR[CriarUsuarioRequest]
        AUR[AtualizarUsuarioRequest]
        CER[CriarEnderecoRequest]
        UDTO[UsuarioDTO]
        EDTO[EnderecoDTO]
    end
    
    subgraph "⚙️ Business Layer"
        direction LR
        US[UsuarioService]
        USI[UsuarioServiceImpl]
        ES[EnderecoService]
        ESI[EnderecoServiceImpl]
    end
    
    subgraph "🔄 Mapper Layer"
        direction LR
        MU[MapperUtils]
        UM[UsuarioMapper]
        EM[EnderecoMapper]
    end
    
    subgraph "💾 Persistence Layer"
        direction LR
        UR[UsuarioRepository]
        ER[EnderecoRepository]
    end
    
    subgraph "🗃️ Domain Layer"
        direction LR
        UE[UsuarioEntity]
        EE[EnderecoEntity]
    end
    
    subgraph "🌍 External Services"
        VCS[ViaCepService]
        VCC[ViaCepClient]
    end
    
    UC --> US
    EC --> ES
    US --> USI
    ES --> ESI
    USI --> UM
    USI --> EM
    ESI --> EM
    UM --> MU
    EM --> MU
    USI --> UR
    ESI --> ER
    USI --> VCS
    ESI --> VCS
    VCS --> VCC
    UR --> UE
    ER --> EE

    style UC fill:#FF6B6B,color:#fff
    style EC fill:#FF6B6B,color:#fff
    style USI fill:#4ECDC4,color:#fff
    style ESI fill:#4ECDC4,color:#fff
    style MU fill:#45B7D1,color:#fff
    style UM fill:#45B7D1,color:#fff
    style EM fill:#45B7D1,color:#fff
    style UR fill:#96CEB4,color:#fff
    style ER fill:#96CEB4,color:#fff
    style UE fill:#FFEAA7,color:#000
    style EE fill:#FFEAA7,color:#000
    style VCS fill:#DDA0DD,color:#fff
    style VCC fill:#DDA0DD,color:#fff
```

---

## Diagrama de Componentes

```mermaid
C4Component
    title Diagrama de Componentes - Gerenciamento de Usuários

    Container_Boundary(api, "API Application") {
        Component(controllers, "Controllers", "Spring MVC", "Recebe requisições HTTP e retorna respostas")
        Component(services, "Services", "Spring Service", "Implementa regras de negócio")
        Component(mappers, "Mappers", "Spring Component", "Converte entre DTOs e Entities")
        Component(repositories, "Repositories", "Spring Data JPA", "Acesso ao banco de dados")
        Component(entities, "Entities", "JPA Entity", "Representação das tabelas")
        Component(feign, "Feign Client", "OpenFeign", "Cliente HTTP declarativo")
    }

    ContainerDb(db, "PostgreSQL", "Banco de Dados", "Armazena dados de usuários e endereços")
    System_Ext(viacep, "ViaCEP API", "API externa para consulta de CEP")

    Rel(controllers, services, "Usa")
    Rel(services, mappers, "Usa")
    Rel(services, repositories, "Usa")
    Rel(services, feign, "Usa")
    Rel(mappers, entities, "Converte")
    Rel(repositories, db, "Lê/Escreve")
    Rel(feign, viacep, "HTTP GET")
```

---

## Diagrama de Implantação

```mermaid
graph TB
    subgraph "🖥️ Ambiente Local / Desenvolvimento"
        subgraph "Docker Compose"
            DB[(PostgreSQL<br/>Container)]
        end
        
        subgraph "JVM"
            APP[Spring Boot<br/>Application<br/>:8080]
        end
    end
    
    subgraph "☁️ Serviços Externos"
        VIACEP[ViaCEP API<br/>viacep.com.br]
    end
    
    subgraph "👤 Clientes"
        POSTMAN[Postman]
        CURL[cURL]
        BROWSER[Browser]
    end
    
    POSTMAN -->|HTTP| APP
    CURL -->|HTTP| APP
    BROWSER -->|HTTP| APP
    APP -->|JDBC:5432| DB
    APP -->|HTTPS| VIACEP

    style DB fill:#336791,color:#fff
    style APP fill:#6DB33F,color:#fff
    style VIACEP fill:#FF6B6B,color:#fff
```

---

## Padrões Arquiteturais

### 1. Layered Architecture (Arquitetura em Camadas)

```mermaid
graph LR
    subgraph "Camadas"
        A[Presentation] --> B[Business]
        B --> C[Persistence]
        C --> D[Database]
    end
    
    subgraph "Responsabilidades"
        A1["Controllers<br/>DTOs<br/>Request/Response"]
        B1["Services<br/>Business Rules<br/>Mappers"]
        C1["Repositories<br/>Entities<br/>Queries"]
        D1["Tables<br/>Indexes<br/>Constraints"]
    end
    
    A -.-> A1
    B -.-> B1
    C -.-> C1
    D -.-> D1
```

### 2. Repository Pattern

```mermaid
classDiagram
    class JpaRepository~T, ID~ {
        <<interface>>
        +findAll() List~T~
        +findById(ID id) Optional~T~
        +save(T entity) T
        +deleteById(ID id)
    }
    
    class UsuarioRepository {
        <<interface>>
        +findByEmail(String email) Optional~UsuarioEntity~
    }
    
    class EnderecoRepository {
        <<interface>>
        +findByUsuarioId(Long usuarioId) List~EnderecoEntity~
    }
    
    JpaRepository <|-- UsuarioRepository
    JpaRepository <|-- EnderecoRepository
```

### 3. Service Layer Pattern

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
        -UsuarioRepository repository
        -UsuarioMapper mapper
        -EnderecoMapper enderecoMapper
        -ViaCepService viaCepService
        +criarUsuario(CriarUsuarioRequest) UsuarioDTO
        +listarUsuarios() List~UsuarioDTO~
        +buscarUsuarioPorId(Long) UsuarioDTO
        +atualizarUsuario(Long, AtualizarUsuarioRequest) UsuarioDTO
        +deletarUsuario(Long)
        -buscarInformacoesEndereco(UsuarioEntity) UsuarioEntity
    }
    
    UsuarioService <|.. UsuarioServiceImpl
```

### 4. Mapper Pattern (com SOLID)

```mermaid
classDiagram
    class MapperUtils {
        <<utility>>
        +convert(Object source, Class~T~ targetClass)$ T
        -processRecordFields(Object, Class, Object, Class)$
        -processClassFields(Object, Class, Object, Class)$
        -processField(String, Object, Class, Object, Class)$
        -hasBuilder(Class)$ boolean
        -findFieldType(Class, String)$ Class
        -findBuilderMethod(Class, String, Object, Class)$ Method
    }
    
    class UsuarioMapper {
        +toDTO(UsuarioEntity) UsuarioDTO
        +toDTO(CriarUsuarioRequest) UsuarioDTO
        +toDTO(AtualizarUsuarioRequest) UsuarioDTO
        +toEntity(UsuarioDTO) UsuarioEntity
        +toEntity(CriarUsuarioRequest) UsuarioEntity
        +toEntity(AtualizarUsuarioRequest) UsuarioEntity
    }
    
    class EnderecoMapper {
        +toDTO(EnderecoEntity) EnderecoDTO
        +toDTO(CriarEnderecoRequest) EnderecoDTO
        +toEntity(EnderecoDTO) EnderecoEntity
        +toEntity(CriarEnderecoRequest) EnderecoEntity
        +merge(EnderecoDTO, EnderecoDTO) EnderecoDTO
    }
    
    UsuarioMapper ..> MapperUtils : usa
    EnderecoMapper ..> MapperUtils : usa
```

---

## Decisões de Arquitetura

### ADR-001: Uso de Records para DTOs

| Item | Descrição |
|------|-----------|
| **Status** | Aceito |
| **Contexto** | DTOs precisam ser imutáveis e ter menos boilerplate |
| **Decisão** | Usar Java Records com `@Builder(toBuilder = true)` do Lombok |
| **Consequências** | ✅ Imutabilidade garantida<br/>✅ Menos código<br/>✅ equals/hashCode automáticos<br/>⚠️ Requer Java 16+ |

### ADR-002: MapperUtils com Reflection

| Item | Descrição |
|------|-----------|
| **Status** | Aceito |
| **Contexto** | Evitar código repetitivo em conversões de objetos |
| **Decisão** | Criar utilitário genérico usando Reflection e builders do Lombok |
| **Consequências** | ✅ Código DRY<br/>✅ Suporta classes e records<br/>✅ Conversão recursiva<br/>⚠️ Overhead de reflection<br/>⚠️ Erros em runtime |

### ADR-003: Feign Client para Integrações

| Item | Descrição |
|------|-----------|
| **Status** | Aceito |
| **Contexto** | Necessidade de integração com API ViaCEP |
| **Decisão** | Usar Spring Cloud OpenFeign para cliente HTTP declarativo |
| **Consequências** | ✅ Código declarativo<br/>✅ Integração com Spring<br/>✅ Retry/Circuit Breaker<br/>⚠️ Dependência adicional |

### ADR-004: PostgreSQL como Banco de Dados

| Item | Descrição |
|------|-----------|
| **Status** | Aceito |
| **Contexto** | Necessidade de banco relacional robusto |
| **Decisão** | Usar PostgreSQL com Docker Compose |
| **Consequências** | ✅ Open source<br/>✅ Suporte a JSON<br/>✅ Escalável<br/>⚠️ Requer Docker |

---

## Fluxo de Dados

```mermaid
flowchart LR
    subgraph Input
        REQ[HTTP Request]
    end
    
    subgraph Processing
        C[Controller]
        S[Service]
        M[Mapper]
        R[Repository]
    end
    
    subgraph Output
        RES[HTTP Response]
    end
    
    subgraph Storage
        DB[(Database)]
    end
    
    REQ -->|JSON| C
    C -->|Request DTO| S
    S -->|DTO| M
    M -->|Entity| R
    R -->|SQL| DB
    DB -->|ResultSet| R
    R -->|Entity| M
    M -->|DTO| S
    S -->|Response DTO| C
    C -->|JSON| RES
```

---

<p align="center">
  <a href="./README.md">← Voltar ao Índice</a>
</p>

