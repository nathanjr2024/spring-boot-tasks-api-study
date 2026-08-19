# API de Gerenciamento de Tarefas

API REST simples para gerenciar tarefas, construída com Spring Boot 4.1.0 e Java 17.

## Tecnologias

- Java 17
- Spring Boot 4.1.0
- Maven (Wrapper 3.9.16)

## Como executar

### Pré-requisitos

- JDK 17 instalado
- Maven (ou use o wrapper incluído)

### Subindo a aplicação

```bash
# Com Maven Wrapper (Windows)
.\mvnw.cmd spring-boot:run

# Com Maven Wrapper (Linux/macOS)
./mvnw spring-boot:run

# Com Maven instalado globalmente
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

## Endpoints

### `GET /tasks`

Retorna todas as tarefas cadastradas.

**Resposta:** `200 OK` — array JSON de strings

```json
["Estudar Spring Boot", "Fazer exercícios", "Ler documentação"]
```

---

### `POST /tasks`

Adiciona uma nova tarefa.

**Body:** texto puro (plain text)

**Resposta:** `200 OK`

---

### `DELETE /tasks/{task}`

Remove a primeira ocorrência da tarefa com o valor informado no path.

**Parâmetros:**
- `{task}` — nome exato da tarefa (URL-encoded se contiver espaços ou caracteres especiais)

**Resposta:** `200 OK`

## Observações

- Os dados são armazenados **em memória** — todas as tarefas são perdidas ao reiniciar a aplicação.
- Não há persistência em banco de dados.
- A API **não é thread-safe**.

## Testes

Para rodar os testes existentes:

```bash
.\mvnw.cmd test       # Windows
./mvnw test           # Linux/macOS
```

Consulte [TESTING.md](TESTING.md) para o guia completo de como testar cada endpoint manualmente.
