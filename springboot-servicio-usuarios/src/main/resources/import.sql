INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('andres', '$2a$10$gCkE9rbPcXhmFynpWjVUv.DaRLtrtkuiXOzXpxDVIqZPsr7jJ/wZu', true, 'Andres', 'Guzman', 'profesor@bolsadeideas.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('gserranog', '$2a$10$Ya3z3xGR8GCk6Hqsz6UNA.HXSKHvqDfkoFSp/08DvV4HlywNjNd56', true, 'Gregorio', 'Serrano', 'gserranog@coltomex360.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);