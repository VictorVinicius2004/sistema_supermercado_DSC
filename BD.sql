CREATE DATABASE SistemaSupermercado CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE SistemaSupermercado;

CREATE TABLE Funcionario (
	ID_Funcionario INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(150) NOT NULL,
    Sexo VARCHAR(20) NOT NULL,
    Data_Nascimento DATE NOT NULL,
    Email VARCHAR(120) NOT NULL,
    Tel VARCHAR(50) NOT NULL,
    CPF VARCHAR(14) NOT NULL,
    Tipo_Usuario VARCHAR(50) NOT NULL,
    Senha VARCHAR(255) NOT NULL
);

CREATE TABLE Mercadoria (
	Codigo INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(150) NOT NULL,
    Tipo VARCHAR(80) NOT NULL,
    Modelo VARCHAR(80),
    Descricao TEXT,
    Preco_Unitario DECIMAL(10,2) NOT NULL,
    Quantidade_Estoque INT NOT NULL,
    Fornecedor VARCHAR(50) NOT NULL
);

CREATE TABLE Venda (
	ID_Venda INT AUTO_INCREMENT PRIMARY KEY,
    Data_Venda DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Valor_Total DECIMAL(10,2) NOT NULL,
    ID_Identificador_Funcionario INT NOT NULL,
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

INSERT INTO Funcionario (Nome, Sexo, Data_Nascimento, Email, Tel, CPF, Tipo_Usuario, Senha) VALUES
('Ana Souza', 'Feminino', '1990-05-14', 'ana.admin@supermercado.com', '(11) 99999-1111', '111.111.111-11', 'ADMINISTRADOR', '$2a$10$wK1yR9N1A7pQxUe8z/j5ou.1B2/8YxQzB9qP3h.R8aV1kM9bW'),
('Carlos Silva', 'Masculino', '1998-10-22', 'carlos.caixa@supermercado.com', '(11) 98888-2222', '222.222.222-22', 'PADRAO', '$2a$10$xyz123abc456def789ghi.jklmnopqrstuvwxyzABCDEFGHIJ');

INSERT INTO Mercadoria (Nome, Tipo, Modelo, Descricao, Preco_Unitario, Quantidade_Estoque, Fornecedor) VALUES
('Arroz Agulhinha 5kg', 'Alimento', 'Tipo 1', 'Arroz branco tradicional', 25.50, 100, 'Cerealista Juca'),
('Feijão Carioca 1kg', 'Alimento', 'Tipo 1', 'Feijão carioca tipo exportação', 8.90, 150, 'Cerealista Juca'),
('Leite Integral 1L', 'Laticínio', 'Caixa', 'Leite UHT Integral', 5.50, 200, 'Fazenda Bela Vista'),
('Sabão em Pó 1kg', 'Limpeza', 'Caixa', 'Sabão em pó fragrância lavanda', 12.30, 80, 'Química Limpa');

INSERT INTO Venda (Data_Venda, Valor_Total, ID_Identificador_Funcionario) VALUES
('2026-05-10 14:30:00', 59.90, 2),
('2026-05-15 09:15:00', 55.00, 1);

INSERT INTO ItemVenda (ID_Venda, Codigo_Mercadoria, Quantidade, Subtotal) VALUES
(1, 1, 2, 51.00),
(1, 2, 1, 8.90);

INSERT INTO ItemVenda (ID_Venda, Codigo_Mercadoria, Quantidade, Subtotal) VALUES
(2, 3, 10, 55.00);
