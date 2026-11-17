CREATE DATABASE IF NOT EXISTS fonoagenda CHARACTER SET utf8mb4;
USE fonoagenda;

CREATE TABLE IF NOT EXISTS paciente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    idade INT,
    queixa VARCHAR(255),
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS fonoaudiologo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especialidade VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS consulta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_id INT NOT NULL,
    fonoaudiologo_id INT NOT NULL,
    data DATE NOT NULL,
    hora TIME NOT NULL,
    FOREIGN KEY (paciente_id) REFERENCES paciente(id),
    FOREIGN KEY (fonoaudiologo_id) REFERENCES fonoaudiologo(id),
    UNIQUE KEY uk_fono_horario (fonoaudiologo_id, data, hora)
);

INSERT INTO paciente (nome, idade, queixa, telefone, email, endereco) VALUES
('Ana Souza', 6, 'Troca de letras na fala', '(33) 99999-1111', 'ana@email.com', 'Rua das Flores, 100'),
('Carlos Lima', 45, 'Rouquidao frequente', '(33) 99999-2222', 'carlos@email.com', 'Av. Central, 250'),
('Marina Alves', 3, 'Atraso na fala', '(33) 99999-3333', 'marina@email.com', 'Rua Minas Gerais, 78');

INSERT INTO fonoaudiologo (nome, especialidade) VALUES
('Dra. Helena Rocha', 'Linguagem infantil'),
('Dr. Paulo Mendes', 'Voz e motricidade orofacial');