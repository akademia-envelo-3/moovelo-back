INSERT INTO API_USER(EMAIL, FIRSTNAME, LASTNAME, PASSWORD, ROLE)
VALUES ('jan.bananek@test.pl', 'Jan', 'Bananek', 'okon', 'ROLE_USER'),
       ('tom.wisnia@test.pl', 'Tom', 'Wisnia', 'okon2', 'ROLE_USER'),
       ('user@test.pl', 'user', 'user', 'user', 'ROLE_USER'),
       ('admin@test.pl', 'admin', 'admin', 'admin', 'ROLE_ADMIN');

INSERT INTO BASIC_USER(ID)
VALUES ( 1 ), ( 2 ), ( 3 );

INSERT INTO ADMIN(ID)
VALUES ( 4 );

/*INSERT INTO API_USER_ROLES(API_USER_ID, ROLES)
VALUES ( 1, 'ROLE_USER' ), ( 2, 'ROLE_USER' ),( 3, 'ROLE_USER' ),( 4, 'ROLE_ADMIN' );*/