# 📄 API - Financial Account

Documentação completa dos endpoints do módulo de gestão de receitas e despesas.

## Base URL

```
http://localhost:8080/v1
```

---

## 📑 Índice

- [Despesas (Expenses)](#despesas-expenses)
  - [Registrar Despesa](#registrar-despesa)
- [Receitas (Incomes)](#receitas-incomes)
  - [Registrar Receita](#registrar-receita)

---

## Despesas (Expenses)

### Registrar Despesa

Registra uma nova despesa associada a uma conta.

**Endpoint:**
```
POST /v1/expense
```

**Headers:**
```
Content-Type: application/json
```

**Request Body:**

```json
{
  "accountId": "550e8400-e29b-41d4-a716-446655440000",
  "description": "Conta de luz",
  "type": "FIXED",
  "category": {
    "description": "UTILITIES"
  },
  "status": "PENDING",
  "amount": {
    "value": 150.50,
    "currency": "BRL"
  },
  "installments": [
    {
      "installmentNumber": 1,
      "installmentAmount": 150.50,
      "dueDate": "2026-03-10"
    }
  ]
}
```

**Campos do Request:**

| Campo | Tipo | Obrigatório | Descrição |
|-------|------|-------------|-----------|
| `accountId` | `string (UUID)` | ✅ Sim | ID da conta associada |
| `description` | `string` | ✅ Sim | Descrição da despesa |
| `type` | `enum` | ✅ Sim | Tipo: `FIXED`, `VARIABLE` |
| `category` | `object` | ✅ Sim | Categoria da despesa |
| `category.description` | `string` | ✅ Sim | Nome da categoria |
| `status` | `enum` | ✅ Sim | Status: `PENDING`, `PAID`, `CANCELED` |
| `amount` | `object` | ✅ Sim | Valor da despesa |
| `amount.value` | `decimal` | ✅ Sim | Valor numérico |
| `amount.currency` | `string` | ✅ Sim | Moeda (ex: `BRL`, `USD`) |
| `installments` | `array` | ❌ Não | Lista de parcelas |
| `installments[].installmentNumber` | `integer` | ✅ Sim* | Número da parcela |
| `installments[].installmentAmount` | `decimal` | ✅ Sim* | Valor da parcela |
| `installments[].dueDate` | `date` | ✅ Sim* | Data de vencimento (YYYY-MM-DD) |

> *Obrigatório apenas se `installments` for informado

**Valores de Type:**

| Valor | Descrição |
|-------|-----------|
| `FIXED` | Despesa fixa (aluguel, assinatura) |
| `VARIABLE` | Despesa variável (alimentação, lazer) |

**Valores de Status:**

| Valor | Descrição |
|-------|-----------|
| `PENDING` | Aguardando pagamento |
| `PAID` | Pago |
| `CANCELED` | Cancelado |

**Categorias Sugeridas:**

| Categoria | Descrição |
|-----------|-----------|
| `HOUSING` | Moradia |
| `UTILITIES` | Utilidades (luz, água, gás) |
| `FOOD` | Alimentação |
| `TRANSPORTATION` | Transporte |
| `HEALTHCARE` | Saúde |
| `ENTERTAINMENT` | Entretenimento |
| `EDUCATION` | Educação |
| `OTHER` | Outros |

**Response - Sucesso (201 Created):**

```json
"Expense saved successfully"
```

**Response - Erro de Validação (400 Bad Request):**

```json
{
  "timestamp": "2026-02-12T10:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/v1/expense"
}
```

**Exemplo cURL:**

```bash
curl -X POST http://localhost:8080/v1/expense \
  -H "Content-Type: application/json" \
  -d '{
    "accountId": "550e8400-e29b-41d4-a716-446655440000",
    "description": "Conta de luz",
    "type": "FIXED",
    "category": {
      "description": "UTILITIES"
    },
    "status": "PENDING",
    "amount": {
      "value": 150.50,
      "currency": "BRL"
    },
    "installments": []
  }'
```

---

## Receitas (Incomes)

### Registrar Receita

Registra uma nova receita associada a uma conta.

**Endpoint:**
```
POST /v1/income
```

**Headers:**
```
Content-Type: application/json
```

**Request Body:**

```json
{
  "accountId": "550e8400-e29b-41d4-a716-446655440000",
  "description": "Salário mensal",
  "type": "FIXED",
  "category": {
    "description": "SALARY"
  },
  "status": "RECEIVED",
  "amount": {
    "value": 5000.00,
    "currency": "BRL"
  },
  "date": "2026-02-05",
  "installments": []
}
```

**Campos do Request:**

| Campo | Tipo | Obrigatório | Descrição |
|-------|------|-------------|-----------|
| `accountId` | `string (UUID)` | ✅ Sim | ID da conta associada |
| `description` | `string` | ✅ Sim | Descrição da receita |
| `type` | `enum` | ✅ Sim | Tipo: `FIXED`, `VARIABLE` |
| `category` | `object` | ✅ Sim | Categoria da receita |
| `category.description` | `string` | ✅ Sim | Nome da categoria |
| `status` | `enum` | ✅ Sim | Status: `EXPECTED`, `RECEIVED`, `CANCELED` |
| `amount` | `object` | ✅ Sim | Valor da receita |
| `amount.value` | `decimal` | ✅ Sim | Valor numérico |
| `amount.currency` | `string` | ✅ Sim | Moeda (ex: `BRL`, `USD`) |
| `date` | `date` | ❌ Não | Data do recebimento (YYYY-MM-DD) |
| `installments` | `array` | ❌ Não | Lista de parcelas |

**Valores de Type:**

| Valor | Descrição |
|-------|-----------|
| `FIXED` | Receita fixa (salário, aluguel recebido) |
| `VARIABLE` | Receita variável (freelance, comissão) |

**Valores de Status:**

| Valor | Descrição |
|-------|-----------|
| `EXPECTED` | Receita esperada/prevista |
| `RECEIVED` | Recebido |
| `CANCELED` | Cancelado |

**Categorias Sugeridas:**

| Categoria | Descrição |
|-----------|-----------|
| `SALARY` | Salário |
| `FREELANCE` | Trabalho freelance |
| `INVESTMENT` | Rendimentos de investimentos |
| `RENTAL` | Aluguel recebido |
| `BONUS` | Bônus |
| `GIFT` | Presente/Doação |
| `OTHER` | Outros |

**Response - Sucesso (201 Created):**

```json
"Income saved successfully"
```

**Response - Erro de Validação (400 Bad Request):**

```json
{
  "timestamp": "2026-02-12T10:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/v1/income"
}
```

**Exemplo cURL:**

```bash
curl -X POST http://localhost:8080/v1/income \
  -H "Content-Type: application/json" \
  -d '{
    "accountId": "550e8400-e29b-41d4-a716-446655440000",
    "description": "Salário mensal",
    "type": "FIXED",
    "category": {
      "description": "SALARY"
    },
    "status": "RECEIVED",
    "amount": {
      "value": 5000.00,
      "currency": "BRL"
    },
    "date": "2026-02-05",
    "installments": []
  }'
```

---

## 📊 Exemplos de Uso

### Exemplo 1: Despesa Parcelada

```json
{
  "accountId": "550e8400-e29b-41d4-a716-446655440000",
  "description": "Notebook Dell",
  "type": "VARIABLE",
  "category": {
    "description": "ELECTRONICS"
  },
  "status": "PENDING",
  "amount": {
    "value": 3600.00,
    "currency": "BRL"
  },
  "installments": [
    {
      "installmentNumber": 1,
      "installmentAmount": 300.00,
      "dueDate": "2026-03-10"
    },
    {
      "installmentNumber": 2,
      "installmentAmount": 300.00,
      "dueDate": "2026-04-10"
    },
    {
      "installmentNumber": 3,
      "installmentAmount": 300.00,
      "dueDate": "2026-05-10"
    }
  ]
}
```

### Exemplo 2: Receita de Freelance

```json
{
  "accountId": "550e8400-e29b-41d4-a716-446655440000",
  "description": "Projeto de desenvolvimento web",
  "type": "VARIABLE",
  "category": {
    "description": "FREELANCE"
  },
  "status": "RECEIVED",
  "amount": {
    "value": 2500.00,
    "currency": "BRL"
  },
  "date": "2026-02-10",
  "installments": []
}
```

### Exemplo 3: Despesa em Dólar

```json
{
  "accountId": "550e8400-e29b-41d4-a716-446655440000",
  "description": "Assinatura Adobe Creative Cloud",
  "type": "FIXED",
  "category": {
    "description": "SOFTWARE"
  },
  "status": "PAID",
  "amount": {
    "value": 54.99,
    "currency": "USD"
  },
  "installments": []
}
```

---

## 🔗 Links Úteis

- [Voltar ao README principal](../../README.md)
- [Documentação de Arquitetura](../../ARCHITECTURE.md)
- [API de Ações (Em Construção)](./financial-stocks.md)
- [API de Relatórios (Em Construção)](./financial-reports.md)

