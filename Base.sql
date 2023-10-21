CREATE TABLE IF NOT EXISTS public.empleados
(
    edad integer NOT NULL,
    fecha date NOT NULL,
    salario numeric(38,2) NOT NULL,
    telefono integer NOT NULL,
    id bigint NOT NULL DEFAULT nextval('empleados_id_seq'::regclass),
    apellidom character varying(255) COLLATE pg_catalog."default",
    apellidop character varying(255) COLLATE pg_catalog."default",
    correo character varying(255) COLLATE pg_catalog."default",
    nombre character varying(255) COLLATE pg_catalog."default",
    sexo character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT empleados_pkey PRIMARY KEY (id)
);
CREATE INDEX emp_nom_index ON empleados (nombre);
CREATE INDEX emp_sal_index ON empleados (salario);
CREATE INDEX emp_sex_index ON empleados (sexo);
create table IF NOT EXISTS public.usuarios(usu varchar(20), pass varchar(100),bloqueado boolean,disabled boolean,
CONSTRAINT ususarios_pkey PRIMARY KEY (usu));

CREATE TABLE IF NOT EXISTS user_role (
  usu VARCHAR(20) NOT NULL,
  role VARCHAR(20) NOT NULL ,
  granted_date TIMESTAMP NOT NULL,
  PRIMARY KEY (usu, role),
  CONSTRAINT fk_user_role_user1
    FOREIGN KEY (usu)
    REFERENCES usuarios(usu)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);