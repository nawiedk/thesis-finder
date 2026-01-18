CREATE TABLE supervisor (
                            id BIGSERIAL PRIMARY KEY,
                            title VARCHAR(255),
                            first_name VARCHAR(255) NOT NULL,
                            last_name VARCHAR(255) NOT NULL,
                            email VARCHAR(255) NOT NULL,
                            office VARCHAR(255),
                            phone VARCHAR(255)
);

CREATE TABLE topic (
                       id BIGSERIAL PRIMARY KEY,
                       topic VARCHAR(500) NOT NULL,
                       description text,
                       supervisor_id BIGINT,
                       supervisor_key INT,
                       CONSTRAINT fk_supervisor FOREIGN KEY (supervisor_id) REFERENCES supervisor(id)
);

INSERT INTO SUPERVISOR (ID, FIRST_NAME, LAST_NAME, EMAIL, TITLE)
VALUES (1, 'Max', 'Mustermann', 'max@hhu.de', 'DR');