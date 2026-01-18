CREATE TABLE supervisor (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            title VARCHAR(255),
                            first_name VARCHAR(255) NOT NULL,
                            last_name VARCHAR(255) NOT NULL,
                            email VARCHAR(255) NOT NULL,
                            office VARCHAR(255),
                            phone VARCHAR(255)
);

CREATE TABLE topic (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(500) NOT NULL,
                       description CLOB,
                       supervisor_id BIGINT,
                       CONSTRAINT fk_supervisor FOREIGN KEY (supervisor_id) REFERENCES supervisor(id)
);