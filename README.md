# 🛠️ API de Gerenciamento de Oficina

API REST desenvolvida em **Java 21** com **Spring Boot**, utilizando **Maven** como gerenciador de dependências.
O sistema gerencia operações de uma oficina mecânica, com autenticação via **JWT**.

---

## 📌 Funcionalidades

- Autenticação com **JWT**
- Endpoint `/login` para geração de token
- Endpoints protegidos por autenticação
- Docker Compose para subir a aplicação facilmente
- Export de **collection Insomnia** para testes

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Maven**
- **Docker & Docker Compose**
- **Insomnia** (para testes de API)

---

## 📂 Estrutura do Projeto

```

/
├── docker-compose.yml                  # Arquivo para subir aplicação
├── pom.xml                             # Dependências Maven
├── src/                                # Código-fonte
├── insomnia\Oficina-collection.json    # Collection de testes no Insomnia
└── README.md                           # Este arquivo

````

---

## ⚙️ Como Rodar o Projeto

### 1️⃣ Pré-requisitos
- **Java 21** instalado
- **Maven** instalado
- **Docker** e **Docker Compose** instalados

### 2️⃣ Rodando com Docker Compose
Na raiz do projeto, execute:

```bash
docker-compose up --build
````

A aplicação ficará disponível em:

```
http://localhost:8080
```

### 3️⃣ Rodando localmente (sem Docker)

Na raiz do projeto, execute:

```bash
mvn spring-boot:run
```

---

## 🔑 Autenticação

A API utiliza autenticação JWT.
Para acessar os endpoints protegidos, siga os passos:

### 1️⃣ Gerar token

Faça um **POST** para:

```
POST /login
Content-Type: application/json
```

**Body:**

```json
{
  "username": "fiap",
  "password": "1234"
}
```

**Resposta esperada:**

```json
{
  "token": "seu-token-jwt-aqui"
}
```

---

### 2️⃣ Usar o token

Em qualquer requisição para endpoints protegidos, envie o header:

```
Authorization: Bearer seu-token-jwt-aqui
```

---

## 🧪 Testando com Insomnia

O projeto contém um arquivo `Oficina-collection.json` exportado com todos os endpoints para facilitar os testes.

Para usar:

1. Abra o **Insomnia**
2. Vá em **Application → Import/Export → Import Data**
3. Selecione o arquivo `Oficina-collection.json` na raiz do projeto
4. Teste os endpoints (já configurados com o JWT)

---

## 📜 Licença

Este projeto é de uso livre para fins de estudo e demonstração.

---
