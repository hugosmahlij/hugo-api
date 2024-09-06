CREATE TABLE concepto_laboral (
    id INT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    hs_minimo INT,
    hs_maximo INT,
    laborable BOOLEAN NOT NULL
);