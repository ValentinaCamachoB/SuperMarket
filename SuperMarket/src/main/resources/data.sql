
INSERT INTO categories(name, description)
VALUES
('Beverages', 'Drinks and liquids'),
('Snacks', 'Chips and cookies'),
('Dairy', 'Milk and dairy products'),
('Bakery', 'Bread and bakery products'),
('Cleaning', 'Cleaning supplies');

INSERT INTO products(name, barcode, price, stock, active, category_id)
VALUES
('Coca Cola 400ml', '770100000001', 3500, 80, 1, 1),
('Pepsi 400ml', '770100000002', 3400, 60, 1, 1),
('Doritos Nacho', '770100000003', 5000, 40, 1, 2),
('Oreo Cookies', '770100000004', 4500, 50, 1, 2),
('Whole Milk 1L', '770100000005', 4200, 70, 1, 3),
('Cheese 500g', '770100000006', 12000, 25, 1, 3),
('White Bread', '770100000007', 3000, 35, 1, 4),
('Chocolate Cake', '770100000008', 18000, 10, 1, 4),
('Detergent', '770100000009', 15000, 20, 1, 5),
('Bleach', '770100000010', 7000, 30, 1, 5),
('Sprite 400ml', '770100000011', 3300, 15, 1, 1),
('Fanta 400ml', '770100000012', 3300, 25, 1, 1),
('Inactive Product', '770100000013', 1000, 0, 0, 2);

INSERT INTO suppliers(name, nit, phone, email)
VALUES
('Coca Cola Company', '900111111', '3001111111', 'cocacola@email.com'),
('Pepsi Company', '900222222', '3002222222', 'pepsi@email.com'),
('Colombina', '900333333', '3003333333', 'colombina@email.com');

INSERT INTO product_supplier(product_id, supplier_id)
VALUES
(1,1),
(2,2),
(3,3),
(4,3),
(11,1),
(12,2);

INSERT INTO employees(id_number, name, position, hire_date, salary)
VALUES
('100001', 'Carlos Perez', 'ADMINISTRATOR', '2024-01-10', 3500000),
('100002', 'Laura Gomez', 'CASHIER', '2024-03-15', 1800000),
('100003', 'Andres Ruiz', 'AUXILIARY', '2024-05-20', 1600000),
('100004', 'Sofia Torres', 'CASHIER', '2024-06-01', 1800000);

INSERT INTO sales(sale_date, subtotal, tax, total, employee_id)
VALUES
('2026-05-20', 10000, 1900, 11900, 2);

INSERT INTO sale_details(quantity, unit_price, subtotal, sale_id, product_id)
VALUES
(2, 5000, 10000, 1, 3);