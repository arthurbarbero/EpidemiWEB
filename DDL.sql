create schema business;
create schema users;

CREATE USER epidemiuser WITH PASSWORD 'adm1n';

GRANT CONNECT ON DATABASE epidemiweb TO epidemiuser;
GRANT ALL ON SCHEMA business TO epidemiuser;
GRANT ALL ON SCHEMA users TO epidemiuser;


-- Users Tables
CREATE TABLE users.adr_address (
    id_address SERIAL NOT NULL,
    st_address CHARACTER VARYING NOT NULL,
    nm_number INT NOT NULL,
    st_complement CHARACTER VARYING,
    st_district CHARACTER VARYING NOT NULL,
    st_city CHARACTER VARYING NOT NULL,
    st_state CHARACTER VARYING NOT NULL,
    st_country CHARACTER VARYING NOT NULL,
    created_at DATE NULL DEFAULT now(),
	updated_at DATE NULL DEFAULT now(),
    CONSTRAINT pk_id_address PRIMARY KEY (id_address)
);

CREATE TABLE users.usr_users (
    id_user SERIAL NOT NULL,
    st_name CHARACTER VARYING NOT NULL,
    st_email CHARACTER VARYING NOT NULL UNIQUE,
    st_password CHARACTER VARYING NOT NULL,
    id_address INTEGER NOT NULL,
    created_at DATE NULL DEFAULT now(),
	updated_at DATE NULL DEFAULT now(),
    CONSTRAINT pk_id_user PRIMARY KEY(id_user),
    CONSTRAINT fk_address FOREIGN KEY(id_address) 
        REFERENCES users.adr_address (id_address) 
            ON DELETE CASCADE 
            ON UPDATE CASCADE
);

CREATE TABLE users.grp_groups (
    id_group SERIAL NOT NULL,
    st_name CHARACTER VARYING NOT NULL UNIQUE,
    created_at DATE NULL DEFAULT now(),
	updated_at DATE NULL DEFAULT now(),
    CONSTRAINT pk_id_group PRIMARY KEY(id_group)
);

CREATE TABLE users.users_groups (
    id_user INTEGER NOT NULL,
    id_group INTEGER NOT NULL,
    CONSTRAINT fk_users_groups_user FOREIGN KEY (id_user)
        REFERENCES users.usr_users (id_user),
    CONSTRAINT fk_users_groups_groups FOREIGN KEY (id_group)
        REFERENCES users.grp_groups (id_group)
);

-- Business TABLES

CREATE TABLE business.dse_disease (
	id_disease SERIAL NOT NULL,
	st_name CHARACTER VARYING NOT NULL UNIQUE,
	created_at DATE NOT NULL DEFAULT now(),
	updated_at DATE NOT NULL DEFAULT now(),
	CONSTRAINT pk_id_disease PRIMARY KEY (id_disease)
);

CREATE TABLE business.sym_symptoms (
    id_symptom SERIAL NOT NULL,
    st_name CHARACTER VARYING NOT NULL UNIQUE,
    st_description CHARACTER VARYING NOT NULL,
    nm_severity INT NOT NULL,
    created_at DATE NOT NULL DEFAULT now(),
	updated_at DATE NOT NULL DEFAULT now(),
    CONSTRAINT pk_id_symptom PRIMARY KEY (id_symptom)
);

CREATE TABLE business.disease_symptoms (
    id_disease Integer NOT NULL,
    id_symptom Integer NOT NULL,
    CONSTRAINT fk_disease_symptoms_disease FOREIGN KEY (id_disease)
        REFERENCES business.dse_disease (id_disease),
    CONSTRAINT fk_disease_symptoms_symptoms FOREIGN KEY (id_symptom)
        REFERENCES business.sym_symptoms (id_symptom)
);

CREATE TABLE business.inc_incidence (
    id_incidence SERIAL NOT NULL,
    id_disease Integer NOT NULL,
    id_user Integer NOT NULL,
    dt_incidence DATE NOT NULL DEFAULT now(),
    created_at DATE NOT NULL DEFAULT now(),
	updated_at DATE NOT NULL DEFAULT now(),
    CONSTRAINT pk_id_incidence PRIMARY KEY (id_incidence),
    CONSTRAINT fk_inc_incidence_disease FOREIGN KEY (id_disease)
        REFERENCES business.dse_disease (id_disease),
    CONSTRAINT fk_inc_incidence_user FOREIGN KEY (id_user)
        REFERENCES users.usr_users (id_user)
);

GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA business TO epidemiuser;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA users TO epidemiuser;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA business TO epidemiuser;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA users TO epidemiuser;

-- INITAL VALUES

INSERT INTO users.adr_address(st_address, nm_number, st_complement, st_district, st_city, st_state, st_country)
VALUES ('Rua. Fictícia', 123, 'Casa azul', 'Bairro bom', 'Cidade boa', 'Estado bom', 'País bom também');

INSERT INTO users.usr_users(st_name, st_email, st_password, id_address)
VALUES ('First_User', 'first_user@email.com', '123456', 1);

INSERT INTO users.grp_groups (st_name) 
VALUES ('PATIENT'), ('HEALTH_AGENT');

INSERT INTO users.users_groups (id_user, id_group)
VALUES (1, 1), (1, 2);

INSERT INTO business.dse_disease (st_name)
VALUES ('Test_disease');

INSERT INTO business.sym_symptoms (st_name, st_description, nm_severity)
VALUES ('Test_symptom', 'Test_description', 1);

INSERT INTO business.disease_symptoms (id_disease, id_symptom) 
VALUES (1, 1);

INSERT INTO business.inc_incidence (id_disease, id_user, dt_incidence)
VALUES (1, 1, now());