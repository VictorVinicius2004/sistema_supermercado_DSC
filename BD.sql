CREATE DATABASE SistemaSupermercado CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE SistemaSupermercado;

CREATE TABLE Funcionario (
    ID_Funcionario INT AUTO_INCREMENT PRIMARY KEY,
    Nome_Usuario VARCHAR(100) NOT NULL UNIQUE,
    Nome VARCHAR(150) NOT NULL,
    Sexo VARCHAR(20) NOT NULL,
    Data_Nascimento DATE NOT NULL,
    Email VARCHAR(120) NOT NULL,
    Tel VARCHAR(50) NOT NULL,
    CPF VARCHAR(14) NOT NULL,
    Tipo_Usuario VARCHAR(50) NOT NULL,
    Senha VARCHAR(255) NOT NULL,
    Ativo BOOLEAN NOT NULL DEFAULT 1
);

CREATE TABLE Mercadoria (
    Codigo INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(150) NOT NULL,
    Tipo VARCHAR(80) NOT NULL,
    Modelo VARCHAR(80),
    Descricao TEXT,
    Preco_Unitario DECIMAL(10,2) NOT NULL,
    Quantidade_Estoque INT NOT NULL,
    Fornecedor VARCHAR(50) NOT NULL,
    Ativo BOOLEAN NOT NULL DEFAULT 1
);

CREATE TABLE Venda (
    ID_Venda INT AUTO_INCREMENT PRIMARY KEY,
    Data_Venda DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Valor_Total DECIMAL(10,2) NOT NULL,
    ID_Identificador_Funcionario INT NOT NULL,
    Tipo_Pagamento VARCHAR(50) NOT NULL,
    Valor_Pago DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (ID_Identificador_Funcionario) REFERENCES Funcionario(ID_Funcionario)
);

CREATE TABLE ItemVenda (
    ID_ItemVenda INT AUTO_INCREMENT PRIMARY KEY,
    Quantidade INT NOT NULL,
    Subtotal DECIMAL(10,2) NOT NULL,
    ID_Venda INT NOT NULL,
    Codigo_Mercadoria INT NOT NULL,
    FOREIGN KEY (ID_Venda) REFERENCES Venda(ID_Venda),
    FOREIGN KEY (Codigo_Mercadoria) REFERENCES Mercadoria(Codigo)
);

INSERT INTO Funcionario (Nome, Nome_Usuario, Sexo, Data_Nascimento, Email, Tel, CPF, Tipo_Usuario, Senha) VALUES
('Admin da Silva', 'admin', 'Feminino', '1990-05-14', 'ana.admin@supermercado.com', '(11) 99999-1111', '111.111.111-11', 'ADMINISTRADOR', '$2a$10$uZRsNeZJ0u7KUoNKXakZp.AmdLD3Hb55FPWrMXvpp5TCKwOZIWKx6'),
('Padrão da Silva', 'padrao', 'Masculino', '1998-10-22', 'carlos.caixa@supermercado.com', '(11) 98888-2222', '222.222.222-22', 'PADRAO', '$2a$10$wWX/0uzvnkwUag6ct35NSOAchknFBNqrDzOOxQJy6lKoVdPn/n6PS');

INSERT INTO Mercadoria (Nome, Tipo, Modelo, Descricao, Preco_Unitario, Quantidade_Estoque, Fornecedor) VALUES
('Arroz Agulhinha 5kg', 'Alimento', 'Tipo 1', 'Arroz branco tradicional', 25.50, 100, 'Cerealista Juca'),
('Feijão Carioca 1kg', 'Alimento', 'Tipo 1', 'Feijão carioca tipo exportação', 8.90, 150, 'Cerealista Juca'),
('Leite Integral 1L', 'Laticínio', 'Caixa', 'Leite UHT Integral', 5.50, 200, 'Fazenda Bela Vista'),
('Sabão em Pó 1kg', 'Limpeza', 'Caixa', 'Sabão em pó fragrância lavanda', 12.30, 80, 'Química Limpa'),
('Café Torrado 500g', 'Alimento', 'Pacote', 'Café a vácuo premium 100% arábica', 18.50, 45, 'Fazenda Sul de Minas'),
('Óleo de Soja 900ml', 'Alimento', 'Garrafa PET', 'Óleo de soja refinado', 7.20, 120, 'Lovera Alimentos'),
('Detergente Líquido 500ml', 'Limpeza', 'Frasco', 'Detergente neutro para louças', 2.80, 300, 'Química Limpa'),
('Iogurte de Morango 1L', 'Laticínio', 'Garrafa', 'Iogurte integral batido sabor morango', 9.50, 60, 'Fazenda Bela Vista');

INSERT INTO Venda (Data_Venda, Valor_Total, ID_Identificador_Funcionario) VALUES
('2026-05-10 14:30:00', 59.90, 2),
('2026-05-15 09:15:00', 55.00, 1),
('2026-06-01 10:00:00', 33.00, 1),
('2026-06-05 18:20:00', 109.90, 2);

INSERT INTO ItemVenda (ID_Venda, Codigo_Mercadoria, Quantidade, Subtotal) VALUES
(1, 1, 2, 51.00),
(1, 2, 1, 8.90);

INSERT INTO ItemVenda (ID_Venda, Codigo_Mercadoria, Quantidade, Subtotal) VALUES
(2, 3, 10, 55.00);

INSERT INTO ItemVenda (ID_Venda, Codigo_Mercadoria, Quantidade, Subtotal) VALUES
(3, 5, 1, 18.50),
(3, 2, 1, 8.90),
(3, 7, 2, 5.60);

INSERT INTO ItemVenda (ID_Venda, Codigo_Mercadoria, Quantidade, Subtotal) VALUES
(4, 1, 3, 76.50),
(4, 6, 2, 14.40),
(4, 8, 2, 19.00);

INSERT INTO Mercadoria (Nome, Tipo, Modelo, Descricao, Preco_Unitario, Quantidade_Estoque, Fornecedor) VALUES
('Macarrão Espaguete 500g', 'Alimento', 'Pacote', 'Massa de sêmola tipo espaguete', 4.50, 200, 'Massas Ouro'),
('Molho de Tomate 340g', 'Alimento', 'Sachê', 'Molho de tomate tradicional', 3.20, 250, 'Massas Ouro'),
('Refrigerante Cola 2L', 'Bebida', 'Garrafa PET', 'Refrigerante sabor cola', 8.00, 180, 'Bebidas Brasil'),
('Suco de Laranja 1L', 'Bebida', 'Caixa', 'Suco integral de laranja', 6.90, 90, 'Fazenda Bela Vista'),
('Papel Higiênico 12 rolos', 'Limpeza', 'Pacote', 'Papel higiênico folha dupla', 22.90, 70, 'Química Limpa'),
('Sabonete em Barra 90g', 'Higiene', 'Unidade', 'Sabonete hidratante', 2.10, 300, 'Cosméticos Aroma'),
('Shampoo 350ml', 'Higiene', 'Frasco', 'Shampoo para cabelos normais', 15.90, 60, 'Cosméticos Aroma'),
('Pão de Forma 500g', 'Padaria', 'Pacote', 'Pão de forma tradicional', 7.50, 100, 'Padaria Trigo Dourado'),
('Manteiga com Sal 200g', 'Laticínio', 'Pote', 'Manteiga com sal', 11.80, 80, 'Fazenda Bela Vista'),
('Queijo Mussarela 400g', 'Laticínio', 'Peça', 'Queijo mussarela fatiado', 19.90, 50, 'Fazenda Bela Vista'),
('Frango Congelado 1kg', 'Açougue', 'Bandeja', 'Frango inteiro congelado', 13.50, 40, 'Frigorífico Bom Sabor'),
('Carne Moída 1kg', 'Açougue', 'Bandeja', 'Carne bovina moída', 28.90, 35, 'Frigorífico Bom Sabor');

INSERT INTO Venda (Data_Venda, Valor_Total, ID_Identificador_Funcionario) VALUES
('2026-06-10 11:00:00', 34.00, 2),
('2026-06-12 16:45:00', 45.10, 1),
('2026-06-18 09:30:00', 46.70, 2),
('2026-06-20 19:00:00', 55.90, 1),
('2026-06-25 13:15:00', 23.40, 2),
('2026-06-28 20:10:00', 58.50, 1),
('2026-07-01 10:05:00', 40.50, 2),
('2026-07-04 17:40:00', 79.70, 1);

INSERT INTO ItemVenda (ID_Venda, Codigo_Mercadoria, Quantidade, Subtotal) VALUES
(5, 9, 4, 18.00),
(5, 11, 2, 16.00);

INSERT INTO ItemVenda (ID_Venda, Codigo_Mercadoria, Quantidade, Subtotal) VALUES
(6, 13, 1, 22.90),
(6, 14, 3, 6.30),
(6, 15, 1, 15.90);

INSERT INTO ItemVenda (ID_Venda, Codigo_Mercadoria, Quantidade, Subtotal) VALUES
(7, 16, 2, 15.00),
(7, 17, 1, 11.80),
(7, 18, 1, 19.90);

INSERT INTO ItemVenda (ID_Venda, Codigo_Mercadoria, Quantidade, Subtotal) VALUES
(8, 19, 2, 27.00),
(8, 20, 1, 28.90);

INSERT INTO ItemVenda (ID_Venda, Codigo_Mercadoria, Quantidade, Subtotal) VALUES
(9, 10, 3, 9.60),
(9, 12, 2, 13.80);

INSERT INTO ItemVenda (ID_Venda, Codigo_Mercadoria, Quantidade, Subtotal) VALUES
(10, 1, 1, 25.50),
(10, 4, 2, 24.60),
(10, 7, 3, 8.40);

INSERT INTO ItemVenda (ID_Venda, Codigo_Mercadoria, Quantidade, Subtotal) VALUES
(11, 11, 3, 24.00),
(11, 9, 2, 9.00),
(11, 16, 1, 7.50);

INSERT INTO ItemVenda (ID_Venda, Codigo_Mercadoria, Quantidade, Subtotal) VALUES
(12, 18, 2, 39.80),
(12, 20, 1, 28.90),
(12, 3, 2, 11.00);
