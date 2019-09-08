DELETE from payments;

INSERT INTO payments (id, sender_user_id, recipient_user_id, amount)
VALUES (1, 1, 2, 10.50),
       (2, 1, 2, 10.50),
       (3, 3, 4, 100),
       (4, 2, 3, 0.30);