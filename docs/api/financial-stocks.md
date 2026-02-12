# 📄 API - Financial Stocks

> 🚧 **MÓDULO EM CONSTRUÇÃO** 🚧

Documentação dos endpoints do módulo de gestão de carteira de ações e investimentos.

## Status do Desenvolvimento

| Feature | Status | Previsão |
|---------|--------|----------|
| Cadastro de Ações | 🚧 Planejado | Q2 2026 |
| Registro de Compras | 🚧 Planejado | Q2 2026 |
| Registro de Vendas | 🚧 Planejado | Q2 2026 |
| Cálculo de Preço Médio | 🚧 Planejado | Q2 2026 |
| Integração com APIs de Cotação | 🚧 Planejado | Q3 2026 |

---

## Base URL (Planejado)

```
http://localhost:8080/v1/stocks
```

---

## 📑 Endpoints Planejados

### Ações

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `POST` | `/v1/stocks` | Cadastrar nova ação na carteira | 🚧 Planejado |
| `GET` | `/v1/stocks` | Listar todas as ações | 🚧 Planejado |
| `GET` | `/v1/stocks/{ticker}` | Buscar ação por ticker | 🚧 Planejado |
| `DELETE` | `/v1/stocks/{ticker}` | Remover ação da carteira | 🚧 Planejado |

### Operações

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `POST` | `/v1/stocks/operations/buy` | Registrar compra de ações | 🚧 Planejado |
| `POST` | `/v1/stocks/operations/sell` | Registrar venda de ações | 🚧 Planejado |
| `GET` | `/v1/stocks/operations` | Listar histórico de operações | 🚧 Planejado |

### Cotações

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `GET` | `/v1/stocks/{ticker}/quote` | Obter cotação atual | 🚧 Planejado |
| `GET` | `/v1/stocks/{ticker}/history` | Histórico de cotações | 🚧 Planejado |

---

## 📋 Modelos de Request (Preview)

### Cadastrar Ação

```json
{
  "accountId": "550e8400-e29b-41d4-a716-446655440000",
  "ticker": "PETR4",
  "name": "Petrobras PN",
  "sector": "ENERGY",
  "type": "STOCK"
}
```

### Registrar Compra

```json
{
  "accountId": "550e8400-e29b-41d4-a716-446655440000",
  "ticker": "PETR4",
  "quantity": 100,
  "price": {
    "value": 35.50,
    "currency": "BRL"
  },
  "operationDate": "2026-02-10",
  "broker": "XP Investimentos",
  "fees": {
    "value": 0.00,
    "currency": "BRL"
  }
}
```

### Registrar Venda

```json
{
  "accountId": "550e8400-e29b-41d4-a716-446655440000",
  "ticker": "PETR4",
  "quantity": 50,
  "price": {
    "value": 38.00,
    "currency": "BRL"
  },
  "operationDate": "2026-03-15",
  "broker": "XP Investimentos",
  "fees": {
    "value": 0.00,
    "currency": "BRL"
  }
}
```

---

## 📊 Funcionalidades Planejadas

### Cálculo de Preço Médio

O sistema calculará automaticamente o preço médio ponderado das ações:

```
Preço Médio = (Σ quantidade × preço) / Σ quantidade
```

### Tipos de Ativos Suportados

| Tipo | Descrição |
|------|-----------|
| `STOCK` | Ações ordinárias e preferenciais |
| `FII` | Fundos Imobiliários |
| `ETF` | Exchange Traded Funds |
| `BDR` | Brazilian Depositary Receipts |

### Setores

| Setor | Descrição |
|-------|-----------|
| `ENERGY` | Energia |
| `FINANCIAL` | Financeiro |
| `TECHNOLOGY` | Tecnologia |
| `HEALTHCARE` | Saúde |
| `CONSUMER` | Consumo |
| `UTILITIES` | Utilidades |
| `REAL_ESTATE` | Imobiliário |
| `INDUSTRIAL` | Industrial |

---

## 🔗 Links Úteis

- [Voltar ao README principal](../../README.md)
- [Documentação de Arquitetura](../../ARCHITECTURE.md)
- [API de Receitas e Despesas](./financial-account.md)
- [API de Relatórios (Em Construção)](./financial-reports.md)

---

> 💡 **Contribua!** Este módulo está em desenvolvimento. Sugestões são bem-vindas!

