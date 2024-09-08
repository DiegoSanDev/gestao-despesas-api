--INSERT EXPENSE CATEGORY
INSERT INTO expense_category(name, description) VALUES('FOOD', 'Despesas com alimentação e refeição, incluindo restaurantes e delivery.');
INSERT INTO expense_category(name, description) VALUES('TAXES', 'Despesas relacionadas a impostos, como, IPVA, IPTU e outros tributos semelhantes');
INSERT INTO expense_category(name, description) VALUES('OTHERS', 'Qualquer outra tipo de despesa');
INSERT INTO expense_category(name, description) VALUES('HEALTH', 'Despesas médicas, odontológicas, medicamentos e outras despesas relacionadas à saude.');
INSERT INTO expense_category(name, description) VALUES('LEISURE', 'Despesas com cinema, shows, eventos, hobbies, entre outros.');
INSERT INTO expense_category(name, description) VALUES('HOUSING', 'Despesas relacionadas a aluguel, hipoteca, contas de serviços públicos, e manutenção da casa.');
INSERT INTO expense_category(name, description) VALUES('CLOTHES', 'Despesas com roupas, calçados e acessórios.');
INSERT INTO expense_category(name, description) VALUES('EDUCATION', 'Despesas com cursos, mensalidades escolares, material didático, entre outros.');
INSERT INTO expense_category(name, description) VALUES('TRANSPORT', 'Despesas com transporte público, combustível, manutenção de veículo, entre outros.');

--INSERT PAYMENT METHOD
INSERT INTO payment_method(name, description) VALUES('CASH', 'Dinheiro físico, incluindo moedas e cédulas, usado para realizar pagamentos diretamente.');
INSERT INTO payment_method(name, description) VALUES('CREDIT_CARD', 'Método de pagamento que permite realizar compras com crédito emprestado, com pagamento posterior.');
INSERT INTO payment_method(name, description) VALUES('DEBIT_CARD', 'Cartão que retira dinheiro diretamente da conta bancária do usuário ao fazer uma compra, em vez de emprestar fundos.');
INSERT INTO payment_method(name, description) VALUES('BANK_TRANSFER', 'Transferência direta de dinheiro de uma conta bancária para outra, geralmente usada para pagamentos de maior valor ou transações recorrentes.');
INSERT INTO payment_method(name, description) VALUES('BOLETO', ' Método de pagamento brasileiro que gera um boleto para ser pago em bancos, caixas eletrônicos ou online');
INSERT INTO payment_method(name, description) VALUES('PIX', 'Sistema de pagamento instantâneo criado pelo Banco Central do Brasil, que permite transferências entre contas em tempo real.');
INSERT INTO payment_method(name, description) VALUES('CHECK', 'Documento escrito, datado e assinado que autoriza um banco a pagar uma quantia específica de dinheiro ao portador.');
INSERT INTO payment_method(name, description) VALUES('OTHER', 'Qualquer outro método de pagamento não listado acima, como criptomoeda, carteiras digitais ou escambo.');