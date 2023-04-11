insert into authority (permission)
values ('product.create'),
       ('product.read'),
       ('product.update'),
       ('product.delete');

insert into account_role (name)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

insert into role_authority (role_id, authority_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 2);

