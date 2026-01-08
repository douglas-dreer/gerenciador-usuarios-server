# 🔄 Fluxos de Negócio

## 📋 Índice

- [Visão Geral dos Casos de Uso](#visão-geral-dos-casos-de-uso)
- [Fluxos Detalhados](#fluxos-detalhados)
- [Regras de Negócio](#regras-de-negócio)
- [Diagramas de Atividade](#diagramas-de-atividade)

---

## Visão Geral dos Casos de Uso

```mermaid
graph TB
    subgraph "Atores"
        USER[👤 Usuário do Sistema]
        ADMIN[👨‍💼 Administrador]
        SYSTEM[🤖 Sistema]
    end
    
    subgraph "Casos de Uso - Usuários"
        UC1[Cadastrar Usuário]
        UC2[Consultar Usuário]
        UC3[Listar Usuários]
        UC4[Atualizar Usuário]
        UC5[Remover Usuário]
    end
    
    subgraph "Casos de Uso - Endereços"
        UC6[Cadastrar Endereço]
        UC7[Consultar CEP]
    end
    
    subgraph "Integrações"
        UC8[Integrar ViaCEP]
    end
    
    USER --> UC1
    USER --> UC2
    USER --> UC3
    USER --> UC4
    USER --> UC5
    USER --> UC6
    
    ADMIN --> UC1
    ADMIN --> UC2
    ADMIN --> UC3
    ADMIN --> UC4
    ADMIN --> UC5
    ADMIN --> UC6
    
    UC1 --> UC8
    UC6 --> UC8
    SYSTEM --> UC7
    UC8 --> UC7
```

---

## Fluxos Detalhados

### UC01 - Cadastrar Usuário

```mermaid
flowchart TD
    START([Início]) --> A[Cliente envia dados do usuário]
    A --> B{Dados válidos?}
    
    B -->|Não| C[Retorna erro de validação]
    C --> END1([Fim - Erro 400])
    
    B -->|Sim| D{Email já existe?}
    D -->|Sim| E[Retorna erro de conflito]
    E --> END2([Fim - Erro 409])
    
    D -->|Não| F{Possui endereço?}
    
    F -->|Não| G[Cria usuário sem endereço]
    G --> H[Salva no banco]
    H --> I[Retorna usuário criado]
    I --> END3([Fim - 201 Created])
    
    F -->|Sim| J[Extrai CEP do endereço]
    J --> K[Consulta ViaCEP]
    K --> L{CEP válido?}
    
    L -->|Não| M[Retorna erro CEP inválido]
    M --> END4([Fim - Erro 400])
    
    L -->|Sim| N[Mescla dados do endereço]
    N --> O[Cria usuário com endereço]
    O --> H
```

**Descrição:**
1. Cliente envia requisição POST com dados do usuário
2. Sistema valida campos obrigatórios
3. Sistema verifica se email já está cadastrado
4. Se possui endereço, consulta ViaCEP para completar dados
5. Sistema salva usuário no banco de dados
6. Retorna usuário criado com ID gerado

**Pré-condições:**
- API deve estar disponível
- ViaCEP deve estar acessível (se endereço informado)

**Pós-condições:**
- Usuário cadastrado no banco de dados
- Endereço preenchido automaticamente (se CEP válido)

---

### UC02 - Consultar Usuário

```mermaid
flowchart TD
    START([Início]) --> A[Cliente envia ID do usuário]
    A --> B{ID é válido?}
    
    B -->|Não| C[Retorna erro de validação]
    C --> END1([Fim - Erro 400])
    
    B -->|Sim| D[Busca usuário no banco]
    D --> E{Usuário encontrado?}
    
    E -->|Não| F[Retorna não encontrado]
    F --> END2([Fim - Erro 404])
    
    E -->|Sim| G[Converte para DTO]
    G --> H[Retorna usuário]
    H --> END3([Fim - 200 OK])
```

---

### UC03 - Listar Usuários

```mermaid
flowchart TD
    START([Início]) --> A[Cliente solicita lista]
    A --> B[Busca todos os usuários]
    B --> C{Lista vazia?}
    
    C -->|Sim| D[Retorna lista vazia]
    D --> END1([Fim - 200 OK])
    
    C -->|Não| E[Converte para DTOs]
    E --> F[Retorna lista de usuários]
    F --> END2([Fim - 200 OK])
```

---

### UC04 - Atualizar Usuário

```mermaid
flowchart TD
    START([Início]) --> A[Cliente envia ID e novos dados]
    A --> B{Usuário existe?}
    
    B -->|Não| C[Retorna não encontrado]
    C --> END1([Fim - Erro 404])
    
    B -->|Sim| D{Email alterado?}
    
    D -->|Não| E[Atualiza dados]
    
    D -->|Sim| F{Novo email já existe?}
    F -->|Sim| G[Retorna erro de conflito]
    G --> END2([Fim - Erro 409])
    
    F -->|Não| E
    
    E --> H[Salva no banco]
    H --> I[Retorna usuário atualizado]
    I --> END3([Fim - 200 OK])
```

**Regras de Negócio:**
- Não permite alterar email para um já existente
- Mantém dados não informados na requisição

---

### UC05 - Remover Usuário

```mermaid
flowchart TD
    START([Início]) --> A[Cliente envia ID do usuário]
    A --> B{Usuário existe?}
    
    B -->|Não| C[Retorna não encontrado]
    C --> END1([Fim - Erro 404])
    
    B -->|Sim| D[Remove usuário do banco]
    D --> E{Possui endereço?}
    
    E -->|Sim| F[Remove endereço em cascata]
    F --> G[Confirma exclusão]
    
    E -->|Não| G
    
    G --> END2([Fim - 204 No Content])
```

---

### UC06 - Cadastrar Endereço

```mermaid
flowchart TD
    START([Início]) --> A[Cliente envia dados do endereço]
    A --> B{CEP informado?}
    
    B -->|Não| C[Retorna erro de validação]
    C --> END1([Fim - Erro 400])
    
    B -->|Sim| D[Consulta ViaCEP]
    D --> E{CEP válido?}
    
    E -->|Não| F[Retorna CEP inválido]
    F --> END2([Fim - Erro 400])
    
    E -->|Sim| G[Mescla dados]
    Note right of G: Preserva número e<br/>complemento do cliente
    
    G --> H[Salva no banco]
    H --> I[Retorna endereço completo]
    I --> END3([Fim - 200 OK])
```

---

### UC07 - Integração ViaCEP

```mermaid
flowchart TD
    START([Início]) --> A[Recebe CEP]
    A --> B[Formata CEP - remove caracteres]
    B --> C{CEP tem 8 dígitos?}
    
    C -->|Não| D[Retorna erro de formato]
    D --> END1([Fim - Erro])
    
    C -->|Sim| E[Chama API ViaCEP]
    E --> F{Timeout?}
    
    F -->|Sim| G[Retorna erro de serviço]
    G --> END2([Fim - Erro 503])
    
    F -->|Não| H{Resposta OK?}
    
    H -->|Não| I{CEP não encontrado?}
    I -->|Sim| J[Retorna CEP inexistente]
    J --> END3([Fim - Erro 404])
    
    I -->|Não| K[Retorna erro genérico]
    K --> END4([Fim - Erro 500])
    
    H -->|Sim| L[Converte para EnderecoDTO]
    L --> M[Retorna endereço]
    M --> END5([Fim - Sucesso])
```

---

## Regras de Negócio

### RN01 - Unicidade de Email

```mermaid
graph LR
    A[Email informado] --> B{Já existe no banco?}
    B -->|Sim| C[❌ Rejeita operação]
    B -->|Não| D[✅ Permite operação]
```

**Descrição:** O email deve ser único no sistema. Não é permitido cadastrar dois usuários com o mesmo email.

**Aplicação:** Cadastro e Atualização de usuários.

---

### RN02 - Preenchimento Automático de Endereço

```mermaid
graph TB
    A[CEP informado] --> B[Consulta ViaCEP]
    B --> C[Obtém dados completos]
    C --> D[Mescla com dados do cliente]
    D --> E{Quais dados preservar?}
    E --> F[Número ← Cliente]
    E --> G[Complemento ← Cliente]
    E --> H[Logradouro ← ViaCEP]
    E --> I[Bairro ← ViaCEP]
    E --> J[Cidade ← ViaCEP]
    E --> K[Estado ← ViaCEP]
    E --> L[Região ← ViaCEP]
```

**Descrição:** Ao informar um CEP válido, os dados do endereço são automaticamente preenchidos via API ViaCEP. O número e complemento informados pelo cliente são preservados.

---

### RN03 - Exclusão em Cascata

```mermaid
graph TD
    A[Deletar Usuário] --> B{Possui endereço?}
    B -->|Sim| C[Deleta endereço primeiro]
    C --> D[Deleta usuário]
    B -->|Não| D
    D --> E[Operação concluída]
```

**Descrição:** Ao excluir um usuário, seu endereço associado também é excluído automaticamente (CASCADE).

---

### RN04 - Validação de Documento

| Tipo Documento | Formato | Validação |
|----------------|---------|-----------|
| CPF | XXX.XXX.XXX-XX | 11 dígitos + algoritmo |
| CNPJ | XX.XXX.XXX/XXXX-XX | 14 dígitos + algoritmo |

> **Nota:** Validação de documento está planejada para Sprint 2.

---

## Diagramas de Atividade

### Ciclo de Vida do Usuário

```mermaid
stateDiagram-v2
    [*] --> Inexistente
    
    Inexistente --> Criando: POST /usuarios
    Criando --> Ativo: Sucesso
    Criando --> Inexistente: Erro
    
    Ativo --> Consultando: GET /usuarios/{id}
    Consultando --> Ativo: Retorna dados
    
    Ativo --> Atualizando: PATCH /usuarios/{id}
    Atualizando --> Ativo: Sucesso
    Atualizando --> Ativo: Erro (mantém estado)
    
    Ativo --> Deletando: DELETE /usuarios/{id}
    Deletando --> Inexistente: Sucesso
    
    Ativo --> Listando: GET /usuarios
    Listando --> Ativo: Retorna na lista
```

### Fluxo de Processamento de CEP

```mermaid
stateDiagram-v2
    [*] --> Recebido: CEP informado
    
    Recebido --> Validando: Inicia validação
    
    Validando --> Invalido: Formato incorreto
    Invalido --> [*]: Erro 400
    
    Validando --> Consultando: Formato OK
    
    Consultando --> Encontrado: ViaCEP retorna dados
    Consultando --> NaoEncontrado: ViaCEP retorna erro
    Consultando --> Timeout: Sem resposta
    
    NaoEncontrado --> [*]: Erro 404
    Timeout --> [*]: Erro 503
    
    Encontrado --> Mesclando: Dados recebidos
    Mesclando --> Completo: Merge finalizado
    Completo --> [*]: Sucesso
```

---

## Matriz de Responsabilidades

| Caso de Uso | Controller | Service | Mapper | Repository | ViaCEP |
|-------------|------------|---------|--------|------------|--------|
| Cadastrar Usuário | ✅ | ✅ | ✅ | ✅ | ✅ |
| Consultar Usuário | ✅ | ✅ | ✅ | ✅ | - |
| Listar Usuários | ✅ | ✅ | ✅ | ✅ | - |
| Atualizar Usuário | ✅ | ✅ | ✅ | ✅ | - |
| Remover Usuário | ✅ | ✅ | - | ✅ | - |
| Cadastrar Endereço | ✅ | ✅ | ✅ | ✅ | ✅ |

---

<p align="center">
  <a href="./README.md">← Voltar ao Índice</a>
</p>

