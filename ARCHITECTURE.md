# Arquitetura do Projeto MindCash

Este documento descreve a arquitetura e os padrões utilizados no projeto MindCash, com foco no módulo `financial-account`. Ele serve como guia para manter consistência em novos módulos e desenvolvimento futuro, seguindo princípios de Clean Architecture, Domain-Driven Design (DDD) e boas práticas de Clean Code.

## Visão Geral

O MindCash é uma aplicação Java Spring Boot para gerenciamento financeiro, estruturada como um projeto multi-módulo Gradle. O módulo `financial-account` exemplifica a arquitetura, gerenciando despesas (expenses) e receitas (incomes) com persistência em MySQL via JDBC e migrações Flyway.

A arquitetura é baseada em **Clean Architecture** (ou Arquitetura Hexagonal), garantindo isolamento entre camadas e testabilidade. Ela separa responsabilidades em camadas independentes, evitando acoplamento entre domínio e infraestrutura.

## Princípios Arquiteturais

- **Clean Architecture**: Camadas independentes com dependências direcionadas para dentro (interno não conhece externo).
- **DDD (Domain-Driven Design)**: Foco no domínio de negócio, com Value Objects, Entities, Aggregates, Events e Ubiquitous Language.
- **CQRS (Command Query Responsibility Segregation)**: Separação entre comandos (mudanças de estado) e consultas (leituras).
- **SOLID**: Princípios aplicados em classes e interfaces.
- **Imutabilidade**: Uso de records e objetos imutáveis para reduzir bugs.
- **Testabilidade**: Testes unitários prioritários, com mocks apenas para dependências externas.

## Estrutura de Camadas

O projeto segue uma estrutura hexagonal com três camadas principais:

### 1. Driving (Entrada/Externa)
   - **Responsabilidade**: Recebe entradas externas (ex.: HTTP requests) e as converte para comandos/queries da aplicação.
   - **Pacotes**: `driving.http.[feature]`
   - **Exemplos**:
     - `ExpenseController`: Endpoint POST `/expenses` para registrar despesa.
     - `IncomeController`: Endpoint GET `/incomes/{accountId}` para listar receitas.
   - **Padrões**: Controladores REST com `@RestController`, validações com `@Valid`, responses em JSON usando records.

### 2. Application (Aplicação)
   - **Responsabilidade**: Contém lógica de negócio pura, sem dependências externas.
   - **Subcamadas**:
     - `commands`: Comandos imutáveis (ex.: `RegisterExpense`).
     - `handlers`: Manipuladores que executam comandos, injetando portas outbound.
     - `domain.events`: Eventos de domínio (ex.: `ExpenseRegistered` implementando `AccountEvent`).
     - `domain.models`: Modelos de domínio (Value Objects como `Amount`, `Description`; Enums como `Status`, `Category`).
     - `ports`: Interfaces para comunicação entre camadas (inbound para driving, outbound para driven).
   - **Padrões**: Comandos são records com validações. Handlers usam injeção de dependências. Eventos são publicados via portas.

### 3. Driven (Saída/Infraestrutura)
   - **Responsabilidade**: Implementa adaptações para infraestrutura (DB, APIs externas), sem lógica de negócio.
   - **Pacotes**: `driven.[nome]`
   - **Exemplos**:
     - `Adapter`: Classe principal com `@Component`, injetando `JdbcTemplate`.
     - `Selects`: Queries SQL para leituras.
     - `statements.[feature]`: Builders de SQL para inserts/updates (ex.: `ExpenseRegisteredStatement.from(event)`).
   - **Padrões**: SQL direto com JDBC, evitando ORMs. Migrações Flyway em `src/main/resources/db/migration/`.

## Tecnologias e Ferramentas

- **Linguagem**: Java 21.
- **Framework**: Spring Boot (Web, JDBC, Validation, DevTools).
- **Build**: Gradle (multi-módulo, com `settings.gradle.kts`).
- **Banco**: MySQL, com Flyway para migrações.
- **Testes**: JUnit 5, Mockito (unitários); JaCoCo para cobertura (HTML/XML/CSV).
- **Outros**: Docker Compose para ambiente local (se aplicável).

## Padrão Outbox e Event Sourcing

O projeto implementa o **Outbox Pattern** para garantir consistência entre operações de negócio e publicação de eventos, combinado com snapshots de agregados para reconstrução de estado.

### Estrutura de Revisões (`driven/revision/`)

A pasta `revision` organiza versões do agregado, permitindo evolução sem quebrar compatibilidade:

```
driven/
└── revision/
    └── v1/
        ├── AggregateLoader.java       # Carrega estado atual do agregado
        ├── aggregate/
        │   ├── AggregateV1.java       # Record do agregado (AccountId, balance, currency, revision)
        │   └── AggregateV1Snapshot.java # Serializa agregado para JSON
        └── events/
            ├── RevisionEvent.java      # Encapsula evento para outbox
            ├── ExpenseRegisteredEvent.java # Serializa ExpenseRegistered para JSON
            └── IncomeRegisteredEvent.java  # Serializa IncomeRegistered para JSON
```

### AggregateV1 (Agregado)

O agregado representa o estado atual de uma conta financeira:

```java
public record AggregateV1(
    AccountId accountId,
    BigDecimal balance,
    Currency currency,
    int revision
) {
    public static AggregateV1 empty(AccountId accountId);  // Cria agregado vazio
    public AggregateV1 apply(AccountEvent event);          // Aplica evento e retorna novo estado
    public String aggregateName();                         // Nome para outbox ("Account")
    public String aggregateCode();                         // ID para outbox (accountId)
}
```

- **Imutável**: Cada `apply()` retorna um novo agregado.
- **Revision**: Incrementa a cada evento aplicado.
- **Balance**: Calculado a partir de receitas (+) e despesas (-).

### Tabela Outbox

A tabela `outbox` persiste eventos e snapshots do agregado:

| Coluna | Tipo | Descrição |
|--------|------|-----------|
| `idt_outbox_event` | BINARY(16) | UUID do evento |
| `nam_event` | VARCHAR(100) | Nome do evento (ex: "ExpenseRegistered") |
| `dat_event` | DATETIME(6) | Timestamp do evento |
| `jsn_event_payload` | JSON | Payload completo do evento |
| `cod_aggregate` | VARCHAR(36) | ID do agregado (AccountId) |
| `nam_aggregate` | VARCHAR(100) | Nome do agregado ("Account") |
| `jsn_aggregate_snapshot` | JSON | Snapshot do estado após o evento |
| `num_revision` | INT | Número da revisão |

### Fluxo de Persistência

1. **Handler** executa comando e cria evento de domínio.
2. **Adapter.save()** recebe o evento.
3. **AccountEventStatements.from()** gera lista de statements:
   - Statement do evento (insert na tabela específica: expense/income).
   - Statement da outbox (insert com evento + snapshot do agregado).
4. **AggregateLoader.load()** carrega último snapshot da outbox.
5. **AggregateV1.apply()** aplica evento ao agregado, gerando novo estado.
6. **OutboxStatement.from()** serializa evento e agregado para JSON.
7. Todos statements executam na mesma transação (`@Transactional`).

### Exemplo de Uso

```java
// Em AccountEventStatements
public List<Statement> from(AccountEvent event) {
    List<Statement> statements = new ArrayList<>();
    
    // Insert na tabela específica
    Statement eventStatement = switch (event) {
        case ExpenseRegistered ev -> ExpenseRegisteredStatement.from(ev);
        case IncomeRegistered ev -> IncomeRegisteredStatement.from(ev);
    };
    statements.add(eventStatement);
    
    // Carrega agregado, aplica evento, persiste na outbox
    AccountId accountId = extractAccountId(event);
    AggregateV1 currentAggregate = aggregateLoader.load(accountId);
    AggregateV1 newAggregate = currentAggregate.apply(event);
    
    Statement outboxStatement = OutboxStatement.from(event, newAggregate);
    statements.add(outboxStatement);
    
    return statements;
}
```

### Benefícios

- **Consistência**: Evento e agregado persistem na mesma transação.
- **Auditoria**: Histórico completo de eventos e estados.
- **Reconstrução**: Agregado pode ser reconstruído a partir de snapshots.
- **Evolução**: Novas versões de agregado (v2, v3) podem coexistir.

## Estrutura de Módulos

O projeto é multi-módulo Gradle. Cada módulo (ex.: `financial-account`) tem:
- `build.gradle.kts`: Dependências e configurações (plugins: java, spring-boot, jacoco).
- `src/main/java/br/com/mindcash/financial/`: Código fonte.
- `src/main/resources/`: `application.yml` (config DB/Flyway), `db/migration/` (scripts SQL).
- `src/test/java/`: Testes unitários.
- `build/`: Artefatos gerados (classes, reports).

Para adicionar um novo módulo:
1. Crie pasta `financial-[nome]/`.
2. Adicione em `settings.gradle.kts`: `include("financial-[nome]")`.
3. Copie estrutura do `financial-account`.
4. Atualize `Makefile` para incluir `:financial-[nome]:test`.

## Padrões de Desenvolvimento

### Código
- **Imutabilidade**: Use records para comandos, eventos e models.
- **Validações**: Bean Validation em requests/commands.
- **IDs**: UUIDs como strings, convertidos para BINARY(16) no DB.
- **Enums**: Para tipos fixos (ex.: `Status.PAGO`, `Category.ALIMENTACAO`).
- **Logs**: SLF4J para debugging.
- **Exceções**: Customizadas para domínio (ex.: `InvalidAmountException`).

### Banco de Dados
- **Tabelas**: Prefixo `idt_` para IDs (BINARY(16)), `dat_` para datas, `des_` para descrições, `val_` para valores, `ind_` para enums.
- **Migrações**: Scripts Flyway nomeados `V000X__Descricao.sql`.
- **Índices**: Adicione em FKs e campos de busca.
- **Constraints**: FKs e checks para integridade.

### Testes
- **Unitários**: Teste handlers, statements e models. Use valores reais para comandos/requests; mocke apenas `JdbcTemplate` ou portas.
- **Cobertura**: JaCoCo gera relatórios em `build/reports/jacoco/html/`. Meta: >80%.
- **Configuração**: `@SpringBootTest` apenas para integração; prefira testes isolados.

### APIs
- **REST**: Endpoints CRUD com HTTP status apropriados (201 Created, 400 Bad Request).
- **Requests/Responses**: Records imutáveis.
- **Documentação**: Use comentários ou Swagger se expandir.

## Exemplo de Implementação: Novo Módulo `financial-budget`

Para criar `financial-budget` (gerenciamento de orçamentos):
1. **Domínio**: Entity `Budget` (BudgetId, AccountId, Amount, Period). Event `BudgetCreated`. Command `CreateBudget`.
2. **Estrutura**: Copie pacotes do `financial-account`, adaptando nomes.
3. **Migration**: `V0003__Create_budget_table.sql` com tabela `budget`.
4. **Controller**: POST `/budgets` injetando `CreateBudgetUseCase` (porta inbound).
5. **Handler**: `CreateBudgetHandler` executando insert via `Adapter`.
6. **Testes**: Unitários para handler e statement.

## Boas Práticas e Regras

- **Não acople camadas**: Domain não conhece driven (use portas).
- **Commits**: Mensagens claras (ex.: "Add budget module following Clean Arch").
- **Code Review**: Sempre revisar aderência à arquitetura.
- **Refatoração**: Mantenha consistência; evite duplicação.
- **Documentação**: Atualize este arquivo ao adicionar padrões.

Este guia garante que novos módulos sigam o padrão do `financial-account`, mantendo a qualidade e escalabilidade do projeto. Para dúvidas, consulte o código existente ou abra issues.</content>
<parameter name="filePath">C:/Users/DIOGO/OneDrive/Desktop/Sandbox/Projetos/Backend/Java/mind-cash-be/ARCHITECTURE.md
