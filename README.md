# **Challenge Hyperativa Application**
Bem-vindo ao **Challenge Hyperativa Application**, um projeto desenvolvido para atender às necessidades de gerenciamento de cartões de forma segura e eficiente.
## **Descrição**
A aplicação oferece uma API RESTful que permite operações relacionadas ao gerenciamento de cartões, incluindo:
- Adicionar um novo cartão.
- Listar cartões existentes.
- Buscar detalhes de um cartão.
- Atualizar informações de um cartão.
- Deletar cartões.

Essa aplicação foi desenvolvida utilizando o **Spring Boot** com suporte a banco de dados, segurança com autenticação via tokens (JWT), e integração com Testcontainers para testes.
## **Pré-requisitos**
Antes de executar o projeto, certifique-se de que você possui os seguintes requisitos instalados em sua máquina:
- **Java 17** ou superior
- **Maven** 3.6+ (ou outra ferramenta de dependências configurada)
- **Docker** (para usar Testcontainers)
- Postman (opcional) para testar os endpoints

## **Tecnologias Utilizadas**
- **Java 17**: Linguagem de programação principal.
- **Spring Boot**: Framework responsável por simplificar o desenvolvimento de aplicações Java.
    - Módulos utilizados: `Spring Web`, `Spring Data JPA`, `Spring Security`.

- **Hibernate**: Implementação de JPA para o gerenciamento do banco de dados.
- **JWT**: Autenticação segura baseada em token.
- **MySQL**: Banco de dados utilizado para persistência.
- **Testcontainers**: Para isolamento de testes usando o banco em contêiner.
- **JUnit 5**: Framework de testes.

## **Instalação**
### Passos para configurar e executar a aplicação localmente:
1. Clone o repositório:
``` bash
   git clone https://github.com/sua-empresa/challenge-hyperativa.git
   cd challenge-hyperativa
```
1. Certifique-se de que as dependências estão instaladas e compiladas:
``` bash
   mvn clean install
```
1. Configure o banco de dados:
    - A aplicação está configurada para usar o **MySQL**.
    - Certifique-se de que o serviço MySQL está disponível e as credenciais (username/password) estão corretas.

2. Configure as propriedades em `application.properties`: No arquivo `src/main/resources/application.properties`, defina as propriedades para o banco de dados:
``` properties
   spring.datasource.url=jdbc:mysql://localhost:3306/hyperativa_db
   spring.datasource.username=root
   spring.datasource.password=senha_secreta
   spring.jpa.hibernate.ddl-auto=update
```
1. Execute a aplicação:
``` bash
   mvn spring-boot:run
```
## **Testes**
Para executar os testes (incluindo os que utilizam Testcontainers):
``` bash
mvn test
```
Certifique-se de que você está com o **Docker** em execução antes de executar os testes, pois o `Testcontainers` instanciará automaticamente um contêiner com o banco de dados.
## **Endpoints da API**
Abaixo estão os principais endpoints disponíveis:
### **1. Adicionar um Cartão**
- **URL:** `POST /api/cards`
- **Headers:**
    - `Authorization: Bearer <seu_token_aqui>`
    - `Content-Type: application/json`

- **Body:**
``` json
  {
    "cardNumber": "4111111111111111",
    "cardHolderName": "John Doe",
    "cvv": "123",
    "expirationDate": "12/2024"
  }
```
### **2. Listar Cartões**
- **URL:** `GET /api/cards`
- **Headers:**
    - `Authorization: Bearer <seu_token_aqui>`

### **3. Buscar Detalhes de um Cartão**
- **URL:** `GET /api/cards/{id}`
- **Headers:**
    - `Authorization: Bearer <seu_token_aqui>`

### **4. Atualizar Cartão**
- **URL:** `PUT /api/cards/{id}`
- **Headers:**
    - `Authorization: Bearer <seu_token_aqui>`
    - `Content-Type: application/json`

- **Body:**
``` json
  {
    "cardHolderName": "Jane Doe",
    "expirationDate": "01/2025"
  }
```
### **5. Deletar Cartão**
- **URL:** `DELETE /api/cards/{id}`
- **Headers:**
    - `Authorization: Bearer <seu_token_aqui>`

## **Autenticação**
A aplicação utiliza autenticação baseada em **JWT**. Para obter o token de acesso, use o seguinte endpoint:
- **URL:** `POST /login`
- **Headers:**
    - `Content-Type: application/json`

- **Body:**
``` json
  {
    "username": "testUser",
    "password": "testPassword"
  }
```
O servidor retornará um `token` no formato:
``` json
{
   "token": "eyJhbGciOiJIUzUxMiJ9.sample-token"
}
```
Utilize este token em todas as requisições protegidas, adicionando o cabeçalho:
``` http
Authorization: Bearer <token>
```
## **Estrutura do Projeto**
O projeto segue a estrutura padrão do Spring Boot:
``` plaintext
.
├── src/main/java/com/hyperativa/challenge/
│   ├── controller/          # Controladores (endpoints)
│   ├── model/               # Modelos de domínio
│   ├── repository/          # Repositórios para acesso ao banco
│   ├── service/             # Serviços e regras de negócio
│   └── ChallengeHyperativaApplication.java # Classe principal
├── src/main/resources/
│   ├── application.properties # Configurações da aplicação
└── src/test/                 # Testes
```
