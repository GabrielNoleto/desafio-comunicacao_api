# 🚀 Desafio Técnico: API de Agendamento de Comunicação

Este projeto é uma solução completa para o gerenciamento de agendamentos de comunicação, estruturado para operar em ambiente de microsserviços. A aplicação engloba desde a recepção de dados via API até o processamento automático e integração com serviços externos.

## ✅ Requisitos Implementados

Conforme as especificações do desafio, foram entregues as seguintes funcionalidades core:

### 1. Agendamento de Comunicação (`POST /comunicacao/agendar`)
* **Responsabilidade**: Cadastro e validação de novas solicitações.
* **Diferencial**: Validação de integridade de dados e persistência com status inicial pendente.

### 2. Consulta de Status (`GET /comunicacao`)
* **Responsabilidade**: Recuperação de registros via e-mail do destinatário.
* **Diferencial**: Tratamento de exceções para registros inexistentes com retorno `404 Not Found`.

### 3. Cancelamento de Agendamento (`PATCH /comunicacao/cancelar`)
* **Responsabilidade**: Atualização parcial do status para "CANCELADO".
* **Diferencial**: Conformidade com os padrões REST para modificações parciais.

---

## 🛠️ Tecnologias e Arquitetura

Para garantir uma solução escalável e profissional, foram integradas as seguintes tecnologias:

* **Docker**: Conteinerização da aplicação para garantir paridade entre os ambientes de desenvolvimento e produção.
* **Swagger (OpenAPI)**: Documentação interativa da API, permitindo testes rápidos dos endpoints e facilitando o consumo por outros desenvolvedores.
* **FeignClient**: Implementação de cliente HTTP declarativo para integração eficiente com outros microsserviços e APIs externas.
* **Task Scheduling (Cron)**: Implementação de rotinas agendadas para o processamento automático e envio das comunicações pendentes.
* **Global Exception Handler**: Centralização do tratamento de erros via `@RestControllerAdvice` para respostas HTTP padronizadas.

---

## 🧪 Estratégia de Qualidade

A robustez da aplicação é garantida por uma suíte de testes rigorosa:
* **Testes de Integração (MockMvc)**: Validação do fluxo completo dos endpoints e contratos de API.
* **Testes Unitários (Mockito)**: Isolamento das regras de negócio na camada de serviço.
* **Data Factories**: Padronização da massa de dados de teste para maior manutenibilidade.

---

## ⚙️ Execução do Projeto

### Via Docker
Para subir a aplicação e suas dependências:
```bash
docker-compose up --build
