create schema business;
create schema users;

CREATE USER epidemiuser WITH PASSWORD 'adm1n';

GRANT CONNECT ON DATABASE epidemiweb TO epidemiuser;
GRANT USAGE ON SCHEMA business TO epidemiuser;
GRANT USAGE ON SCHEMA users TO epidemiuser;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA business TO epidemiuser;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA users TO epidemiuser;

-- Users Tables
CREATE TABLE users.adr_address (
    id_address SERIAL NOT NULL,
    st_address CHARACTER VARYING NOT NULL,
    nm_number INT NOT NULL,
    st_complement CHARACTER VARYING NOT NULL,
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
    fk_address bigint NOT NULL,
    created_at DATE NULL DEFAULT now(),
	updated_at DATE NULL DEFAULT now(),
    CONSTRAINT pk_id_user PRIMARY KEY(id_user),
    CONSTRAINT fk_address FOREIGN KEY(fk_address) 
        REFERENCES users.adr_address (id_address) 
            ON DELETE CASCADE 
            ON UPDATE CASCADE
) ;

CREATE TABLE users.grp_groups (
    id_group SERIAL NOT NULL,
    st_name CHARACTER VARYING NOT NULL,
    created_at DATE NULL DEFAULT now(),
	updated_at DATE NULL DEFAULT now(),
    CONSTRAINT pk_id_group PRIMARY KEY(id_group)
) ;

CREATE TABLE users.users_groups (
    fk_user bigint NOT NULL,
    fk_group bigint NOT NULL,
    CONSTRAINT fk_users_groups_user FOREIGN KEY (fk_user)
        REFERENCES users.usr_users (id_user),
    CONSTRAINT fk_users_groups_groups FOREIGN KEY (fk_group)
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
    id_symptoms SERIAL NOT NULL,
    st_name CHARACTER VARYING NOT NULL UNIQUE,
    st_description CHARACTER VARYING NOT NULL,
    nm_severity INT NOT NULL,
    created_at DATE NOT NULL DEFAULT now(),
	updated_at DATE NOT NULL DEFAULT now(),
    CONSTRAINT pk_id_symptoms PRIMARY KEY (id_symptoms)
);

CREATE TABLE business.disease_symptoms (
    fk_disease BIGINT NOT NULL,
    fk_symptoms BIGINT NOT NULL,
    CONSTRAINT fk_disease_symptoms_disease FOREIGN KEY (fk_disease)
        REFERENCES business.dse_disease (id_disease),
    CONSTRAINT fk_disease_symptoms_symptoms FOREIGN KEY (fk_symptoms)
        REFERENCES business.sym_symptoms (id_symptoms)
);

CREATE TABLE business.inc_incidence (
    id_incidence SERIAL NOT NULL,
    fk_disease BIGINT NOT NULL,
    fk_user BIGINT NOT NULL,
    dt_incidence DATE NOT NULL DEFAULT now(),
    created_at DATE NOT NULL DEFAULT now(),
	updated_at DATE NOT NULL DEFAULT now(),
    CONSTRAINT pk_id_incidence PRIMARY KEY (id_incidence),
    CONSTRAINT fk_inc_incidence_disease FOREIGN KEY (fk_disease)
        REFERENCES business.dse_disease (id_disease),
    CONSTRAINT fk_inc_incidence_user FOREIGN KEY (fk_user)
        REFERENCES users.usr_users (id_user)
);