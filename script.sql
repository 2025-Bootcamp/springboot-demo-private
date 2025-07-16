use mydb;

show tables;

CREATE TABLE employees
(
    id         BIGINT  NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255),
    age        INTEGER,
    gender     VARCHAR(255),
    salary     FLOAT(53),
    active     BOOLEAN NOT NULL DEFAULT TRUE,
    company_id BIGINT,
    PRIMARY KEY (id)
)

select *
from employees;

INSERT INTO `employees` (`name`, `age`, `gender`, `salary`, `company_id`)
VALUES ('John Smith', 32, 'MALE', 5000.0, 1);
INSERT INTO `employees` (`name`, `age`, `gender`, `salary`, `company_id`)
VALUES ('Jane Johnson', 28, 'FEMALE', 6000.0, 1);
INSERT INTO `employees` (`name`, `age`, `gender`, `salary`, `company_id`)
VALUES ('David Williams', 35, 'MALE', 5500.0, 1);
INSERT INTO `employees` (`name`, `age`, `gender`, `salary`, `company_id`)
VALUES ('Emily Brown', 23, 'FEMALE', 4500.0, 1);
INSERT INTO `employees` (`name`, `age`, `gender`, `salary`, `company_id`)
VALUES ('Michael Jones', 40, 'MALE', 7500.0, 1);

CREATE TABLE companies
(
    id   BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (id)
)

select *
from companies;

INSERT INTO companies (id, name)
VALUES (1, 'Acme Corporation'),
       (2, 'TechCom Solutions'),
       (3, 'Global Innovators'),
       (4, 'Stellar Enterprises'),
       (5, 'Nexus Industries');
