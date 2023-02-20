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

INSERT INTO BASIC_USER_SURVEY_ANSWERS(USER_ID, ANSWER_ID)
VALUES (1, 1),
       (1, 3),
       (1, 4),
       (2, 2),
       (2, 3),
       (2, 5);


