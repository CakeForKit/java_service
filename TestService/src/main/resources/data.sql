DELETE FROM Users;

INSERT INTO
  Users (id, firstname, lastname, age)
VALUES
  (gen_random_uuid(), 'John', 'Doe', 25),
  (gen_random_uuid(), 'Jane', 'Smith', 30),
  (gen_random_uuid(), 'Bob', 'Johnson', 35),
  (gen_random_uuid(), 'Alice', 'Williams', 28),
  (gen_random_uuid(), 'Charlie', 'Brown', 40),
  (gen_random_uuid(), 'Eva', 'Davis', 22),
  (gen_random_uuid(), 'Frank', 'Miller', 33),
  (gen_random_uuid(), 'Grace', 'Wilson', 27),
  (gen_random_uuid(), 'Henry', 'Moore', 45),
  (gen_random_uuid (), 'Ivy', 'Taylor', 31)
  ;
