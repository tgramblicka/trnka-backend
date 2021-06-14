SET FOREIGN_KEY_CHECKS=0; --

insert into user (id, username, password, type, enabled) values (1, 'admin', '$2a$10$L/jQ4yPglCSben3bFcONROnJfkxq2wL9NKfgo2lsG3p82QjdxT8SO', 'ADMIN', true);
insert into user (id, username, password, type, enabled) values (2, 'ucitel1', '$2a$10$LLa/kCmqV1cBUZdzK5YJte8c.VPWuXXgFeLvGVIdyE./TftILS2ai', 'TEACHER', true);
insert into user (id, username, password, type, enabled) values (3, 'ucitel2', '$2a$10$k8/N3u01.Fv8ft0Kj9TMXOottY0zbWQf12MtcCKIQW9Cl8h1IYKiW', 'TEACHER', true);
insert into user (id, username, password, type, enabled) values (4, 'device', '$2a$10$WzrAFeOouXHCWjbTHdGxWO4nVkO0sPTPw/Omm5Y6p03uD6cgT2PeW', 'TEACHER', true);


insert into authorities (user_id, authority) values (1, 'ADMIN');
insert into authorities (user_id, authority) values (1, 'TEACHER');
insert into authorities (user_id, authority) values (2, 'TEACHER');
insert into authorities (user_id, authority) values (3, 'TEACHER');
insert into authorities (user_id, authority) values (4, 'ADMIN');

insert into teacher (id, user_id) values (1, 1);
insert into teacher (id, user_id) values (3, 2);
insert into teacher (id, user_id) values (2, 3);





INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (1, NULL, '[1]', 'a');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (2, NULL, '[1,2]', 'b');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (3, NULL, '[1,4]', 'c');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (4, NULL, '[1,4,5]', 'd');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (5, NULL, '[1,5]', 'e');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (6, NULL, '[1,2,4]', 'f');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (7, NULL, '[1,2,4,5]', 'g');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (8, NULL, '[1,2,5]', 'h');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (9, NULL, '[2,4]', 'i');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (10, NULL, '[2,4,5]', 'j');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (11, NULL, '[3,4]', 'k');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (12, NULL, '[4,5,6]', 'l');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (13, NULL, '[1,3,4]', 'm');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (14, NULL, '[1,3,4,5]', 'n');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (15, NULL, '[1,3,5]', 'o');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (16, NULL, '[1,2,3,4]', 'p');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (17, NULL, '[1,2,3,4,5]', 'q');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (18, NULL, '[1,2,3,5]', 'r');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (19, NULL, '[2,3,4]', 's');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (20, NULL, '[2,3,4,5]', 't');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (21, NULL, '[1,3,6]', 'u');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (22, NULL, '[1,2,3,6]', 'v');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (23, NULL, '[1,3,4,6]', 'x');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (24, NULL, '[1,3,4,5,6]', 'y');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (25, NULL, '[1,3,5,6]', 'z');

INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (27, NULL, '[1,6]', 'á');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (28, NULL, '[4]', 'ä');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (29, NULL, '[1,4,6]', 'č');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (30, NULL, '[1,4,5,6]', 'ď');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (31, NULL, '[3,4,5]', 'é');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (32, NULL, '[3,4]', 'í');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (33, NULL, '[4,6]', 'ľ');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (34, NULL, '[4,5,6]', 'ľ'); -- makke l
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (35, NULL, '[1,2,4,6]', 'ň');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (36, NULL, '[2,4,6]', 'ó');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (37, NULL, '[2,3,4,5,6]', 'ô');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (38, NULL, '[1,2,3,5,6]', 'ŕ');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (39, NULL, '[1,5,6]', 'š');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (40, NULL, '[1,2,5,6]', 'ť');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (41, NULL, '[3,4,6]', 'ú');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (42, NULL, '[1,2,3,4,6]', 'ý');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (43, NULL, '[2,3,4,6]', 'ž');

INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (44, NULL, '[3,4,5,6]', 'čz'); -- čiselny znak
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (45, NULL, '[1,2,3,4,5,6]', 'pz');  -- plny znak
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (46, NULL, '[6]', 'vp');  -- znak velke pismeno
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (47, NULL, '[2]', ',');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (48, NULL, '[2,5,6]', '.');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (49, NULL, '[2,3,5]', '!');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (50, NULL, '[3,6]', '-');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (51, NULL, '[2,5]', ':');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (52, NULL, '[2,3,5,6]', '"');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (53, NULL, '[3]', 'apostrof');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (54, NULL, '[2,3,6]', '(');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (55, NULL, '[3,5,6]', ')');
INSERT INTO `brail_character` (`id`, `audio_id`, `brail_representation`, `letter`) VALUES (56, NULL, '[3,5]', '*');

SET FOREIGN_KEY_CHECKS=1; --
