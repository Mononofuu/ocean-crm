CREATE TABLE currency (
  id   BIGINT GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1 ) PRIMARY KEY,
  code VARCHAR(10),
  name VARCHAR(45)
);
