DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

CREATE SEQUENCE IF NOT EXISTS public.secuencia_vocabulario
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
	
CREATE SEQUENCE IF NOT EXISTS public.secuencia_posteos
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
	
CREATE TABLE IF NOT EXISTS public.vocabulario
(
    id_vocabulario bigint NOT NULL DEFAULT nextval('secuencia_vocabulario'::regclass),
    palabra character varying(80) COLLATE pg_catalog."default" NOT NULL,
    cantidad_archivos integer NOT NULL,
    frecuencia_maxima integer NOT NULL,
    CONSTRAINT pk_id_vocabulario PRIMARY KEY (id_vocabulario),
    CONSTRAINT uq_palabra UNIQUE (palabra)
);

CREATE TABLE IF NOT EXISTS public.posteos
(
    id_posteo bigint NOT NULL DEFAULT nextval('secuencia_posteos'::regclass),
    nombre_archivo character varying(80) COLLATE pg_catalog."default" NOT NULL,
    frecuencia integer NOT NULL,
    palabra_vocabulario character varying(80) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_id_posteo PRIMARY KEY (id_posteo),
    CONSTRAINT fk_palabra_vocabulario FOREIGN KEY (palabra_vocabulario)
        REFERENCES public.vocabulario (palabra) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
