DROP SCHEMA IF EXISTS money_transfer;
CREATE SCHEMA money_transfer;
USE money_transfer;

CREATE TABLE users (
  uuid VARCHAR(36)  NOT NULL,
  name VARCHAR(30) NOT NULL,
  PRIMARY KEY (uuid),
  UNIQUE (name)
);

CREATE TABLE accounts (
  uuid VARCHAR(36)  NOT NULL,
  useruuid VARCHAR(36)  NOT NULL,
  balance DECIMAL NOT NULL,
  PRIMARY KEY (uuid)
  );

CREATE TABLE transactions (
  uuid VARCHAR(36)  NOT NULL,
  type VARCHAR(10)  NOT NULL,
  accountuuid VARCHAR(36) NOT NULL,
  details VARCHAR(36),
  amount DECIMAL NOT NULL,
  inserted_time BIGINT NOT NULL,
  PRIMARY KEY (uuid)
  );

INSERT INTO users (uuid, name)
VALUES  ('f887b36f-b799-4eb5-93c2-5698d84ee7c0', 'Mic');

INSERT INTO accounts (uuid, useruuid, balance)
VALUES  ('d477d4f6-4650-4909-88a6-d004d6825f94', 'f887b36f-b799-4eb5-93c2-5698d84ee7c0', '45.67'),
('73232944-2f8f-4102-af90-7505338eb0a5', 'f887b36f-b799-4eb5-93c2-5698d84ee7c0', '5.67');

INSERT INTO transactions (uuid, type, accountuuid, details, amount, inserted_time)
VALUES
('ddaf0048-5596-4e81-9923-0f3f6d4c2de4', 'DEPOSIT', 'd477d4f6-4650-4909-88a6-d004d6825f94',
'opening balance', '45.67', '1578247526435');

