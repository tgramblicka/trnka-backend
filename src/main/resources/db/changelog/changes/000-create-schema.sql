CREATE TABLE audio (id BIGINT NOT NULL, filename VARCHAR(255) NULL, CONSTRAINT PK_AUDIO PRIMARY KEY (id));

CREATE TABLE brail_character (id BIGINT NOT NULL, brail_representation LONGTEXT NULL, letter VARCHAR(255) NULL, audio_id BIGINT DEFAULT NULL NULL, CONSTRAINT PK_BRAIL_CHARACTER PRIMARY KEY (id));

CREATE TABLE course (id BIGINT NOT NULL, name VARCHAR(255) NULL, teacher_id BIGINT DEFAULT NULL NULL, CONSTRAINT PK_COURSE PRIMARY KEY (id));

CREATE TABLE examination (id BIGINT NOT NULL, course_id BIGINT NOT NULL, allowed_retries INT DEFAULT NULL NULL, audio VARCHAR(255) NULL, complexity BIGINT DEFAULT NULL NULL, name VARCHAR(255) NULL, timeout BIGINT DEFAULT NULL NULL, type INT DEFAULT NULL NULL, CONSTRAINT PK_EXAMINATION PRIMARY KEY (id));

CREATE TABLE examination_step (id BIGINT NOT NULL, preserve_order BIT DEFAULT 0 NULL, brail_character_id BIGINT NOT NULL, examination_id BIGINT DEFAULT NULL NULL, CONSTRAINT PK_EXAMINATION_STEP PRIMARY KEY (id));

CREATE TABLE student (id BIGINT NOT NULL, device_identification_code VARCHAR(255) NULL, course_id BIGINT NOT NULL, CONSTRAINT PK_STUDENT PRIMARY KEY (id));

CREATE TABLE teacher (id BIGINT NOT NULL, user_id BIGINT DEFAULT NULL NULL, CONSTRAINT PK_TEACHER PRIMARY KEY (id));

CREATE TABLE user (id BIGINT NOT NULL, password VARCHAR(255) NULL, username VARCHAR(255) NULL, CONSTRAINT PK_USER PRIMARY KEY (id));

create sequence vst_seq start with 100 minvalue 100 maxvalue 9223372036854775806 increment by 1 cache 20
            nocycle;


CREATE INDEX FK1bsjbolbkeidctoruif7i3b6k ON brail_character(audio_id);
CREATE INDEX FK38jgjyongm0ve81vkf0gnvmku ON examination_step(brail_character_id);
CREATE INDEX FKpb6g6pahj1mr2ijg92r7m1xlh ON teacher(user_id);
CREATE INDEX FKrg7nxjlvm7ak0gvuhwgr96v21 ON examination_step(examination_id);
CREATE INDEX FKsybhlxoejr4j3teomm5u2bx1n ON course(teacher_id);
CREATE INDEX FK_examination_course_id ON examination(course_id);
CREATE INDEX FK_student_course_id ON student(course_id);

ALTER TABLE brail_character ADD CONSTRAINT FK1bsjbolbkeidctoruif7i3b6k FOREIGN KEY (audio_id) REFERENCES audio (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE examination_step ADD CONSTRAINT FK38jgjyongm0ve81vkf0gnvmku FOREIGN KEY (brail_character_id) REFERENCES brail_character (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE teacher ADD CONSTRAINT FKpb6g6pahj1mr2ijg92r7m1xlh FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE examination_step ADD CONSTRAINT FKrg7nxjlvm7ak0gvuhwgr96v21 FOREIGN KEY (examination_id) REFERENCES examination (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE course ADD CONSTRAINT FKsybhlxoejr4j3teomm5u2bx1n FOREIGN KEY (teacher_id) REFERENCES teacher (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE examination ADD CONSTRAINT FK_examination_course_id FOREIGN KEY (course_id) REFERENCES course (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE student ADD CONSTRAINT FK_student_course_id FOREIGN KEY (course_id) REFERENCES course (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

