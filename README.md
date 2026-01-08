# 👥 Gerenciamento de Usuários

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen?style=for-the-badge&logo=spring&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.0-brightgreen?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-blue?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

Sistema de gerenciamento de usuários desenvolvido com **Spring Boot**, oferecendo uma API RESTful robusta para operações CRUD de usuários com integração à API ViaCEP para busca de endereços.

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura](#-arquitetura)
- [Padrões de Projeto](#-padrões-de-projeto)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Configuração](#-instalação-e-configuração)
- [Executando a Aplicação](#-executando-a-aplicação)
- [Endpoints da API](#-endpoints-da-api)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Avaliação do Código](#-avaliação-do-código-code-review)
- [Roadmap](#-roadmap)
- [Contribuição](#-contribuição)
- [Licença](#-licença)
- [Contato](#-contato)

---

## 📖 Sobre o Projeto

O **Gerenciamento de Usuários** é uma aplicação backend desenvolvida em Java com Spring Boot, projetada para gerenciar operações relacionadas a usuários de forma eficiente e segura.

### Funcionalidades Principais

- ✅ Cadastro de novos usuários
- ✅ Consulta de usuários (individual e listagem)
- ✅ Atualização de dados de usuários (PATCH)
- ✅ Exclusão de usuários
- ✅ Busca de endereço por CEP (Integração ViaCEP)
- ✅ Persistência em banco de dados PostgreSQL
- ✅ Mappers genéricos com SOLID
- ✅ DTOs imutáveis com Records
- ✅ Logs com SLF4J seguindo boas práticas
- 🔄 Validação de dados de entrada (em desenvolvimento)
- 🔄 Tratamento de erros personalizado (em desenvolvimento)

---

## 🛠 Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| **Java** | 21 | Linguagem de programação |
| **Spring Boot** | 4.0.1 | Framework principal |
| **Spring Data JPA** | - | Persistência de dados |
| **Spring Validation** | - | Validação de dados |
| **Spring Cloud OpenFeign** | 2025.1.0 | Cliente HTTP declarativo |
| **PostgreSQL** | Latest | Banco de dados relacional |
| **Docker Compose** | - | Orquestração de containers |
| **Lombok** | 1.18.32 | Redução de código boilerplate |
| **SLF4J** | - | Abstração de logging |
| **Maven** | - | Gerenciador de dependências |

---

## 🏗 Arquitetura

O projeto segue uma arquitetura em camadas (Layered Architecture):

```
┌─────────────────────────────────────────────────────────────────┐
│                        Controller Layer                          │
│           (UsuarioController, EnderecoController)                │
│                    ↓ Request DTOs (Records)                      │
├─────────────────────────────────────────────────────────────────┤
│                         Service Layer                            │
│              (UsuarioService, EnderecoService)                   │
├─────────────────────────────────────────────────────────────────┤
│                         Mapper Layer                             │
│     (UsuarioMapper, EnderecoMapper) ← MapperUtils (Genérico)     │
├─────────────────────────────────────────────────────────────────┤
│                       Repository Layer                           │
│            (UsuarioRepository, EnderecoRepository)               │
├─────────────────────────────────────────────────────────────────┤
│                        Entity Layer                              │
│              (UsuarioEntity, EnderecoEntity)                     │
├─────────────────────────────────────────────────────────────────┤
│                      External Services                           │
│                    (ViaCEP API - Feign)                          │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🎯 Padrões de Projeto

### SOLID nos Mappers

O projeto implementa os princípios SOLID na camada de mapeamento:

| Princípio | Implementação |
|-----------|---------------|
| **S** - Single Responsibility | Cada mapper é responsável por um domínio específico |
| **O** - Open/Closed | `MapperUtils` pode ser estendido sem modificação |
| **L** - Liskov Substitution | Mappers são substituíveis em testes |
| **I** - Interface Segregation | Interfaces enxutas e específicas |
| **D** - Dependency Inversion | Services dependem de abstrações, não implementações |

### Estrutura dos Mappers

```
mapper/
├── MapperUtils.java       → Utilitário genérico de conversão (Reflection)
├── MappingException.java  → Exceção customizada para erros de mapeamento
├── UsuarioMapper.java     → Mapper específico para Usuario (@Component)
└── EnderecoMapper.java    → Mapper específico para Endereco (@Component)
```

### DTOs Imutáveis com Records

```java
@Builder(toBuilder = true)
public record EnderecoDTO(
    Long id,
    Integer numero,
    String cep,
    String logradouro,
    // ... outros campos
) {}
```

**Benefícios:**
- ✅ Imutabilidade por padrão
- ✅ Equals, hashCode, toString automáticos
- ✅ `toBuilder()` para cópias modificadas
- ✅ Menos boilerplate

### Logs com Boas Práticas

```java
log.debug("Mapeando UsuarioEntity(id={}) para UsuarioDTO", entity.getId());
log.warn("Mesclagem ignorada: request={}, dto={}", request != null, dto != null);
log.error("Falha ao converter {} -> {}: {}", source, target, e.getMessage());
```

| Nível | Uso |
|-------|-----|
| `TRACE` | Detalhes internos (campos mapeados) |
| `DEBUG` | Fluxo de conversão (início/fim) |
| `WARN` | Situações inesperadas |
| `ERROR` | Falhas de execução |

---

## 📦 Pré-requisitos

Antes de começar, certifique-se de ter instalado em sua máquina:

- ☕ **Java 21** ou superior
- 🐳 **Docker** e **Docker Compose**
- 📦 **Maven 3.8+** (opcional, o projeto inclui Maven Wrapper)

### Verificando as instalações

```bash
java -version
docker --version
docker-compose --version
```

---

## ⚙️ Instalação e Configuração

### 1. Clone o repositório

```bash
git clone https://github.com/gabitxt/gerenciamento-usuario.git
cd gerenciamento-usuario
```

### 2. Configure as variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
POSTGRES_PASSWORD=sua_senha_segura
```

### 3. Inicie o banco de dados

```bash
docker-compose up -d
```

---

## 🚀 Executando a Aplicação

### Usando Maven Wrapper (recomendado)

**Windows:**
```powershell
.\mvnw.cmd spring-boot:run
```

**Linux/macOS:**
```bash
./mvnw spring-boot:run
```

### Usando Maven instalado

```bash
mvn spring-boot:run
```

### Build do projeto

```bash
# Windows
.\mvnw.cmd clean package

# Linux/macOS
./mvnw clean package
```

### Executando o JAR

```bash
java -jar target/gerenciamento-usuario-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em: `http://localhost:8080`

---

## 📡 Endpoints da API

### Usuários

| Método | Endpoint | Descrição | Status Code |
|--------|----------|-----------|-------------|
| `GET` | `/usuarios` | Lista todos os usuários | 200 |
| `GET` | `/usuarios/{id}` | Busca usuário por ID | 200 / 404 |
| `POST` | `/usuarios` | Cria um novo usuário | 201 |
| `PATCH` | `/usuarios/{id}` | Atualiza um usuário | 200 / 404 |
| `DELETE` | `/usuarios/{id}` | Remove um usuário | 204 / 404 |

### Endereços

| Método | Endpoint | Descrição | Status Code |
|--------|----------|-----------|-------------|
| `POST` | `/enderecos` | Cria um novo endereço | 200 |
| `GET` | `/enderecos/{id}` | Busca endereço por ID | 200 / 404 |

### Exemplos de Requisições

#### Criar Usuário
```bash
curl -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{
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
  }'
```

#### Criar Endereço
```bash
curl -X POST http://localhost:8080/enderecos \
  -H "Content-Type: application/json" \
  -d '{
    "cep": "01310100",
    "numero": 123,
    "complemento": "Apto 45"
  }'
```

**Resposta:**
```json
{
  "id": 1,
  "cep": "01310100",
  "numero": 123,
  "logradouro": "Avenida Paulista",
  "complemento": "Apto 45",
  "bairro": "Bela Vista",
  "localidade": "São Paulo",
  "estado": "SP",
  "regiao": "Sudeste"
}
```

---

## 📁 Estrutura do Projeto

```
gerenciamento-usuario/
├── 📄 compose.yaml              # Configuração Docker Compose
├── 📄 CRONOGRAMA.md             # Cronograma de desenvolvimento
├── 📂 docs/                     # Documentação técnica
│   ├── 📄 README.md             # Índice da documentação
│   ├── 📄 ARQUITETURA.md        # Arquitetura do sistema
│   ├── 📄 BANCO-DE-DADOS.md     # DER, DDL, dicionário
│   ├── 📄 DIAGRAMAS.md          # Diagramas UML
│   ├── 📄 API.md                # Documentação da API
│   ├── 📄 FLUXOS.md             # Fluxos de negócio
│   ├── 📄 INTEGRAÇÕES.md        # Integrações externas
│   └── 📄 GLOSSARIO.md          # Glossário técnico
├── 📄 mvnw                      # Maven Wrapper (Linux/macOS)
├── 📄 mvnw.cmd                  # Maven Wrapper (Windows)
├── 📄 pom.xml                   # Configuração Maven
├── 📄 README.md                 # Documentação principal
└── 📂 src/
    ├── 📂 main/
    │   ├── 📂 java/io/github/gabitxt/gerenciamentousuario/
    │   │   ├── 📄 GerenciamentoUsuarioApplication.java
    │   │   ├── 📂 api/
    │   │   │   ├── 📂 client/
    │   │   │   │   └── 📄 ViaCepClient.java         # Feign Client ViaCEP
    │   │   │   ├── 📂 dto/
    │   │   │   │   └── 📄 EnderecoApiResponse.java  # DTO resposta ViaCEP
    │   │   │   └── 📂 service/
    │   │   │       └── 📄 ViaCepService.java        # Service integração
    │   │   ├── 📂 config/
    │   │   │   └── 📄 GlobalExceptionHandler.java   # Handler de exceções
    │   │   ├── 📂 controller/
    │   │   │   ├── 📄 EnderecoController.java       # Controller endereços
    │   │   │   ├── 📄 UsuarioController.java        # Controller usuários
    │   │   │   └── 📂 request/
    │   │   │       ├── 📄 CriarUsuarioRequest.java  # DTO request criar
    │   │   │       ├── 📄 AtualizarUsuarioRequest.java # DTO request atualizar
    │   │   │       └── 📄 CriarEnderecoRequest.java # DTO request endereço
    │   │   ├── 📂 entity/
    │   │   │   ├── 📄 UsuarioEntity.java            # Entidade JPA Usuario
    │   │   │   └── 📄 EnderecoEntity.java           # Entidade JPA Endereco
    │   │   ├── 📂 enums/
    │   │   │   └── 📄 TipoDocumento.java            # Enum tipo documento
    │   │   ├── 📂 mapper/
    │   │   │   ├── 📄 MapperUtils.java              # Utilitário genérico
    │   │   │   ├── 📄 MappingException.java         # Exceção customizada
    │   │   │   ├── 📄 EnderecoMapper.java           # Mapper endereços
    │   │   │   └── 📄 UsuarioMapper.java            # Mapper usuários
    │   │   ├── 📂 model/
    │   │   │   ├── 📄 EnderecoDTO.java              # DTO endereço (Record)
    │   │   │   ├── 📄 ErroResponse.java             # DTO resposta erro
    │   │   │   └── 📄 UsuarioDTO.java               # DTO usuário
    │   │   ├── 📂 repository/
    │   │   │   ├── 📄 UsuarioRepository.java        # Repository Usuario
    │   │   │   └── 📄 EnderecoRepository.java       # Repository Endereco
    │   │   └── 📂 service/
    │   │       ├── 📄 EnderecoService.java          # Interface endereços
    │   │       ├── 📄 EnderecoServiceImpl.java      # Implementação endereços
    │   │       ├── 📄 UsuarioService.java           # Interface usuários
    │   │       └── 📄 UsuarioServiceImpl.java       # Implementação usuários
    │   └── 📂 resources/
    │       ├── 📄 application.yml                   # Configuração principal
    │       └── 📄 application-dev.yml               # Configuração dev
    └── 📂 test/
        ├── 📂 java/io/github/gabitxt/gerenciamentousuario/
        │   └── 📄 GerenciamentoUsuarioApplicationTests.java
        └── 📂 resources/
            └── 📄 application.yml
```

---

## 📚 Documentação Técnica

A documentação técnica completa está disponível na pasta [`docs/`](./docs/):

| Documento | Descrição |
|-----------|-----------|
| [📁 docs/README.md](./docs/README.md) | Índice da documentação |
| [🏗️ ARQUITETURA.md](./docs/ARQUITETURA.md) | Arquitetura do sistema, padrões e decisões |
| [🗄️ BANCO-DE-DADOS.md](./docs/BANCO-DE-DADOS.md) | DER, DDL, dicionário de dados |
| [📊 DIAGRAMAS.md](./docs/DIAGRAMAS.md) | Diagramas de classe, sequência, estados |
| [📡 API.md](./docs/API.md) | Documentação completa da API REST |
| [🔄 FLUXOS.md](./docs/FLUXOS.md) | Fluxos de negócio e casos de uso |
| [🌐 INTEGRAÇÕES.md](./docs/INTEGRAÇÕES.md) | Integração com ViaCEP e serviços externos |
| [📖 GLOSSARIO.md](./docs/GLOSSARIO.md) | Glossário de termos técnicos |

> 💡 Os diagramas utilizam [Mermaid](https://mermaid.js.org/) para renderização. O GitHub e editores como VS Code suportam nativamente.

---

## 🗺 Roadmap

Consulte o arquivo [CRONOGRAMA.md](CRONOGRAMA.md) para detalhes completos.

| Sprint | Período | Status | Entregável |
|--------|---------|--------|------------|
| Sprint 0 | Concluído | ✅ Concluído | Refatoração Mappers + DTOs |
| Sprint 1 | Semana 1-2 | 🔄 Planejado | Tratamento de Erros |
| Sprint 2 | Semana 3-4 | 📋 Backlog | Validadores |
| Sprint 3 | Semana 5-6 | 📋 Backlog | Testes Unitários |
| Sprint 4 | Semana 7-8 | 📋 Backlog | CRUD Endereço |

---

## 🐳 Docker Compose

O projeto utiliza Docker Compose para gerenciar o banco de dados PostgreSQL:

```yaml
services:
  postgres:
    image: 'postgres:latest'
    environment:
      - 'POSTGRES_DB=gerenciamento_usuario_db'
      - 'POSTGRES_PASSWORD=${POSTGRES_PASSWORD}'
      - 'POSTGRES_USER=myuser'
    ports:
      - '5432'
```

### Comandos úteis

```bash
# Iniciar containers
docker-compose up -d

# Parar containers
docker-compose down

# Ver logs
docker-compose logs -f postgres

# Acessar o banco de dados
docker exec -it <container_id> psql -U myuser -d gerenciamento_usuario_db
```

---

## 🤝 Contribuição

Contribuições são sempre bem-vindas! Siga os passos abaixo:

1. **Fork** o projeto
2. Crie uma **branch** para sua feature (`git checkout -b feature/nova-feature`)
3. **Commit** suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. **Push** para a branch (`git push origin feature/nova-feature`)
5. Abra um **Pull Request**

### Padrões de Commit

- `feat:` nova funcionalidade
- `fix:` correção de bug
- `docs:` alterações na documentação
- `style:` formatação de código
- `refactor:` refatoração de código
- `test:` adição ou correção de testes

---

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 📧 Contato

**gabitxt** - [@gabitxt](https://github.com/gabitxt)

Link do Projeto: [https://github.com/gabitxt/gerenciamento-usuario](https://github.com/gabitxt/gerenciamento-usuario)

---

<p align="center">
  Feito com ❤️ por <a href="https://github.com/gabitxt">gabitxt</a>
</p>

