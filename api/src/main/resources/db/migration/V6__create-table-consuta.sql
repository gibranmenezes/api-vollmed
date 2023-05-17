create table consulta (

    id serial primary key,
    medico_id bigint not null,
    paciente_id bigint not null,
    data Timestamp without Time Zone not null,
    FOREIGN KEY (medico_id) REFERENCES medicos (id),
    FOREIGN KEY (paciente_id) REFERENCES pacientes (id)

);