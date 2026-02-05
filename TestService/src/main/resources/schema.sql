CREATE TABLE IF NOT EXISTS Users(
  id uuid PRIMARY KEY,
  firstname VARCHAR(256) NOT NULL,
  lastname VARCHAR(256) NOT NULL,
  age INTEGER,
--  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  is_deleted BOOL DEFAULT FALSE
);

--select * from Users;
--
--select *
--from information_schema.tables
--where table_schema = 'public';