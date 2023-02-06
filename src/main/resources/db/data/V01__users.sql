INSERT INTO API_USER(EMAIL, FIRSTNAME, LASTNAME, PASSWORD)
VALUES ('jan.bananek@test.pl', 'Jan', 'Bananek', '$2a$12$i2O8iXE2JrI2pBjXUnIaBuXHQpsUjFYX/eSn8hqPo12cunTxvlF.K'),
       ('tom.wisnia@test.pl', 'Tom', 'Wisnia', '$2a$12$pU2K4auE/gKofmnKQaMAKO01WVPsFgV1NMg9s.jtX3M7pLQSmYv5y'),
       ('user@test.pl', 'user', 'user', '$2a$12$Na19El0lbNzxaeI4Unnf1.b2B0U2QVQm5CcVwfu6yyij2levWKS1m'),
       ('admin@test.pl', 'admin', 'admin', '$2a$12$jE7o7CbR.m8QkqrF/oE0oOlZaYxmkcFK8ohu9wGp8e1GYq5XDCwvK');

INSERT INTO API_USER_ROLES(API_USER_ID, ROLES)
VALUES ( 1, 'ROLE_USER' ), ( 2, 'ROLE_USER' ),( 3, 'ROLE_USER' ),( 4, 'ROLE_ADMIN' );

INSERT INTO BASIC_USER(ID)
VALUES ( 1 ), ( 2 ), ( 3 );

INSERT INTO ADMIN(ID)
VALUES ( 4 );