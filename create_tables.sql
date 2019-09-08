CREATE DATABASE database0;
USE database0;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id bigint NOT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
);
INSERT INTO users (id, name)
VALUES (1, 'Alex'),
       (2, 'Poll'),
       (3, 'Martin'),
       (4, 'Tom');

DROP TABLE IF EXISTS payments;
CREATE TABLE payments (
  id bigint NOT NULL,
  sender_user_id bigint NOT NULL,
  recipient_user_id bigint NOT NULL,
  amount decimal(19,4) NOT NULL,
  PRIMARY KEY (id)
);

CREATE DATABASE database1;
USE database1;

DROP TABLE IF EXISTS payments;
CREATE TABLE payments (
  id bigint NOT NULL,
  sender_user_id bigint NOT NULL,
  recipient_user_id bigint NOT NULL,
  amount decimal(19,4) NOT NULL,
  PRIMARY KEY (id)
);

CREATE DATABASE database2;
USE database2;

DROP TABLE IF EXISTS payments;
CREATE TABLE payments (
  id bigint NOT NULL,
  sender_user_id bigint NOT NULL,
  recipient_user_id bigint NOT NULL,
  amount decimal(19,4) NOT NULL,
  PRIMARY KEY (id)
);