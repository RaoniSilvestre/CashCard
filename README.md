# CashCard

CashCard é uma aplicação Spring Boot que permite o gerenciamento de cartões de pagamento. Este projeto implementa um CRUD (Create, Read, Update, Delete) para gerenciar as informações dos cartões, permitindo que os usuários criem, visualizem, atualizem e excluam seus cartões de pagamento.

## Funcionalidades

- **Gerenciamento de Cartões**: Permite que os usuários adicionem, visualizem, atualizem e excluam seus cartões de pagamento.
- **Autenticação**: Utiliza autenticação básica para garantir que apenas usuários autorizados possam acessar suas informações.
- **Paginação**: Suporte à paginação nas listas de cartões, facilitando a navegação através de muitos registros.

## Estrutura do Projeto

O projeto é dividido nas seguintes partes principais:

- **`CashCard`**: Um `record` que representa um cartão de pagamento, contendo um identificador, um saldo e o nome do proprietário.

```java
  record CashCard(@Id Long id, Double amount, String owner) { }
```

- **`CashCardApplication`**: Classe principal da aplicação, que inicializa o Spring Boot.

```java

@SpringBootApplication
public class CashCardApplication {
    public static void main(String[] args) {
        SpringApplication.run(CashCardApplication.class, args);
    }
}
```
- **`CashCardController`**: Controlador REST que gerencia as requisições relacionadas aos cartões. Fornece endpoints para operações CRUD.

```java
@RestController
@RequestMapping("/cashcards")
class CashCardController {
    // Métodos para gerenciar CashCards
}
```
- **`CashCardRepository`**: Repositório que fornece acesso aos dados dos cartões, incluindo operações CRUD e suporte à paginação.

```java
interface CashCardRepository extends CrudRepository<CashCard, Long>, PagingAndSortingRepository<CashCard, Long> {
    // Métodos para acesso a dados
}
```

- **`SecurityConfig`**: Configuração de segurança da aplicação, definindo as regras de autenticação e os usuários em memória.

```java
@Configuration
class SecurityConfig {
    // Métodos para configuração de segurança
}
```

## Como Executar a Aplicação

Para executar a aplicação CashCard, siga as instruções abaixo:

Clone o repositório:

```bash
git clone <URL-do-repositório>
cd CashCard
```
Certifique-se de que você tem o JDK 17 ou superior instalado.

Compile e execute a aplicação:

```bash
./gradlew bootRun
```

Acesse a API: A API estará disponível em http://localhost:8080/cashcards.

## Endpoints da API:

- GET /cashcards: Lista todos os CashCards do usuário autenticado (suporta paginação).
- GET /cashcards/{id}: Busca um CashCard específico pelo ID.
- POST /cashcards: Cria um novo CashCard.
- PUT /cashcards/{id}: Atualiza um CashCard existente.
- DELETE /cashcards/{id}: Deleta um CashCard existente.

## Autenticação

A aplicação utiliza autenticação básica. Os seguintes usuários estão disponíveis para testes:
- Usuário: sarah1
- Senha: abc123
- Role: CARD-OWNER
- Usuário: hank-owns-no-cards
- Senha: qrs456
- Role: NON-OWNER
