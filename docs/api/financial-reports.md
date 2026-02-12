# 📄 API - Financial Reports

> 🚧 **MÓDULO EM CONSTRUÇÃO** 🚧

Documentação dos endpoints do módulo de relatórios e dashboards analíticos.

## Status do Desenvolvimento

| Feature | Status | Previsão |
|---------|--------|----------|
| Dashboard de Gastos | 🚧 Planejado | Q3 2026 |
| Relatório Mensal | 🚧 Planejado | Q3 2026 |
| Evolução Patrimonial | 🚧 Planejado | Q3 2026 |
| Exportação PDF/Excel | 🚧 Planejado | Q4 2026 |

---

## Base URL (Planejado)

```
http://localhost:8080/v1/reports
```

---

## 📑 Endpoints Planejados

### Dashboard

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `GET` | `/v1/reports/dashboard` | Resumo geral da conta | 🚧 Planejado |
| `GET` | `/v1/reports/dashboard/expenses` | Despesas por categoria | 🚧 Planejado |
| `GET` | `/v1/reports/dashboard/incomes` | Receitas por categoria | 🚧 Planejado |
| `GET` | `/v1/reports/dashboard/balance` | Saldo atual e evolução | 🚧 Planejado |

### Relatórios Mensais

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `GET` | `/v1/reports/monthly/{year}/{month}` | Relatório do mês | 🚧 Planejado |
| `GET` | `/v1/reports/monthly/comparison` | Comparativo mensal | 🚧 Planejado |
| `GET` | `/v1/reports/yearly/{year}` | Relatório anual | 🚧 Planejado |

### Exportação

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `GET` | `/v1/reports/export/pdf` | Exportar para PDF | 🚧 Planejado |
| `GET` | `/v1/reports/export/excel` | Exportar para Excel | 🚧 Planejado |
| `GET` | `/v1/reports/export/csv` | Exportar para CSV | 🚧 Planejado |

### Análises

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `GET` | `/v1/reports/analysis/trends` | Tendências de gastos | 🚧 Planejado |
| `GET` | `/v1/reports/analysis/forecast` | Previsão financeira | 🚧 Planejado |

---

## 📋 Modelos de Response (Preview)

### Dashboard Resumo

```json
{
  "accountId": "550e8400-e29b-41d4-a716-446655440000",
  "period": {
    "start": "2026-02-01",
    "end": "2026-02-28"
  },
  "summary": {
    "totalIncome": {
      "value": 8500.00,
      "currency": "BRL"
    },
    "totalExpense": {
      "value": 5200.00,
      "currency": "BRL"
    },
    "balance": {
      "value": 3300.00,
      "currency": "BRL"
    },
    "savingsRate": 38.82
  }
}
```

### Despesas por Categoria

```json
{
  "accountId": "550e8400-e29b-41d4-a716-446655440000",
  "period": {
    "start": "2026-02-01",
    "end": "2026-02-28"
  },
  "categories": [
    {
      "category": "HOUSING",
      "total": {
        "value": 1800.00,
        "currency": "BRL"
      },
      "percentage": 34.62,
      "transactions": 2
    },
    {
      "category": "FOOD",
      "total": {
        "value": 1200.00,
        "currency": "BRL"
      },
      "percentage": 23.08,
      "transactions": 15
    },
    {
      "category": "TRANSPORTATION",
      "total": {
        "value": 800.00,
        "currency": "BRL"
      },
      "percentage": 15.38,
      "transactions": 8
    },
    {
      "category": "UTILITIES",
      "total": {
        "value": 450.00,
        "currency": "BRL"
      },
      "percentage": 8.65,
      "transactions": 3
    },
    {
      "category": "OTHER",
      "total": {
        "value": 950.00,
        "currency": "BRL"
      },
      "percentage": 18.27,
      "transactions": 10
    }
  ],
  "total": {
    "value": 5200.00,
    "currency": "BRL"
  }
}
```

### Comparativo Mensal

```json
{
  "accountId": "550e8400-e29b-41d4-a716-446655440000",
  "comparison": [
    {
      "month": "2026-01",
      "income": 8000.00,
      "expense": 4800.00,
      "balance": 3200.00
    },
    {
      "month": "2026-02",
      "income": 8500.00,
      "expense": 5200.00,
      "balance": 3300.00
    }
  ],
  "variation": {
    "income": {
      "absolute": 500.00,
      "percentage": 6.25
    },
    "expense": {
      "absolute": 400.00,
      "percentage": 8.33
    },
    "balance": {
      "absolute": 100.00,
      "percentage": 3.13
    }
  }
}
```

### Evolução Patrimonial

```json
{
  "accountId": "550e8400-e29b-41d4-a716-446655440000",
  "evolution": [
    {
      "date": "2026-01-31",
      "balance": 10000.00,
      "stocks": 5000.00,
      "total": 15000.00
    },
    {
      "date": "2026-02-28",
      "balance": 13300.00,
      "stocks": 5500.00,
      "total": 18800.00
    }
  ],
  "growth": {
    "absolute": 3800.00,
    "percentage": 25.33
  }
}
```

---

## 📊 Funcionalidades Planejadas

### Tipos de Gráficos

| Tipo | Uso |
|------|-----|
| **Pizza** | Distribuição por categoria |
| **Barras** | Comparativo mensal |
| **Linha** | Evolução temporal |
| **Área** | Composição patrimonial |

### Filtros Disponíveis

| Filtro | Descrição |
|--------|-----------|
| `startDate` | Data inicial do período |
| `endDate` | Data final do período |
| `category` | Filtrar por categoria |
| `type` | Tipo (FIXED/VARIABLE) |
| `status` | Status da transação |

### Formatos de Exportação

| Formato | Content-Type | Extensão |
|---------|--------------|----------|
| PDF | `application/pdf` | `.pdf` |
| Excel | `application/vnd.openxmlformats-officedocument.spreadsheetml.sheet` | `.xlsx` |
| CSV | `text/csv` | `.csv` |

---

## 🔗 Links Úteis

- [Voltar ao README principal](../../README.md)
- [Documentação de Arquitetura](../../ARCHITECTURE.md)
- [API de Receitas e Despesas](./financial-account.md)
- [API de Ações (Em Construção)](./financial-stocks.md)

---

> 💡 **Contribua!** Este módulo está em desenvolvimento. Sugestões são bem-vindas!

