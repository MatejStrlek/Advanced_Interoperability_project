CREATE TABLE mobile (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        brand VARCHAR(255) NOT NULL,
                        price DOUBLE NOT NULL,
                        description VARCHAR(1000),
                        rating DOUBLE
);