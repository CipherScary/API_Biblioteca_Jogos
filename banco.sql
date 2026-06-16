CREATE DATABASE IF NOT EXISTS api_jogos;

USE api_jogos;

CREATE TABLE IF NOT EXISTS jogos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    plataforma VARCHAR(50) NOT NULL,
    preco DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL
);

INSERT INTO usuarios (nome, email, senha)
SELECT 'Administrador', 'admin@biblioteca.com', '123456'
WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE email = 'admin@biblioteca.com'
);
