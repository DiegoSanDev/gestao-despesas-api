# API Para gestão de despesas

Esta API permite gerenciar despesas de maneira eficiente, possibilitando o cadastro, consulta,
atualização e exclusão de despesas. Através da API, também é possível registrar e consultar despesas
parceladas, fornecendo flexibilidade no controle financeiro.

### Endpoints

Todos os endpoints da API estão disponíveis sob o path base: /v1/expenses.

### Funcionalidades principais:
1. <b>Criação de despesas</b>: Crie novas despesas, com ou sem parcelamento.
2. <b>Consulta de despesas</b>: Consulte detalhes de uma despesa específica ou liste todas as despesas cadastradas.
3. <b>Gerenciamento de parcelamento:</b>: Controle de despesas parceladas, com cálculo automático do valor de cada parcela.

### Exemplo de Endpoints:

1. POST /v1/expenses: Cria uma nova despesa.
2. GET /v1/expenses/{id}: Consulta uma despesa específica.
3. PUT /v1/expenses/{id}: Atualiza as informações de uma despesa.
4. DELETE /v1/expenses/{id}: Remove uma despesa do sistema.

### Tecnologias utilizadas

| Nome              | Versão  | Documentação                                  |
|-------------------|---------|-----------------------------------------------|
| Java              | 21      | https://docs.oracle.com/en/java/javase/21/    |
| Spring Boot       | 3.3.2   | https://docs.spring.io/spring-boot/index.html |
| Junit             | 5.10.3  | https://junit.org/junit5/                     |
| Mockito           | 5.11.0  | https://site.mockito.org/                     |
| Spring Data JDBC  | 3.3.2   | https://spring.io/projects/spring-data-jdbc   |
| H2 Database       | 2.2.224 | https://www.h2database.com/html/main.html     |
| springdoc-openapi | v2.6.0  | https://springdoc.org/                        |