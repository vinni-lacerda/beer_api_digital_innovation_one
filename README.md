# 🍺 Beerstock API — Controle de Estoque de Cervejas

Projeto desenvolvido como parte do **DIO - Santander Bootcamp Java**, focado em **Spring Boot**, **JPA/Hibernate**, **TDD** e **Testes Unitários com JUnit 5 + Mockito**.

---

## 📌 Sobre o Projeto

A **Beerstock API** é uma aplicação REST para gerenciar o estoque de cervejas.
Este projeto demonstra:

* Desenvolvimento de API REST com **Spring Boot**
* Persistência de dados com **JPA/Hibernate**
* Mapeamento DTO ↔ Entity com **MapStruct**
* Testes unitários com **JUnit 5**
* Testes de serviços usando **Mockito**
* Validações e exceções customizadas
* Conceitos práticos de **TDD (Test-Driven Development)**

---

## 🛠 Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA**
* **H2 Database**
* **MapStruct**
* **JUnit 5**
* **Mockito**
* **Hamcrest**

---

## 📁 Estrutura da API

A API permite operações de CRUD e manipulação de estoque:

### Endpoints Principais

| Método   | Rota                           | Descrição                |
| -------- | ------------------------------ | ------------------------ |
| `POST`   | `/api/v1/beers`                | Cadastrar nova cerveja   |
| `GET`    | `/api/v1/beers/{name}`         | Buscar cerveja pelo nome |
| `GET`    | `/api/v1/beers`                | Listar todas             |
| `DELETE` | `/api/v1/beers/{id}`           | Deletar cerveja          |
| `PATCH`  | `/api/v1/beers/{id}/increment` | Incrementar estoque      |
| `PATCH`  | `/api/v1/beers/{id}/decrement` | Reduzir estoque          |

---

## 🧪 Testes Unitários

O projeto implementa testes completos na camada **Service**, cobrindo:

### ✔ Criação de cerveja

Teste valida:

* Salvamento
* Conversão DTO ↔ Entity
* Verificação de duplicidade

### ✔ Busca por nome (sucesso e falha)

### ✔ Listagem (lista cheia e vazia)

### ✔ Exclusão por ID

### ✔ Incremento de estoque

* Dentro do limite
* Acima do limite → dispara exceção

### ✔ (Opcional) Decremento de estoque

* Dentro do limite
* Abaixo de zero → exceção

Os testes utilizam:

* Mockito (`when`, `thenReturn`, `verify`)
* Assertions do Hamcrest
* `assertThrows` do JUnit

---

## ▶️ Como Executar

### 1. Clonar o repositório

```bash
git clone https://github.com/[SEU_USUARIO]/beerstock.git
cd beerstock
```

### 2. Rodar o projeto

```bash
mvn spring-boot:run
```

### 3. Acessar o H2 Console

```
http://localhost:8080/h2-console
```

---

## 🧪 Rodando os Testes

Para executar todos os testes unitários:

```bash
mvn test
```

---

## 📄 Estrutura do Projeto

```
src
├── main
│   ├── java/one.digitalinnovation.beerstock
│   │   ├── controller
│   │   ├── dto
│   │   ├── entity
│   │   ├── service
│   │   ├── repository
│   │   └── mapper
│   └── resources
│       └── application.properties
└── test
    └── java/one.digitalinnovation.beerstock/service
        └── BeerServiceTest.java
```

---

## 🏁 Conclusão

Este projeto demonstra o uso prático de **Spring Boot**, **JPA**, **Mockito** e **JUnit**, reforçando conceitos essenciais de desenvolvimento backend moderno com **Java**.

Sinta-se à vontade para contribuir, melhorar ou adaptar o projeto para estudos!

---

## 👤 Autor

Feito por **Vinicius Lacerda** — focado em backend Java e sempre estudando boas práticas, testes e arquitetura.
