CREATE DATABASE IF NOT EXISTS micromarket_db;
USE micromarket_db;

CREATE TABLE IF NOT EXISTS categories (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS products (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(150)   NOT NULL,
    barcode     VARCHAR(50)    NOT NULL UNIQUE,
    price       DECIMAL(10,2)  NOT NULL,
    stock       INT            NOT NULL DEFAULT 0,
    active      TINYINT(1)     NOT NULL DEFAULT 1,
    category_id BIGINT,
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS suppliers (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(150) NOT NULL,
    nit     VARCHAR(20)  NOT NULL UNIQUE,
    phone   VARCHAR(20),
    email   VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS product_supplier (
    product_id  BIGINT NOT NULL,
    supplier_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, supplier_id),
    CONSTRAINT fk_ps_product  FOREIGN KEY (product_id)  REFERENCES products(id),
    CONSTRAINT fk_ps_supplier FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
);

CREATE TABLE IF NOT EXISTS employees (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_number    VARCHAR(20)    NOT NULL UNIQUE,
    name         VARCHAR(150)   NOT NULL,
    position     VARCHAR(20)    NOT NULL,
    hire_date    DATE,
    salary       DECIMAL(12,2)
);

CREATE TABLE IF NOT EXISTS sales (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    sale_date   DATE           NOT NULL,
    subtotal    DECIMAL(12,2)  NOT NULL DEFAULT 0,
    tax         DECIMAL(12,2)  NOT NULL DEFAULT 0,
    total       DECIMAL(12,2)  NOT NULL DEFAULT 0,
    employee_id BIGINT         NOT NULL,
    CONSTRAINT fk_sales_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

CREATE TABLE IF NOT EXISTS sale_details (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity     INT           NOT NULL,
    unit_price   DECIMAL(10,2) NOT NULL,
    subtotal     DECIMAL(12,2) NOT NULL,
    sale_id      BIGINT        NOT NULL,
    product_id   BIGINT        NOT NULL,
    CONSTRAINT fk_sd_sale    FOREIGN KEY (sale_id)    REFERENCES sales(id),
    CONSTRAINT fk_sd_product FOREIGN KEY (product_id) REFERENCES products(id)
);