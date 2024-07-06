CREATE TABLE ACCOUNTS (
                          ID UUID PRIMARY KEY,
                          TYPE VARCHAR(20) NOT NULL,
                          OPENING_DATE TIMESTAMP NOT NULL,
                          IS_TEMPORARY BOOLEAN NOT NULL,
                          CLOSURE_DATE TIMESTAMP DEFAULT NULL,
                          INITIAL_DEPOSIT DOUBLE PRECISION NOT NULL,
                          CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CREATED_BY VARCHAR(50) DEFAULT USER(),
                          UPDATED_AT TIMESTAMP DEFAULT NULL,
                          UPDATED_BY VARCHAR(50) DEFAULT NULL
);

CREATE TABLE PERSONS (
                         ACCOUNTS UUID PRIMARY KEY,
                         FIRST_NAME VARCHAR(35) NOT NULL,
                         LAST_NAME VARCHAR(35) NOT NULL,
                         DATE_OF_BIRTH DATE NOT NULL,
                         CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         CREATED_BY VARCHAR(50) DEFAULT USER(),
                         UPDATED_AT TIMESTAMP DEFAULT NULL,
                         UPDATED_BY VARCHAR(50) DEFAULT NULL,
                         EMAIL VARCHAR(50) NOT NULL,
                         CONSTRAINT FK_ACCOUNTS FOREIGN KEY (ACCOUNTS) REFERENCES ACCOUNTS(ID)
);
