# Cronograma de Desenvolvimento

# 📅 Cronograma de Desenvolvimento - Gerenciamento de Usuários

## Visão Geral
| Sprint | Período | Entregável |
|--------|---------|------------|
| Sprint 1 | Semana 1-2 | Tratamento de Erros |
| Sprint 2 | Semana 3-4 | Validadores |
| Sprint 3 | Semana 5-6 | Testes Unitários |
| Sprint 4 | Semana 7-8 | CRUD Endereço |

---

## 🔴 Sprint 1: Implementação de Tratamento de Erros

### 1.1 Estruturação das Exceções Customizadas
- [ ] Criar classe `BusinessException` (exceção base)
- [ ] Criar `ResourceNotFoundException` para recursos não encontrados
- [ ] Criar `ValidationException` para erros de validação
- [ ] Criar `DuplicateResourceException` para dados duplicados

### 1.2 Implementação do Global Exception Handler
- [ ] Criar classe `GlobalExceptionHandler` com `@ControllerAdvice`
- [ ] Implementar handler para `ResourceNotFoundException`
- [ ] Implementar handler para `MethodArgumentNotValidException`
- [ ] Implementar handler para exceções genéricas

### 1.3 Padronização de Respostas de Erro
- [ ] Criar DTO `ErrorResponse` com campos: timestamp, status, message, path
- [ ] Criar DTO `ValidationErrorResponse` para erros de validação
- [ ] Documentar códigos de erro no README

---

## 🟡 Sprint 2: Implementação de Validadores

### 2.1 Validações de Campos Básicos
- [ ] Adicionar Bean Validation no `UserDTO` (@NotBlank, @Email, @Size)
- [ ] Implementar validação de CPF com anotação customizada
- [ ] Implementar validação de telefone com regex

### 2.2 Validações de Regras de Negócio
- [ ] Criar validator para verificar email único
- [ ] Criar validator para verificar CPF único
- [ ] Implementar validação de idade mínima (se aplicável)

### 2.3 Validações Customizadas
- [ ] Criar anotação `@ValidCPF`
- [ ] Criar anotação `@UniqueEmail`
- [ ] Criar classe `ConstraintValidator` para cada anotação

---

## 🟢 Sprint 3: Implementação de Testes Unitários

### 3.1 Configuração do Ambiente de Testes
- [ ] Adicionar dependências (JUnit 5, Mockito, AssertJ)
- [ ] Configurar `application-test.properties`
- [ ] Criar classe base para testes

### 3.2 Testes da Camada Service
- [ ] Testes para `createUser()` (sucesso e falhas)
- [ ] Testes para `findById()` (encontrado e não encontrado)
- [ ] Testes para `findAll()` (com e sem dados)
- [ ] Testes para `updateUser()` (sucesso e validações)
- [ ] Testes para `deleteUser()` (existente e inexistente)

### 3.3 Testes da Camada Controller
- [ ] Testes de integração com `@WebMvcTest`
- [ ] Testar endpoints POST, GET, PUT, DELETE
- [ ] Testar respostas de erro (400, 404, 500)

### 3.4 Testes de Validação
- [ ] Testar validadores customizados
- [ ] Testar mensagens de erro de validação

---

## 🔵 Sprint 4: Criação do CRUD Endereço

### 4.1 Modelagem
- [ ] Criar entidade `Address` com relacionamento `@ManyToOne` para User
- [ ] Criar `AddressDTO` e `AddressResponseDTO`
- [ ] Criar migration/script para tabela `addresses`

### 4.2 Camada Repository
- [ ] Criar `AddressRepository` extends `JpaRepository`
- [ ] Implementar query `findByUserId()`
- [ ] Implementar query `findByUserIdAndId()`

### 4.3 Camada Service
- [ ] Criar `AddressService` com operações CRUD
- [ ] Implementar validação de CEP (integração com ViaCEP - opcional)
- [ ] Implementar limite de endereços por usuário (se aplicável)

### 4.4 Camada Controller
- [ ] Criar `AddressController` com endpoints REST
- [ ] `POST /users/{userId}/addresses`
- [ ] `GET /users/{userId}/addresses`
- [ ] `GET /users/{userId}/addresses/{id}`
- [ ] `PUT /users/{userId}/addresses/{id}`
- [ ] `DELETE /users/{userId}/addresses/{id}`

### 4.5 Testes e Documentação
- [ ] Implementar testes unitários para Address
- [ ] Atualizar documentação da API (Swagger/README)

---

## 📊 Métricas de Conclusão

| Critério | Meta |
|----------|------|
| Cobertura de Testes | ≥ 80% |
| Bugs Críticos | 0 |
| Code Review Aprovado | ✅ |
| Documentação Atualizada | ✅ |
```