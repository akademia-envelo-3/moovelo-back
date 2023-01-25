INSERT INTO API_USER(EMAIL, FIRSTNAME, LASTNAME, LOGIN, PASSWORD, ROLE)
VALUES ( 'jan.bananek@test.pl', 'Jan', 'Bananek', 'JBananek', 'okon', 'BASIC_USER');

INSERT INTO BASIC_USER(ID) VALUES ( 1 );

INSERT INTO CATEGORY(NAME, VISIBLE) VALUES ('Sport', true);

INSERT INTO LOCATION(ALTITUDE, APARTMENT_NUMBER, CITY,LATITUDE, POSTCODE, STREET, STREET_NUMBER)
VALUES (0.0, '5A', 'Warsaw', 0.0, '92-100', 'Al. Jerozolimskie', '142');

INSERT INTO EVENT_INFO(DESCRIPTION, IS_CONFIRMATION_REQUIRED, NAME, START_DATE, CATEGORY_ID, LOCATION_ID)
VALUES ('Testowy opis', false, 'Testowy event', {ts '2012-09-17 18:47:52.69'}, 1, 1);

INSERT INTO EVENT_INFO(DESCRIPTION, IS_CONFIRMATION_REQUIRED, NAME, START_DATE, CATEGORY_ID, LOCATION_ID)
VALUES ('Internal', false, 'Testowy Internal event', {ts '2012-09-17 18:47:52.69'}, 1, 1);

INSERT INTO EVENT_INFO(DESCRIPTION, IS_CONFIRMATION_REQUIRED, NAME, START_DATE, CATEGORY_ID, LOCATION_ID)
VALUES ('Cyclic', false, 'Testowy Cyclic event', {ts '2012-09-17 18:47:52.69'}, 1, 1);

INSERT INTO EVENT_INFO(DESCRIPTION, IS_CONFIRMATION_REQUIRED, NAME, START_DATE, CATEGORY_ID, LOCATION_ID)
VALUES ('Internal', false, 'Testowy External event', {ts '2012-09-17 18:47:52.69'}, 1, 1);

INSERT INTO EVENT_OWNER(BASIC_USER_ID) VALUES ( 1 );

INSERT INTO EVENT(EVENT_TYPE, LIMITED_PLACES, EVENT_INFO_ID, EVENT_OWNER_ID)
VALUES ('EVENT', 0, 1, 1);

INSERT INTO EVENT(EVENT_TYPE, LIMITED_PLACES, EVENT_INFO_ID, EVENT_OWNER_ID)
VALUES ('INTERNAL_EVENT', 0, 2, 1);

INSERT INTO INTERNAL_EVENT(IS_PRIVATE, ID, GROUP_ID)
VALUES ( FALSE, 2, NULL );

INSERT INTO EVENT(EVENT_TYPE, LIMITED_PLACES, EVENT_INFO_ID, EVENT_OWNER_ID)
VALUES ('CYCLIC_EVENT', 0, 3, 1);

INSERT INTO INTERNAL_EVENT(IS_PRIVATE, ID, GROUP_ID)
VALUES ( FALSE, 3, NULL );

INSERT INTO CYCLIC_EVENT(FREQUENCY_IN_DAYS, NUMBER_OF_REPEATS, ID)
VALUES ( 2, 4, 3 );

INSERT INTO EVENT(EVENT_TYPE, LIMITED_PLACES, EVENT_INFO_ID, EVENT_OWNER_ID)
VALUES ('EXTERNAL_EVENT', 0, 4, 1);

INSERT INTO EXTERNAL_EVENT(ID) VALUES (4);