ALTER TABLE usuario ALTER COLUMN id SET DEFAULT nextval ('seq_usuario'::regclass);
insert into usuario (nome, senha, login, excluido, email) values ('Administrador', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'admin', 'f', '');

