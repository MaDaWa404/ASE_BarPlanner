INSERT INTO person (id, username, password_hash, lastname, firstname)
VALUES ('594df0d2-9d0e-451c-aa6a-a6065a8caf94', 'MaDaWa', 'wkeugcvbweu', 'Wagner', 'Marcel');
INSERT INTO person (id, username, password_hash, lastname, firstname)
VALUES ('8be1dba7-aebd-4cec-a109-dd35f823d0e7', 'MusterMax', '1234', 'Mustermann', 'Max');
INSERT INTO bar (id, title, administrator, zip, city, street, number)
VALUES ('194df0d2-9d0e-451c-aa6a-a6065a8caf94', 'Marcels Bar', '594df0d2-9d0e-451c-aa6a-a6065a8caf94', '77833',
        'Ottersweier', 'Am Laufbach', '3');
INSERT INTO drink (id, title, price, amount, bar)
VALUES ('164df0d2-9d0e-451c-aa6a-a6065a8caf94', 'Coca-Cola', '300', '70', '194df0d2-9d0e-451c-aa6a-a6065a8caf94');