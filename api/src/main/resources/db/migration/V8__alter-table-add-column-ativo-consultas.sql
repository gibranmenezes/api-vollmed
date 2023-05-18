alter table consultas add column ativa integer;
update consultas set ativa = 1;