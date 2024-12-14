CREATE DATABASE task_management;

USE task_management;

CREATE TABLE tasks (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       deadline DATE,
                       priority VARCHAR(50),
                       status VARCHAR(50)
);
