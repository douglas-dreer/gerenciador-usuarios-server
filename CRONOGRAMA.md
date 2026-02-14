# 📅 Cronograma de Desenvolvimento - Gerenciamento de Usuários

## 📊 Visão Geral do Projeto

| Sprint | Período | Story Points | Status | Entregável |
|--------|---------|--------------|--------|------------|
| Sprint 0 | Concluído | 8 pts | ✅ Concluído | Refatoração Mappers + DTOs + Logs |
| Sprint 1 | Semana 1-2 | 13 pts | 🔄 Planejado | Tratamento de Erros |
| Sprint 2 | Semana 3-4 | 13 pts | 📋 Backlog | Validadores |
| Sprint 3 | Semana 5-6 | 21 pts | 📋 Backlog | Testes Unitários |
| Sprint 4 | Semana 7-8 | 21 pts | 📋 Backlog | CRUD Endereço |

**Total estimado:** 76 Story Points | **Duração:** 8 semanas

---

## ✅ Sprint 0: Refatoração de Código (CONCLUÍDA)

**Objetivo:** Implementar padrões SOLID, DTOs imutáveis e logs estruturados.

**Story Points:** 8 | **Prioridade:** Alta | **Status:** ✅ Concluído

### 📌 Epic 0.1: Refatoração dos Mappers (3 pts) ✅

#### Task 0.1.1: Criar MapperUtils genérico ✅
- [x] Criar classe utilitária `MapperUtils` com método `convert()`
- [x] Implementar conversão via Reflection
- [x] Suportar classes com `@Builder` do Lombok
- [x] Suportar Java Records
- [x] Implementar conversão recursiva para objetos aninhados

#### Task 0.1.2: Criar MappingException ✅
- [x] Criar exceção customizada para erros de mapeamento
- [x] Incluir mensagem descritiva com classe de origem e destino

#### Task 0.1.3: Refatorar UsuarioMapper ✅
- [x] Converter para `@Component` (injetável)
- [x] Usar `MapperUtils.convert()` internamente
- [x] Criar métodos específicos: `toDTO()`, `toEntity()`
- [x] Suportar múltiplos tipos de request

#### Task 0.1.4: Refatorar EnderecoMapper ✅
- [x] Converter para `@Component` (injetável)
- [x] Usar `MapperUtils.convert()` internamente
- [x] Criar método `merge()` para combinar dados
- [x] Remover métodos estáticos

### 📌 Epic 0.2: DTOs Imutáveis com Records (3 pts) ✅

#### Task 0.2.1: Converter EnderecoDTO para Record ✅
- [x] Mudar de `class` para `record`
- [x] Usar `@Builder(toBuilder = true)` do Lombok
- [x] Remover métodos de conversão (responsabilidade do Mapper)
- [x] Configurar valor default para `numero`

#### Task 0.2.2: Criar Request DTOs ✅
- [x] Criar `CriarUsuarioRequest` como record
- [x] Criar `AtualizarUsuarioRequest` como record
- [x] Incluir `CriarEnderecoRequest` aninhado

#### Task 0.2.3: Atualizar Services ✅
- [x] Refatorar `UsuarioServiceImpl` para usar novos requests
- [x] Refatorar `EnderecoServiceImpl` para usar `EnderecoMapper` injetado
- [x] Usar accessors de record (`cep()` ao invés de `getCep()`)

### 📌 Epic 0.3: Logs Estruturados (2 pts) ✅

#### Task 0.3.1: Adicionar logs no MapperUtils ✅
- [x] Log DEBUG no início/fim da conversão
- [x] Log TRACE para campos mapeados
- [x] Log WARN para situações inesperadas
- [x] Log ERROR para falhas de conversão
- [x] Usar placeholders `{}` (sem concatenação)

#### Task 0.3.2: Adicionar logs nos Mappers ✅
- [x] Usar `@Slf4j` do Lombok
- [x] Log DEBUG com IDs para rastreabilidade
- [x] Não logar dados sensíveis

#### Task 0.3.3: Corrigir warnings do Lombok ✅
- [x] Adicionar `@Builder.Default` em `EnderecoEntity.numero`
- [x] Resolver warnings de compilação

---

## 🔴 Sprint 1: Implementação de Tratamento de Erros

**Objetivo:** Criar um sistema robusto de tratamento de exceções com respostas padronizadas.

**Story Points:** 13 | **Prioridade:** Alta

### 📌 Epic 1.1: Estruturação das Exceções Customizadas (5 pts)

#### Task 1.1.1: Criar hierarquia de exceções base
- [X] Criar package `exception` em `io.github.gabitxt.gerenciamentousuario`
- [X] Criar classe abstrata `BusinessException` extends `RuntimeException`
  - [X] Adicionar campo `errorCode` (String)
  - [X] Adicionar campo `details` (Map<String, Object>)
  - [X] Implementar construtores adequados
- [X] Documentar com Javadoc

#### Task 1.1.2: Criar exceções de domínio
- [X] Criar `ResourceNotFoundException` extends `BusinessException`
  - [X] Construtor com parâmetros: resourceName, fieldName, fieldValue
  - [X] Exemplo: "Usuário não encontrado com id: 123"
- [X] Criar `DuplicateResourceException` extends `BusinessException`
  - [X] Construtor com parâmetros: resourceName, fieldName, fieldValue
  - [X] Exemplo: "Email já cadastrado: joao@email.com"
- [X] Criar `ValidationException` extends `BusinessException`
  - [X] Suporte para múltiplos erros de validação

#### Task 1.1.3: Criar exceções de integração
- [X] Criar `ExternalServiceException` extends `BusinessException`
  - [X] Para erros de comunicação com ViaCEP
- [X] Criar `InvalidCepException` extends `BusinessException`
  - [X] Para CEP inválido ou não encontrado

### 📌 Epic 1.2: Implementação do Global Exception Handler (5 pts)

#### Task 1.2.1: Configurar GlobalExceptionHandler
- [x] Adicionar anotação `@RestControllerAdvice` na classe
- [x] Adicionar `@Slf4j` para logging
- [x] Injetar `MessageSource` para internacionalização futura

#### Task 1.2.2: Implementar handlers específicos
- [x] Handler para `ResourceNotFoundException` → HTTP 404
  - [x] Logar warning com detalhes da exceção
  - [x] Retornar `ErrorResponse` padronizado
- [x] Handler para `DuplicateResourceException` → HTTP 409 (Conflict)
  - [x] Logar warning com detalhes
- [x] Handler para `ValidationException` → HTTP 400
  - [x] Retornar lista de erros de validação
- [x] Handler para `MethodArgumentNotValidException` → HTTP 400
  - [x] Mapear erros do Bean Validation
  - [x] Retornar campo + mensagem de erro
- [x] Handler para `HttpMessageNotReadableException` → HTTP 400
  - [x] Tratar JSON malformado
- [x] Handler para `ExternalServiceException` → HTTP 503
  - [x] Indicar serviço indisponível
- [x] Handler para `MappingException` → HTTP 500
  - [x] Logar error com stacktrace
  - [x] Mensagem genérica ao cliente
- [x] Handler para `Exception` genérica → HTTP 500
  - [x] Logar error com stacktrace
  - [x] Não expor detalhes internos ao cliente

#### Task 1.2.3: Implementar handler para Feign exceptions
- [x] Handler para `FeignException` → HTTP apropriado
- [x] Tratar timeout e connection refused

### 📌 Epic 1.3: Padronização de Respostas de Erro (3 pts)

#### Task 1.3.1: Criar DTOs de resposta de erro
- [x] Criar/atualizar `ErrorResponse` com campos:
  ```java
  - timestamp (LocalDateTime)
  - status (int)
  - error (String) // ex: "Not Found"
  - message (String) // ex: "Usuário não encontrado"
  - path (String) // ex: "/usuarios/123"
  - errorCode (String) // ex: "USER_NOT_FOUND"
  - traceId (String) // para rastreabilidade
  ```
- [x] Criar `ValidationErrorResponse` extends `ErrorResponse`:
  ```java
  - errors (List<FieldError>)
    - field (String)
    - message (String)
    - rejectedValue (Object)
  ```

#### Task 1.3.2: Documentar códigos de erro
- [x] Criar seção no README com tabela de códigos de erro
- [x] Incluir exemplos de respostas de erro em cada endpoint

#### Task 1.3.3: Refatorar Services existentes
- [x] Refatorar `UsuarioServiceImpl.buscarUsuarioPorId()`:
  - [x] Substituir `return null` por `throw new ResourceNotFoundException`
- [x] Refatorar `UsuarioServiceImpl.atualizarUsuario()`:
  - [x] Substituir `throw new Exception` por exceções específicas
- [x] Refatorar `UsuarioServiceImpl.deletarUsuario()`:
  - [x] Substituir `throw new Exception` por exceções específicas
- [x] Remover `throws Exception` das assinaturas dos métodos

---

## 🟡 Sprint 2: Implementação de Validadores

**Objetivo:** Implementar validações robustas nos dados de entrada.

**Story Points:** 13 | **Prioridade:** Alta

### 📌 Epic 2.1: Validações de Campos Básicos (5 pts)

#### Task 2.1.1: Configurar Bean Validation nos Request DTOs
- [ ] Adicionar validações em `CriarUsuarioRequest`:
  - [ ] `@NotBlank(message = "Nome é obrigatório")` no campo `nome`
  - [ ] `@Size(min = 2, max = 100)` no campo `nome`
  - [ ] `@NotBlank(message = "Email é obrigatório")` no campo `email`
  - [ ] `@Email(message = "Email inválido")` no campo `email`
  - [ ] `@NotNull(message = "Data de nascimento é obrigatória")` em `dataNascimento`
  - [ ] `@Past(message = "Data deve ser no passado")` em `dataNascimento`
  - [ ] `@NotNull` em `tipoDocumento`
  - [ ] `@NotBlank` em `numeroDocumento`
- [ ] Adicionar validações em `AtualizarUsuarioRequest`
- [ ] Adicionar validações em `CriarEnderecoRequest`:
  - [ ] `@NotBlank` e `@Pattern` no `cep`
  - [ ] `@Positive` no `numero`

#### Task 2.1.2: Ativar validação nos Controllers
- [ ] Adicionar `@Valid` no parâmetro `@RequestBody` do POST `/usuarios`
- [ ] Adicionar `@Valid` no parâmetro `@RequestBody` do PATCH `/usuarios/{id}`
- [ ] Adicionar `@Valid` no parâmetro `@RequestBody` do POST `/enderecos`
- [ ] Testar manualmente as validações

#### Task 2.1.3: Criar mensagens customizadas
- [ ] Criar arquivo `messages.properties` em resources
- [ ] Externalizar mensagens de validação
- [ ] Configurar `MessageSource` para i18n

### 📌 Epic 2.2: Validações Customizadas (5 pts)

#### Task 2.2.1: Criar validador de CPF
- [ ] Criar package `validation`
- [ ] Criar anotação `@ValidCPF`
- [ ] Criar classe `CpfValidator` implements `ConstraintValidator<ValidCPF, String>`
  - [ ] Implementar algoritmo de validação de CPF
  - [ ] Tratar CPFs com máscara (xxx.xxx.xxx-xx)
  - [ ] Tratar CPFs sem máscara (xxxxxxxxxxx)
  - [ ] Rejeitar CPFs com dígitos repetidos (111.111.111-11)

#### Task 2.2.2: Criar validador de CNPJ
- [ ] Criar anotação `@ValidCNPJ`
- [ ] Criar classe `CnpjValidator` implements `ConstraintValidator<ValidCNPJ, String>`

#### Task 2.2.3: Criar validador de documento dinâmico
- [ ] Criar anotação `@ValidDocument` em nível de classe
- [ ] Criar `DocumentValidator` que valida baseado no `tipoDocumento`

#### Task 2.2.4: Criar validador de CEP
- [ ] Criar anotação `@ValidCEP`
- [ ] Criar `CepValidator` com regex para formato brasileiro (XXXXX-XXX ou XXXXXXXX)

### 📌 Epic 2.3: Validações de Regras de Negócio (3 pts)

#### Task 2.3.1: Criar validador de email único
- [ ] Criar anotação `@UniqueEmail`
- [ ] Criar `UniqueEmailValidator`
- [ ] Permitir o próprio email em atualizações

#### Task 2.3.2: Criar validador de documento único
- [ ] Criar anotação `@UniqueDocument`
- [ ] Verificar combinação tipoDocumento + numeroDocumento

#### Task 2.3.3: Criar validador de idade mínima
- [ ] Criar anotação `@MinimumAge(value = 18)`
- [ ] Criar `MinimumAgeValidator`

---

## 🟢 Sprint 3: Implementação de Testes Unitários

**Objetivo:** Alcançar cobertura de testes ≥ 80% nas camadas Service e Controller.

**Story Points:** 21 | **Prioridade:** Média-Alta

### 📌 Epic 3.1: Configuração do Ambiente de Testes (3 pts)

#### Task 3.1.1: Configurar dependências
- [ ] Verificar dependência `spring-boot-starter-test` no pom.xml
- [ ] Adicionar dependência do H2 Database para testes
- [ ] Adicionar dependência AssertJ

#### Task 3.1.2: Configurar application-test.yml
- [ ] Criar `src/test/resources/application-test.yml`
- [ ] Configurar H2 in-memory database
- [ ] Desabilitar integração com ViaCEP nos testes

#### Task 3.1.3: Criar classes utilitárias de teste
- [ ] Criar `TestDataFactory`:
  - [ ] `createValidCriarUsuarioRequest()`
  - [ ] `createValidUsuarioEntity()`
  - [ ] `createValidEnderecoDTO()`
- [ ] Criar `TestConstants` com valores fixos

### 📌 Epic 3.2: Testes da Camada Service (8 pts)

#### Task 3.2.1: Criar classe UsuarioServiceImplTest
- [ ] Usar `@ExtendWith(MockitoExtension.class)`
- [ ] Mockar `UsuarioRepository`, `UsuarioMapper`, `EnderecoMapper`, `ViaCepService`

#### Task 3.2.2: Testes para criarUsuario()
- [ ] `deveCriarUsuarioComSucesso()`
- [ ] `deveCriarUsuarioComEnderecoPreenchidoViaViaCep()`
- [ ] `deveLancarExcecaoQuandoEmailDuplicado()`

#### Task 3.2.3: Testes para buscarUsuarioPorId()
- [ ] `deveRetornarUsuarioQuandoIdExiste()`
- [ ] `deveLancarExcecaoQuandoIdNaoExiste()`

#### Task 3.2.4: Testes para listarUsuarios()
- [ ] `deveRetornarListaDeUsuarios()`
- [ ] `deveRetornarListaVaziaQuandoNaoHaUsuarios()`

#### Task 3.2.5: Testes para atualizarUsuario()
- [ ] `deveAtualizarUsuarioComSucesso()`
- [ ] `deveLancarExcecaoQuandoUsuarioNaoExiste()`
- [ ] `deveLancarExcecaoQuandoEmailPertenceAOutroUsuario()`

#### Task 3.2.6: Testes para deletarUsuario()
- [ ] `deveDeletarUsuarioComSucesso()`
- [ ] `deveLancarExcecaoQuandoUsuarioNaoExisteParaDeletar()`

### 📌 Epic 3.3: Testes da Camada Mapper (5 pts)

#### Task 3.3.1: Criar classe MapperUtilsTest
- [ ] `deveConverterClasseParaClasse()`
- [ ] `deveConverterRecordParaClasse()`
- [ ] `deveConverterObjetosAninhados()`
- [ ] `deveRetornarNullQuandoSourceNull()`
- [ ] `deveLancarMappingExceptionQuandoFalha()`

#### Task 3.3.2: Criar classe UsuarioMapperTest
- [ ] `deveMappearEntityParaDTO()`
- [ ] `deveMappearCriarRequestParaEntity()`
- [ ] `deveMappearAtualizarRequestParaEntity()`

#### Task 3.3.3: Criar classe EnderecoMapperTest
- [ ] `deveMappearEntityParaDTO()`
- [ ] `deveMappearDTOParaEntity()`
- [ ] `deveMergePreservandoNumeroEComplemento()`

### 📌 Epic 3.4: Testes da Camada Controller (5 pts)

#### Task 3.4.1: Criar classe UsuarioControllerTest
- [ ] Usar `@WebMvcTest(UsuarioController.class)`
- [ ] Mockar `UsuarioService` com `@MockBean`

#### Task 3.4.2: Testes para endpoints
- [ ] `GET /usuarios/{id}` → 200 e 404
- [ ] `GET /usuarios` → 200
- [ ] `POST /usuarios` → 201 e 400
- [ ] `PATCH /usuarios/{id}` → 200 e 404
- [ ] `DELETE /usuarios/{id}` → 204 e 404

---

## 🔵 Sprint 4: Criação do CRUD Endereço

**Objetivo:** Implementar CRUD completo para endereços vinculados a usuários.

**Story Points:** 21 | **Prioridade:** Média

### 📌 Epic 4.1: Modelagem de Dados (5 pts)

#### Task 4.1.1: Atualizar entidade EnderecoEntity
- [ ] Configurar relacionamento `@ManyToOne` com `UsuarioEntity`
- [ ] Adicionar campo `principal` (Boolean) para endereço principal
- [ ] Criar índice no campo CEP

#### Task 4.1.2: Configurar relacionamento bidirecional
- [ ] Em `UsuarioEntity`, adicionar `@OneToMany(mappedBy = "usuario")`
- [ ] Configurar `cascade = CascadeType.ALL`
- [ ] Implementar métodos auxiliares: `addEndereco()`, `removeEndereco()`

#### Task 4.1.3: Atualizar DTOs
- [ ] Adicionar campo `usuarioId` em `EnderecoDTO`
- [ ] Criar `AtualizarEnderecoRequest` como record

### 📌 Epic 4.2: Camada Repository (2 pts)

#### Task 4.2.1: Atualizar EnderecoRepository
- [ ] Implementar queries:
  ```java
  List<EnderecoEntity> findByUsuarioId(Long usuarioId);
  Optional<EnderecoEntity> findByIdAndUsuarioId(Long id, Long usuarioId);
  Optional<EnderecoEntity> findByUsuarioIdAndPrincipalTrue(Long usuarioId);
  ```

### 📌 Epic 4.3: Camada Service (6 pts)

#### Task 4.3.1: Atualizar interface EnderecoService
- [ ] `criarEndereco(Long usuarioId, CriarEnderecoRequest)`
- [ ] `listarEnderecos(Long usuarioId)`
- [ ] `buscarEndereco(Long usuarioId, Long enderecoId)`
- [ ] `atualizarEndereco(Long usuarioId, Long enderecoId, AtualizarEnderecoRequest)`
- [ ] `deletarEndereco(Long usuarioId, Long enderecoId)`
- [ ] `definirEnderecoPrincipal(Long usuarioId, Long enderecoId)`

#### Task 4.3.2: Implementar EnderecoServiceImpl completo
- [ ] Implementar todos os métodos
- [ ] Validar se usuário existe
- [ ] Integrar com ViaCEP
- [ ] Gerenciar endereço principal

### 📌 Epic 4.4: Camada Controller (5 pts)

#### Task 4.4.1: Atualizar EnderecoController
- [ ] `POST /usuarios/{usuarioId}/enderecos` → 201
- [ ] `GET /usuarios/{usuarioId}/enderecos` → 200
- [ ] `GET /usuarios/{usuarioId}/enderecos/{id}` → 200/404
- [ ] `PUT /usuarios/{usuarioId}/enderecos/{id}` → 200
- [ ] `DELETE /usuarios/{usuarioId}/enderecos/{id}` → 204
- [ ] `PATCH /usuarios/{usuarioId}/enderecos/{id}/principal` → 200

### 📌 Epic 4.5: Testes e Documentação (3 pts)

#### Task 4.5.1: Implementar testes
- [ ] `EnderecoServiceImplTest`
- [ ] `EnderecoControllerTest`

#### Task 4.5.2: Atualizar documentação
- [ ] Adicionar endpoints no README
- [ ] Documentar regras de negócio

---

## 📊 Métricas de Conclusão

| Critério | Meta | Sprint 0 | Sprint 1 | Sprint 2 | Sprint 3 | Sprint 4 |
|----------|------|----------|----------|----------|----------|----------|
| Cobertura de Testes | ≥ 80% | N/A | N/A | N/A | ✅ | ✅ |
| Bugs Críticos | 0 | ✅ | ✅ | ✅ | ✅ | ✅ |
| Code Review Aprovado | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Documentação Atualizada | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Build Passando | 100% | ✅ | ✅ | ✅ | ✅ | ✅ |

---

## 📝 Definição de Pronto (Definition of Done)

Cada task é considerada **PRONTA** quando:

1. ✅ Código implementado e funcionando
2. ✅ Testes unitários escritos e passando (quando aplicável)
3. ✅ Code review realizado e aprovado
4. ✅ Documentação atualizada
5. ✅ Sem warnings de compilação
6. ✅ Build passando no CI/CD

---

## 🔄 Legenda de Status

| Ícone | Status |
|-------|--------|
| 📋 | Backlog |
| 🔄 | Planejado |
| 🚧 | Em Progresso |
| ✅ | Concluído |
| ⏸️ | Pausado |
| ❌ | Cancelado |
