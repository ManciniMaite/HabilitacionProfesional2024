-- BORRADO
DELETE FROM animal;
DELETE FROM raza;
DELETE FROM especie;
DELETE FROM tipo_especie;

-- INSERTADO DE TIPO_ESPECIE
INSERT INTO tipo_especie (id, nombre) VALUES (1, 'Animales pequeños');
INSERT INTO tipo_especie (id, nombre) VALUES (2, 'Animales grandes');
INSERT INTO tipo_especie (id, nombre) VALUES (3, 'No convencionales');

-- INSERTADO DE ESPECIE (asumiendo que especie es "perro", "gato", etc.)
INSERT INTO especie (id, nombre, tipo_especie_id) VALUES (1, 'perro', 1);
INSERT INTO especie (id, nombre, tipo_especie_id) VALUES (2, 'gato', 1);
INSERT INTO especie (id, nombre, tipo_especie_id) VALUES (3, 'cerdo', 2);
INSERT INTO especie (id, nombre, tipo_especie_id) VALUES (4, 'vaca', 2);
INSERT INTO especie (id, nombre, tipo_especie_id) VALUES (5, 'caballo', 2);
INSERT INTO especie (id, nombre, tipo_especie_id) VALUES (6, 'tortugas', 3);
INSERT INTO especie (id, nombre, tipo_especie_id) VALUES (7, 'hámster', 3);
INSERT INTO especie (id, nombre, tipo_especie_id) VALUES (8, 'conejo', 3);

-- Poblando razas de perros
INSERT INTO raza (id, nombre, especie_id) VALUES (1, 'Labrador Retriever', 1);
INSERT INTO raza (id, nombre, especie_id) VALUES (2, 'Bulldog Francés', 1);
INSERT INTO raza (id, nombre, especie_id) VALUES (3, 'Golden Retriever', 1);
INSERT INTO raza (id, nombre, especie_id) VALUES (4, 'Poodle', 1);

-- Poblando razas de gatos
INSERT INTO raza (id, nombre, especie_id) VALUES (5, 'Siames', 2);
INSERT INTO raza (id, nombre, especie_id) VALUES (6, 'Persa', 2);
INSERT INTO raza (id, nombre, especie_id) VALUES (7, 'Maine Coon', 2);
INSERT INTO raza (id, nombre, especie_id) VALUES (8, 'Bengala', 2);

-- Poblando razas de cerdos
INSERT INTO raza (id, nombre, especie_id) VALUES (9, 'Landrace', 3);
INSERT INTO raza (id, nombre, especie_id) VALUES (10, 'Duroc', 3);
INSERT INTO raza (id, nombre, especie_id) VALUES (11, 'Pietrain', 3);

-- Poblando razas de vacas
INSERT INTO raza (id, nombre, especie_id) VALUES (12, 'Holando-Argentina', 4);
INSERT INTO raza (id, nombre, especie_id) VALUES (13, 'Angus', 4);
INSERT INTO raza (id, nombre, especie_id) VALUES (14, 'Hereford', 4);

-- Poblando razas de caballos
INSERT INTO raza (id, nombre, especie_id) VALUES (15, 'Criollo', 5);
INSERT INTO raza (id, nombre, especie_id) VALUES (16, 'Pura Sangre', 5);
INSERT INTO raza (id, nombre, especie_id) VALUES (17, 'Árabe', 5);

-- Poblando razas de tortugas (normalmente se habla de especies)
INSERT INTO raza (id, nombre, especie_id) VALUES (18, 'Tortuga de Orejas Rojas', 6);
INSERT INTO raza (id, nombre, especie_id) VALUES (19, 'Tortuga Sulcata', 6);

-- Poblando razas de hámster
INSERT INTO raza (id, nombre, especie_id) VALUES (20, 'Hámster Sirio', 7);
INSERT INTO raza (id, nombre, especie_id) VALUES (21, 'Hámster Ruso', 7);

-- Poblando razas de conejos
INSERT INTO raza (id, nombre, especie_id) VALUES (22, 'Rex', 8);
INSERT INTO raza (id, nombre, especie_id) VALUES (23, 'Holland Lop', 8);
INSERT INTO raza (id, nombre, especie_id) VALUES (24, 'Angora', 8);


--ESTADOS
DELETE FROM estado
INSERT INTO estado (id, ambito, descripcion, nombre) VALUES (1,'turno','PENDIENTE DE ACEPTACION','RESERVADO');
INSERT INTO estado (id, ambito, descripcion, nombre) VALUES (2,'turno','CANCELADO','CANCELADO');
INSERT INTO estado (id, ambito, descripcion, nombre) VALUES (3,'turno','PENDIENTE DE ATENCION','ACEPTADO');
INSERT INTO estado (id, ambito, descripcion, nombre) VALUES (4,'turno','RECHAZADO','RECHAZADO');
INSERT INTO estado (id, ambito, descripcion, nombre) VALUES (5,'turno','FINALIZADO','ATENDIDO');