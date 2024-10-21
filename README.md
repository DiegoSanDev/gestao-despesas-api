# API de Gestão de Despesas

## Descrição
Esta API permite gerenciar despesas de maneira eficiente, possibilitando o cadastro, consulta,
atualização e exclusão de despesas. Através da API, também é possível registrar e consultar despesas
parceladas, fornecendo flexibilidade no controle financeiro.

## Tecnologias utilizadas

| Nome              | Versão  | Documentação                                     |
|-------------------|---------|--------------------------------------------------|
| Maven             | 3.8+    | https://maven.apache.org/                        |
| Java              | 21      | https://docs.oracle.com/en/java/javase/21/       |
| Spring Boot       | 3.3.2   | https://docs.spring.io/spring-boot/index.html    |
| Junit             | 5.10.3  | https://junit.org/junit5/                        |
| Mockito           | 5.11.0  | https://site.mockito.org/                        |
| Spring Data JDBC  | 3.3.2   | https://spring.io/projects/spring-data-jdbc      |
| H2 Database       | 2.2.224 | https://www.h2database.com/html/main.html        | 
| springdoc-openapi | v2.6.0  | https://springdoc.org/                           |

## Instalação

1. Clone o repositório:

    ```bash
    git clone https://github.com/usuario/api-gestao-despesas.git
    ```
2. Compile e execute o projeto:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
   
## Endpoints

Todos os endpoints da API estão disponíveis sob o path base: /v1/expenses.

1. Funcionalidades principais:
   1. <b>Criação de despesas</b>: Crie novas despesas, com ou sem parcelamento.
   2. <b>Consulta de despesas</b>: Consulte detalhes de uma despesa específica ou liste todas as despesas cadastradas.
   3. <b>Gerenciamento de parcelamento:</b>: Controle de despesas parceladas, com cálculo automático do valor de cada parcela.

2. Exemplo de Endpoints:

   1. POST /v1/expenses: Cria uma nova despesa.
   2. GET /v1/expenses/{id}: Consulta uma despesa específica.
   3. PUT /v1/expenses/{id}: Atualiza as informações de uma despesa.
   4. DELETE /v1/expenses/{id}: Remove uma despesa do sistema.
   
### Criar Despesa (Sem Parcelas)

#### URL

`POST /v1/expenses`

#### Descrição

Cria uma nova despesa sem parcelamento.

#### Exemplo de Request

```json
{
  "amount": 99.00,
  "description": "pizza",
  "expenseDate": "2024-10-21",
  "category_type": "FOOD",
  "payment_method_type": "CREDIT_CARD"
}