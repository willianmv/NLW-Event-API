# NLW Events - Spring Boot API REST

## Descrição

O **NLW Events** é uma **API REST** desenvolvida com **Spring Boot** para o gerenciamento de eventos e inscrições. A API permite que os usuários criem eventos, inscrevam-se e verifiquem rankings de inscrições com base em um sistema de indicações de usuários.
Esse projeto foi uma prática aplicada durante o evento Next Level Week da RocketSeat em fevereiro de 2024.

## Funcionalidades

- **Gestão de Eventos**: Criação e listagem de eventos.
- **Inscrições**: Inscrição em eventos com possibilidade de indicação de outros usuários.
- **Ranking**: Exibição de ranking por evento e por usuário com base em suas inscrições e indicações.

## Tecnologias Utilizadas

- **Spring Boot**: Framework principal para construção da aplicação.
- **Spring Data JPA**: Para interação com o banco de dados.
- **Spring Web**: Para expor a API REST.
- **Spring Validation**: Para validação de dados de entrada.
- **Banco de Dados Relacional (MySQL)**: Banco de dados utilizado para armazenar eventos, inscrições e usuários.

### Funcionalidades

#### 1. **Eventos**

- **POST /events**: Criação de um novo evento.
  - **Body**: 
    ```json
    {
      "title": "Evento Exemplo"
    }
    ```
  - **Resposta**: 
    ```json
    {
      "id": 1,
      "title": "Evento Exemplo",
      "prettyName": "evento-exemplo"
    }
    ```

- **GET /events**: Lista todos os eventos.
- - **Resposta**: 
    ```json
    [
      {
          "eventId": 5,
          "title": "CodeCraft Summit 2025",
          "prettyName": "codecraft-summit-2025",
          "location": "Online",
          "price": 0.0,
          "startDate": "2025-03-16",
          "endDate": "2025-03-18",
      },
      {
          "eventId": 6,
          "title": "Imersão Java",
          "prettyName": "imersão-java",
          "location": "Online",
          "price": 0.0,
          "startDate": "2025-06-01",
          "endDate": "2025-06-03",
      },
      {
          "eventId": 9,
          "title": "Imersão Angular Inverno 2025",
          "prettyName": "imersão-angular-inverno-2025",
          "location": "Online",
          "price": 0.0,
          "startDate": "2025-07-01",
          "endDate": "2025-08-03",
      }
    ]
    ```

- **GET /events/{prettyName}**: Busca um evento específico pelo seu nome simplificado (pretty name).
- - **Resposta**: 
    ```json
    {
    "eventId": 5,
    "title": "CodeCraft Summit 2025",
    "prettyName": "codecraft-summit-2025",
    "location": "Online",
    "price": 0.0,
    "startDate": "2025-03-16",
    "endDate": "2025-03-18",
    }
    ```

#### 2. **Inscrições**

- **POST /subscriptions/{prettyName}/{userId}**: Inscrição em um evento com indicação de outro usuário.
- **POST /subscriptions/{prettyName}**: Inscrição em um evento, sem indicação de usuário.
  - **Body**: 
    ```json
    {
      "name": "João",
      "email": "joao@example.com"
    }
    ```
  - **Resposta**:
    ```json
    {
      "subscriptionNumber": 123,
      "url": "http://codecraft.com/subscriptions/evento-exemplo/joao"
    }
    ```

- **GET /subscriptions/{prettyName}/ranking**: Exibe o ranking geral de inscrições para um evento.
  - **Resposta**:
    ```json
    [
      {
          "subscribers": 6,
          "userId": 21,
          "name": "Tony Stark"
      },
      {
          "subscribers": 5,
          "userId": 4,
          "name": "Professor Isidro"
      },
      {
          "subscribers": 4,
          "userId": 34,
          "name": "Charles Xavier"
      }
    ]
    ```

- **GET /subscriptions/{prettyName}/ranking/{userId}**: Exibe o ranking de um usuário específico para um evento.
   - **Resposta**:
    ```json
    {
      "item": {
        "subscribers": 4,
        "userId": 34,
        "name": "Charles Xavier"
    },
    "position": 3
    }
    ```

