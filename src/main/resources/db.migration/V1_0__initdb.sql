CREATE SEQUENCE users_sequence
    start with 100000
    increment by 1 cache 50;

CREATE TABLE users
(
    id       BIGINT       NOT NULL DEFAULT nextval('users_sequence'),
    username VARCHAR(32)  NOT NULL,
    email    VARCHAR(320) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT users_id_pk primary key (id),
    CONSTRAINT users_username_uq unique (username),
    CONSTRAINT users_email_uq unique (email)
);

CREATE SEQUENCE workout_sequence
    start with 100000
    increment by 1 cache 50;

CREATE TABLE workout
(
    id      BIGINT NOT NULL DEFAULT nextval('workout_sequence'),
    user_id BIGINT NOT NULL,
    date    DATE,
    CONSTRAINT workout_id_pk primary key (id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE SEQUENCE category_sequence
    start with 100000
    increment by 1 cache 50;

CREATE TABLE category
(
    id   BIGINT      NOT NULL DEFAULT nextval('category_sequence'),
    name VARCHAR(32) NOT NULL,
    CONSTRAINT category_id_pk primary key (id)
);


CREATE SEQUENCE exercise_sequence
    start with 100000
    increment by 1 cache 50;

CREATE TABLE exercise
(
    id          BIGINT      NOT NULL DEFAULT nextval('exercise_sequence'),
    name        VARCHAR(32) NOT NULL,
    category_id BIGINT      NOT NULL,
    date        DATE,
    CONSTRAINT exercise_id_pk primary key (id),
    CONSTRAINT category_id_fk FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE workout_exercise
(
    workout_id    BIGINT NOT NULL,
    exercise_id   BIGINT NOT NULL,
    exercise_sets INT,
    exercise_reps INT,
    CONSTRAINT workout_exercise_id_pk primary key (workout_id, exercise_id),
    CONSTRAINT workout_id_fk FOREIGN KEY (workout_id) REFERENCES workout (id),
    CONSTRAINT exercise_id_fk FOREIGN KEY (exercise_id) REFERENCES exercise (id)
);
