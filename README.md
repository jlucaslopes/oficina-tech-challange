# 🧰 API de Gerenciamento de Oficina — Fase de Deploy e Infraestrutura

API REST desenvolvida em **Java 21** com **Spring Boot**, projetada para o gerenciamento de operações de uma oficina mecânica.  
Nesta fase, o foco foi a **refatoração completa do código**, aplicação de **Clean Code** e implementação de uma **arquitetura de deploy automatizada** utilizando **Kubernetes**, **Terraform** e **GitHub Actions**.

---

## 🎯 Objetivos desta Fase

- Refatorar o código para seguir os princípios de **Clean Code** e boas práticas de arquitetura.
- Criar uma estrutura completa para **deploy automatizado** e **infraestrutura como código**.
- Implementar **pipelines CI/CD** e **ambiente orquestrado com Kubernetes**.
- Prover um ambiente escalável, seguro e reprodutível em qualquer máquina.

---

## 🧩 Arquitetura da Solução

### 🏗️ Componentes da Aplicação
- **Spring Boot (Java 21)** — API REST principal.
- **Spring Data JPA + Hibernate** — Persistência de dados.
- **Spring Security + JWT** — Autenticação e autorização.
- **MySQL** — Banco de dados relacional.
- **Docker** — Empacotamento da aplicação.
- **Kubernetes (Minikube)** — Orquestração de containers.
- **Terraform** — Provisionamento da infraestrutura.
- **GitHub Actions** — CI/CD automatizado.

---

### ☁️ Infraestrutura Provisionada

A infraestrutura é definida como código na pasta `infra/terraform/`, e contempla:

- **Cluster Kubernetes** (Minikube local ou cloud provider)
- **Namespace dedicado à aplicação**
- **Secrets**, **ConfigMaps**, **Volumes Persistentes** e **Services**
- **LoadBalancer** para exposição pública da API
- **Banco de Dados MySQL** provisionado no cluster

---

### 🔄 Fluxo de Deploy Automatizado

1. **Push ou PR** para o repositório GitHub.
2. **GitHub Actions** é acionado:
    - Faz build da imagem Docker.
    - Envia a imagem para o repositório Docker Hub (`jlucaslopes/oficina`).
    - Instala e inicializa o **Minikube**.
    - Executa o **Terraform Apply** para provisionar a infraestrutura.
    - Aplica os manifests do **Kubernetes** (pasta `infra/k8s`).
3. A aplicação é implantada automaticamente no cluster.

---

## ⚙️ Instruções de Execução

### 🧪 Execução Local

#### Pré-requisitos
- **Java 21**
- **Maven**
- **Docker (opcional)**

#### Passos
```bash
mvn spring-boot:run
```
A aplicação estará disponível em:
```
http://localhost:8080
```

---

### ☸️ Deploy em Kubernetes

#### 1️⃣ Subir o cluster (Minikube)
```bash
minikube start
```

#### 2️⃣ Aplicar os manifests
```bash
kubectl apply -f infra/k8s/
```

#### 3️⃣ Verificar os pods
```bash
kubectl get pods
```

#### 4️⃣ Expor o serviço
```bash
minikube service oficina-service
```

---

### 🌍 Provisionamento da Infraestrutura com Terraform

Na pasta `infra/terraform`, execute:

```bash
terraform init
terraform plan
terraform apply -auto-approve
```

Isso criará todos os recursos necessários (rede, cluster, storage, etc).

---

## 🧱 Estrutura do Projeto

```
/
├── src/                     # Código-fonte Java (Spring Boot)
├── infra/
│   ├── k8s/                  # Manifests do Kubernetes
│   └── terraform/            # Infraestrutura como código
├── .github/workflows/        # Pipelines de CI/CD
├── pom.xml                   # Dependências Maven
└── README.md                 # Este arquivo
```

---

## 🧠 Considerações Finais

Esta versão do projeto representa uma **base sólida de arquitetura moderna**, integrando:
- **Clean Code e boas práticas de engenharia**
- **Infraestrutura como código**
- **Automação de deploy e observabilidade**

---
