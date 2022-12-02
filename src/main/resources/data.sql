INSERT INTO authorities (authority)
VALUES ('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO users (firstname, lastname, age, username, password, enabled)
VALUES (
           'Ivan',
           'Ivanov',
           35,
           'admin@admin.ru',
           '$2a$10$0B7KIqpVdLqT0wgfujpX8eoVm.J1D2XoWgcsq8vHVb7pJn.15pq4y',
           true);

INSERT INTO users_role_set (user_id, role_set_id)
VALUES (1, 1);