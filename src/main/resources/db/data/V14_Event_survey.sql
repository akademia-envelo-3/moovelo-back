INSERT INTO EVENT_SURVEY(IS_MULTIPLE_CHOICE, QUESTION, EVENT_ID)
VALUES (true, 'Pytanie nr 1', 1),
       (false, 'Pytanie nr 2', 1);

INSERT INTO ANSWER(ANSWER_VALUE, EVENT_SURVEY_ID)
VALUES ('Odpowiedź do pyt. 1 nr 1', 1),
       ('Odpowiedź do pyt. 1 nr 2', 1),
       ('Odpowiedź do pyt. 1 nr 3', 1),

       ('Odpowiedź do pyt. 2 nr 1', 2),
       ('Odpowiedź do pyt. 2 nr 2', 2),
       ('Odpowiedź do pyt. 2 nr 3', 2);

INSERT INTO ANSWERS_BASIC_USERS(ANSWER_ID, USER_ID)
VALUES (1, 3),
       (3, 3),
       (4, 3),
       (2, 2),
       (3, 2),
       (5, 2);


