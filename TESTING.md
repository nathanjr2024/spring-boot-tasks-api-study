# Guia de Testes — API de Tarefas

Base URL: `http://localhost:8080`

Certifique-se de que a aplicação está rodando antes de executar os testes abaixo.

---

## Ferramentas

Os exemplos usam `curl`. Você também pode usar:
- [Postman](https://www.postman.com/)
- [Insomnia](https://insomnia.rest/)
- [Bruno](https://www.usebruno.com/)
- A aba **HTTP Client** do IntelliJ IDEA

---

## 1. Listar tarefas — `GET /tasks`

### Caso: lista vazia (estado inicial)

```bash
curl -i http://localhost:8080/tasks
```

**Resposta esperada:**

```
HTTP/1.1 200 OK
Content-Type: application/json

[]
```

---

## 2. Adicionar tarefa — `POST /tasks`

### Caso: adicionar uma tarefa

```bash
curl -i -X POST http://localhost:8080/tasks \
  -H "Content-Type: text/plain" \
  -d "Estudar Spring Boot"
```

**Resposta esperada:**

```
HTTP/1.1 200 OK
```

### Caso: adicionar múltiplas tarefas

```bash
curl -i -X POST http://localhost:8080/tasks \
  -H "Content-Type: text/plain" \
  -d "Fazer exercícios"

curl -i -X POST http://localhost:8080/tasks \
  -H "Content-Type: text/plain" \
  -d "Ler documentação"
```

### Verificar resultado após inserções

```bash
curl http://localhost:8080/tasks
```

**Resposta esperada:**

```json
["Estudar Spring Boot","Fazer exercícios","Ler documentação"]
```

---

## 3. Deletar tarefa — `DELETE /tasks/{task}`

### Caso: deletar uma tarefa existente

Supondo que "Fazer exercícios" está na lista:

```bash
curl -i -X DELETE "http://localhost:8080/tasks/Fazer%20exerc%C3%ADcios"
```

> Espaços viram `%20` e acentos precisam de encoding. Use uma ferramenta como o Postman para codificar automaticamente.

**Resposta esperada:**

```
HTTP/1.1 200 OK
```

Verificar que foi removida:

```bash
curl http://localhost:8080/tasks
```

```json
["Estudar Spring Boot","Ler documentação"]
```

### Caso: tarefa não existe na lista

O endpoint retorna `200 OK` mesmo se a tarefa não for encontrada (comportamento atual do `List.remove()`).

```bash
curl -i -X DELETE "http://localhost:8080/tasks/tarefa-inexistente"
```

**Resposta esperada:**

```
HTTP/1.1 200 OK
```

### Caso: tarefas duplicadas — remove somente a primeira

Se houver duas entradas iguais, apenas a primeira é removida:

```bash
# Adicionar duas vezes
curl -X POST http://localhost:8080/tasks -H "Content-Type: text/plain" -d "Duplicada"
curl -X POST http://localhost:8080/tasks -H "Content-Type: text/plain" -d "Duplicada"

# Deletar
curl -X DELETE "http://localhost:8080/tasks/Duplicada"

# Verificar — ainda deve haver uma "Duplicada"
curl http://localhost:8080/tasks
```

---

## 4. Fluxo completo (end-to-end)

```bash
# 1. Confirmar que está vazio
curl http://localhost:8080/tasks

# 2. Adicionar tarefas
curl -X POST http://localhost:8080/tasks -H "Content-Type: text/plain" -d "Tarefa A"
curl -X POST http://localhost:8080/tasks -H "Content-Type: text/plain" -d "Tarefa B"
curl -X POST http://localhost:8080/tasks -H "Content-Type: text/plain" -d "Tarefa C"

# 3. Listar
curl http://localhost:8080/tasks

# 4. Deletar "Tarefa B"
curl -X DELETE "http://localhost:8080/tasks/Tarefa%20B"

# 5. Confirmar remoção
curl http://localhost:8080/tasks
```

**Resultado esperado no passo 5:**

```json
["Tarefa A","Tarefa C"]
```

---

## 5. Rodando os testes automatizados

```bash
# Windows
.\mvnw.cmd test

# Linux/macOS
./mvnw test
```

Atualmente existe apenas um smoke test (`contextLoads`) que verifica se o contexto do Spring sobe corretamente.

---

## Limitações conhecidas

| Limitação | Detalhe |
|-----------|---------|
| Sem persistência | Dados perdidos ao reiniciar |
| Sem thread safety | Não use em produção com requisições concorrentes |
| DELETE por valor | Tarefas com o mesmo nome são indistinguíveis |
| Encoding obrigatório | Nomes com espaços/acentos precisam de URL encoding no path |
