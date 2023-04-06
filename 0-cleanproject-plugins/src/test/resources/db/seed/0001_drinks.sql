INSERT INTO bar (id, title, administrator, zip, city, street, number)
VALUES ('194df0d2-9d0e-451c-aa6a-a6065a8caf94', 'Marcels Bar', 'Marcel Wagner', '77833', 'Ottersweier', 'Am Laufbach',
        '3');
INSERT INTO bar (id, title, administrator, zip, city, street, number)
VALUES ('cb41dd3b-0922-40c7-8846-ce6a5adb33a0', 'Marcels zweite Bar', 'Marcel Wagner', '76133', 'Karlsruhe', 'Erzbergerstraße',
        '121');
INSERT INTO drink (id, title, price, amount, bar)
VALUES ('164df0d2-9d0e-451c-aa6a-a6065a8caf94', 'Coca-Cola', '300', '70', '194df0d2-9d0e-451c-aa6a-a6065a8caf94');
INSERT INTO drink (id, title, price, amount, bar)
VALUES ('91ff3c99-7ae1-419f-9ac6-ed74e962451d', 'Fanta', '250', '50', 'cb41dd3b-0922-40c7-8846-ce6a5adb33a0');