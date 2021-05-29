CREATE TABLE audio (id BIGINT NOT NULL, filename VARCHAR(255) NULL, CONSTRAINT PK_AUDIO PRIMARY KEY (id));

CREATE TABLE brail_character (id BIGINT NOT NULL, brail_representation LONGTEXT NULL, letter VARCHAR(255) NULL, audio_id BIGINT DEFAULT NULL NULL, CONSTRAINT PK_BRAIL_CHARACTER PRIMARY KEY (id));

CREATE TABLE course (id BIGINT NOT NULL, name VARCHAR(255) NULL, teacher_id BIGINT DEFAULT NULL NULL, CONSTRAINT PK_COURSE PRIMARY KEY (id));

CREATE TABLE examination (id BIGINT NOT NULL, course_id BIGINT NOT NULL, allowed_retries INT DEFAULT NULL NULL, audio VARCHAR(255) NULL, passing_rate_percentage DECIMAL(4,2) DEFAULT NULL,
name VARCHAR(255) NULL, timeout BIGINT DEFAULT NULL NULL, type INT DEFAULT NULL NULL, CONSTRAINT PK_EXAMINATION PRIMARY KEY (id));

CREATE TABLE examination_step (id BIGINT NOT NULL, preserve_order BIT DEFAULT 0 NULL, brail_character_id BIGINT NOT NULL, examination_id BIGINT DEFAULT NULL NULL, CONSTRAINT PK_EXAMINATION_STEP PRIMARY KEY (id));

CREATE TABLE student (id BIGINT NOT NULL, device_identification_code VARCHAR(255) NULL, device_login_count INT NULL, course_id BIGINT, CONSTRAINT PK_STUDENT PRIMARY KEY (id));

CREATE TABLE teacher (id BIGINT NOT NULL, user_id BIGINT DEFAULT NULL NULL, CONSTRAINT PK_TEACHER PRIMARY KEY (id));

CREATE TABLE user (id BIGINT NOT NULL, password VARCHAR(255) NULL, username VARCHAR(50) NOT NULL, type VARCHAR(50) NOT NULL, enabled BOOLEAN NOT NULL, CONSTRAINT PK_USER PRIMARY KEY (id));

CREATE TABLE authorities (
  user_id BIGINT NOT NULL,
  authority VARCHAR(50) NOT NULL
);

create sequence vst_seq start with 100 minvalue 100 maxvalue 9223372036854775806 increment by 1 cache 20
            nocycle;


ALTER TABLE brail_character ADD CONSTRAINT FK_brail_character_audio_id FOREIGN KEY (audio_id) REFERENCES audio (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE examination_step ADD CONSTRAINT FK_examination_step_brail_character_id FOREIGN KEY (brail_character_id) REFERENCES brail_character (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE teacher ADD CONSTRAINT FK_teacher_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE examination_step ADD CONSTRAINT FK_examination_step_examination_id FOREIGN KEY (examination_id) REFERENCES examination (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE course ADD CONSTRAINT FK_course_teacher_id FOREIGN KEY (teacher_id) REFERENCES teacher (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE examination ADD CONSTRAINT FK_examination_course_id FOREIGN KEY (course_id) REFERENCES course (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE student ADD CONSTRAINT FK_student_course_id FOREIGN KEY (course_id) REFERENCES course (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE user ADD CONSTRAINT UQ_user_username UNIQUE (username);
ALTER TABLE authorities ADD CONSTRAINT FK_authorities_user FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE RESTRICT;


