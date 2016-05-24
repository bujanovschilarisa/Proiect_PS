CREATE DATABASE proiect_ps;
USE proiect_ps;

CREATE TABLE IF NOT EXISTS user (
  id INT UNIQUE AUTO_INCREMENT PRIMARY KEY,
  name_user VARCHAR(45) UNIQUE NOT NULL,
  password_user VARCHAR(100) NOT NULL
);

INSERT INTO user (name_user, password_user) 
VALUES
('bujanovschi_larisa', '6b1af9a521ff4a6c7942cea27193f001'),
('boar_eleonora', '74da8112bef4f25601169ba63fca3244'),
('huet_monica', 'f854b924e1621ba254e4da6c59be8506');