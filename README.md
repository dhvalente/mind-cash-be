# 💰 MindCash - Sistema de Gestão Financeira Pessoal

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-4.0-green?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
  <img src="https://img.shields.io/badge/Gradle-8.x-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle"/>
  <img src="https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge" alt="Status"/>
</p>

<p align="center">
  <strong>Sistema modular de gestão financeira pessoal desenvolvido com Clean Architecture, DDD e Event Sourcing</strong>
</p>

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Módulos](#-módulos)
- [Arquitetura](#-arquitetura)
- [Padrões e Princípios](#-padrões-e-princípios)
- [Tecnologias](#-tecnologias)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Execução](#-instalação-e-execução)
- [Documentação das APIs](#-documentação-das-apis)
- [Testes e Cobertura](#-testes-e-cobertura)
- [Roadmap](#-roadmap)
- [Autor](#-autor)

---

## 📖 Sobre o Projeto

O **MindCash** é uma aplicação backend para gerenciamento financeiro pessoal, desenvolvida como projeto de portfólio para demonstrar conhecimentos em:

- **Arquitetura de Software** (Clean Architecture, Hexagonal Architecture)
- **Domain-Driven Design** (DDD)
- **Event Sourcing e Outbox Pattern**
- **CQRS** (Command Query Responsibility Segregation)
- **Boas práticas de desenvolvimento** (SOLID, Clean Code, TDD)

O sistema permite o controle completo de finanças pessoais, incluindo receitas, despesas, investimentos em ações e geração de relatórios analíticos.

---

## 📦 Módulos

| Módulo | Descrição | Status |
|--------|-----------|--------|
| **financial-account** | Gestão de receitas e despesas | ✅ Implementado |
| **financial-stocks** | Gestão de carteira de ações e investimentos | 🚧 Em Construção |
| **financial-reports** | Relatórios e dashboards analíticos | 🚧 Em Construção |

### 📊 Diagrama de Módulos

```
┌─────────────────────────────────────────────────────────────┐
│                        MindCash                              │
├─────────────────┬─────────────────┬─────────────────────────┤
│  financial-     │  financial-     │  financial-             │
│  account        │  stocks         │  reports                │
│  ✅ Ativo       │  🚧 Planejado   │  🚧 Planejado           │
├─────────────────┴─────────────────┴─────────────────────────┤
│                    Shared Kernel                             │
│              (Account, Events, Outbox)                       │
└─────────────────────────────────────────────────────────────┘
```

---

## 🏗️ Arquitetura

O projeto segue **Clean Architecture** (também conhecida como Arquitetura Hexagonal ou Ports & Adapters), garantindo:

- Independência de frameworks
- Testabilidade
- Independência de UI
- Independência de banco de dados
- Independência de agentes externos

### 📐 Estrutura de Camadas

```
┌────────────────────────────────────────────────────────────────┐
│                     DRIVING (Entrada)                          │
│         Controllers HTTP, CLI, Message Consumers               │
├────────────────────────────────────────────────────────────────┤
│                     APPLICATION                                │
│     Commands │ Handlers │ Ports │ Domain Events │ Models       │
├────────────────────────────────────────────────────────────────┤
│                     DRIVEN (Saída)                             │
│         Database Adapters, External APIs, Message Publishers   │
└────────────────────────────────────────────────────────────────┘
```

### 📁 Estrutura de Pastas

```
financial-account/
├── src/main/java/br/com/mindcash/financial/
│   ├── application/
│   │   ├── commands/           # Comandos (RegisterExpense, RegisterIncome)
│   │   ├── handlers/           # Manipuladores de comandos
│   │   ├── ports/
│   │   │   ├── inbound/        # Interfaces para entrada
│   │   │   └── outbound/       # Interfaces para saída
│   │   └── domain/
│   │       ├── events/         # Eventos de domínio
│   │       └── models/         # Value Objects, Entities, Aggregates
│   ├── driving/
│   │   └── http/               # Controllers REST
│   └── driven/
│       ├── account/            # Adapter JDBC
│       │   └── statements/     # SQL Builders
│       └── revision/
│           └── v1/             # Agregados e Event Sourcing
│               ├── aggregate/
│               └── events/
├── src/main/resources/
│   ├── application.yml
│   └── db/migration/           # Scripts Flyway
└── src/test/java/              # Testes unitários
```

> 📚 Para documentação detalhada da arquitetura, consulte [ARCHITECTURE.md](./ARCHITECTURE.md)

---

## 🎯 Padrões e Princípios

### Design Patterns Aplicados

| Padrão | Onde é Usado | Propósito |
|--------|--------------|-----------|
| **Command Pattern** | `application/commands/` | Encapsula requisições como objetos |
| **Handler Pattern** | `application/handlers/` | Processa comandos de forma desacoplada |
| **Adapter Pattern** | `driven/account/Adapter.java` | Converte interfaces incompatíveis |
| **Repository Pattern** | `ports/outbound/Accounts.java` | Abstrai acesso a dados |
| **Factory Pattern** | `Statement.from()` | Cria objetos SQL de forma centralizada |
| **Outbox Pattern** | `driven/revision/` | Garante consistência eventual de eventos |
| **Aggregate Pattern** | `AggregateV1.java` | Encapsula entidades relacionadas |
| **Event Sourcing** | `driven/revision/v1/events/` | Persiste estado como sequência de eventos |

### Princípios SOLID

| Princípio | Aplicação no Projeto |
|-----------|---------------------|
| **S**ingle Responsibility | Cada classe tem uma única responsabilidade |
| **O**pen/Closed | Extensível via novas implementações de portas |
| **L**iskov Substitution | Interfaces de portas permitem substituição |
| **I**nterface Segregation | Portas inbound/outbound separadas |
| **D**ependency Inversion | Application depende de abstrações (ports) |

### Princípios DDD

- **Ubiquitous Language**: Nomenclatura consistente (Expense, Income, Account)
- **Bounded Contexts**: Módulos isolados (account, stocks, reports)
- **Value Objects**: `Amount`, `Description`, `AccountId` (imutáveis)
- **Aggregates**: `AggregateV1` controla consistência
- **Domain Events**: `ExpenseRegistered`, `IncomeRegistered`

---

## 🛠️ Tecnologias

### Core

| Tecnologia | Versão | Uso |
|------------|--------|-----|
| Java | 21 LTS | Linguagem principal |
| Spring Boot | 4.0.x | Framework web |
| Spring JDBC | - | Acesso a dados |
| Gradle | 8.x | Build e dependências |

### Banco de Dados

| Tecnologia | Versão | Uso |
|------------|--------|-----|
| MySQL | 8.0 | Banco relacional |
| Flyway | - | Migrations |

### Testes & Qualidade

| Tecnologia | Uso |
|------------|-----|
| JUnit 5 | Testes unitários |
| Mockito | Mocks |
| JaCoCo | Cobertura de código |

### DevOps

| Tecnologia | Uso |
|------------|-----|
| Docker | Containerização |
| Docker Compose | Orquestração local |
| Make | Automação de comandos |

---

## ⚙️ Pré-requisitos

- **Java 21** ou superior
- **Docker** e **Docker Compose**
- **Make** (opcional, para comandos simplificados)

---

## 🚀 Instalação e Execução

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/mind-cash-be.git
cd mind-cash-be
```

### 2. Inicie o banco de dados

```bash
docker-compose up -d
```

### 3. Execute a aplicação

**Com Make:**
```bash
make run
```

**Com Gradle:**
```bash
./gradlew :financial-account:bootRun
```

### 4. Acesse a API

A aplicação estará disponível em: `http://localhost:8080`

---

## 📚 Documentação das APIs

Documentação detalhada dos endpoints de cada módulo:

| Módulo | Documentação | Status |
|--------|--------------|--------|
| **financial-account** | [📄 API de Receitas e Despesas](./docs/api/financial-account.md) | ✅ Disponível |
| **financial-stocks** | [📄 API de Ações](./docs/api/financial-stocks.md) | 🚧 Em Construção |
| **financial-reports** | [📄 API de Relatórios](./docs/api/financial-reports.md) | 🚧 Em Construção |

### Resumo de Endpoints

#### financial-account

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/v1/expense` | Registrar nova despesa |
| `POST` | `/v1/income` | Registrar nova receita |

---

## 🧪 Testes e Cobertura

### Executar testes

**Com Make:**
```bash
make test
```

**Com Gradle:**
```bash
./gradlew :financial-account:test
```

### Ver relatório de cobertura

**Com Make:**
```bash
make report
```

O relatório HTML será gerado em:
```
financial-account/build/reports/jacoco/html/index.html
```

### Meta de Cobertura

| Métrica | Meta | Atual |
|---------|------|-------|
| Cobertura de Linhas | > 80% | 🔄 Em progresso |
| Cobertura de Branches | > 70% | 🔄 Em progresso |

---

## 🗺️ Roadmap

### ✅ Fase 1 - Core (Concluído)
- [x] Estrutura Clean Architecture
- [x] Módulo financial-account
- [x] Registro de despesas
- [x] Registro de receitas
- [x] Event Sourcing com Outbox Pattern
- [x] Agregados com snapshots
- [x] Migrations Flyway
- [x] Testes unitários com JaCoCo

### 🚧 Fase 2 - Stocks (Em Desenvolvimento)
- [ ] Módulo financial-stocks
- [ ] Cadastro de ações
- [ ] Registro de compras/vendas
- [ ] Cálculo de preço médio
- [ ] Integração com APIs de cotação

### 📋 Fase 3 - Reports (Planejado)
- [ ] Módulo financial-reports
- [ ] Dashboard de gastos por categoria
- [ ] Relatório mensal de receitas vs despesas
- [ ] Evolução patrimonial
- [ ] Exportação PDF/Excel

### 🔮 Fase 4 - Melhorias Futuras
- [ ] Autenticação JWT
- [ ] Multi-tenancy
- [ ] Cache Redis
- [ ] Message Queue (RabbitMQ/Kafka)
- [ ] Deploy em Cloud (AWS/GCP)

---

## 👨‍💻 Autor

**Diogo**

- 💼 Desenvolvedor Backend Java
- 📧 [Seu email]
- 🔗 [LinkedIn](https://linkedin.com/in/seu-perfil)
- 🐙 [GitHub](https://github.com/seu-usuario)

---

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](./LICENSE) para mais detalhes.

---

<p align="center">
  <strong>⭐ Se este projeto foi útil, considere dar uma estrela!</strong>
</p>

