
INSERT INTO admins (login, password) VALUES
    (
        'admin',
        '$2a$10$VXlipbClYjRHzHBhA5ojWerLZmXBeVdnzbrUnhz462gUZ.nMnVYci'
    );


INSERT INTO judges (email, password, full_name) VALUES
    (
        'msd2007msd02@gmail.com',
        '$2a$10$VXlipbClYjRHzHBhA5ojWerLZmXBeVdnzbrUnhz462gUZ.nMnVYci',
        'Marshall Eriksen'
    );


INSERT INTO external_services (login, password, webhook_url, webhook_secret) VALUES
    (
        'fines',
        '$2a$10$VXlipbClYjRHzHBhA5ojWerLZmXBeVdnzbrUnhz462gUZ.nMnVYci',
        'http://192.168.100.160:7080/api/v1/fines-penalties/court/penalty-webhook',
        'secret'
    );


INSERT INTO bhm_amounts(amount) VALUES (20000);