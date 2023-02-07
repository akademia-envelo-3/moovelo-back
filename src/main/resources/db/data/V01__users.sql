INSERT INTO API_USER(EMAIL, FIRSTNAME, LASTNAME, PASSWORD)
VALUES ('jan.bananek@test.pl', 'Jan', 'Bananek', 'okon'),
       ('tom.wisnia@test.pl', 'Tom', 'Wisnia', 'okon2'),
       ('user@test.pl', 'user', 'user', 'user'),
       ('admin@test.pl', 'admin', 'admin', 'admin');

INSERT INTO API_USER_ROLES(API_USER_ID, ROLES)
VALUES ( 1, 'ROLE_USER' ), ( 2, 'ROLE_USER' ),( 3, 'ROLE_USER' ),( 4, 'ROLE_ADMIN' );

INSERT INTO BASIC_USER(ID)
VALUES ( 1 ), ( 2 ), ( 3 );

INSERT INTO ADMIN(ID)
VALUES ( 4 );