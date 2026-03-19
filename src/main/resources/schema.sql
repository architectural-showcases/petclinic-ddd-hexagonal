CREATE TABLE IF NOT EXISTS owners (
    id         VARCHAR(36)  PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    street     VARCHAR(100) NOT NULL,
    city       VARCHAR(50)  NOT NULL,
    telephone  VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS pets (
    id         VARCHAR(36) PRIMARY KEY,
    owner_id   VARCHAR(36) NOT NULL REFERENCES owners(id) ON DELETE CASCADE,
    name       VARCHAR(30) NOT NULL,
    birth_date DATE        NOT NULL,
    type       VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS visits (
    id          VARCHAR(36)  PRIMARY KEY,
    pet_id      VARCHAR(36)  NOT NULL REFERENCES pets(id) ON DELETE CASCADE,
    visit_date  DATE         NOT NULL,
    description VARCHAR(500) NOT NULL
);

CREATE TABLE IF NOT EXISTS pet_types (
    name VARCHAR(50) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS vets (
    id         VARCHAR(36)  PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL
);

CREATE TABLE IF NOT EXISTS vet_specialties (
    vet_id         VARCHAR(36)  NOT NULL REFERENCES vets(id) ON DELETE CASCADE,
    specialty_name VARCHAR(50)  NOT NULL,
    PRIMARY KEY (vet_id, specialty_name)
);

CREATE TABLE IF NOT EXISTS specialties (
    name VARCHAR(50) PRIMARY KEY
);