SELECT concat('DROP TABLE IF EXISTS `', table_name, '`;')
FROM information_schema.tables
WHERE table_schema = 'vst';


SET FOREIGN_KEY_CHECKS=0; --
DROP TABLE IF EXISTS `audio` CASCADE;
DROP TABLE IF EXISTS `brail_character` CASCADE;
DROP TABLE IF EXISTS `course` CASCADE;
DROP TABLE IF EXISTS `course_student` CASCADE;
DROP TABLE IF EXISTS `examination` CASCADE;
DROP TABLE IF EXISTS `examination_step` CASCADE;
DROP TABLE IF EXISTS `student` CASCADE;
DROP TABLE IF EXISTS `teacher` CASCADE;
DROP TABLE IF EXISTS `user` CASCADE;
DROP TABLE IF EXISTS `vst_seq` CASCADE;
DROP TABLE IF EXISTS `databasechangelog` CASCADE;
DROP TABLE IF EXISTS `databasechangeloglock` CASCADE;
SET FOREIGN_KEY_CHECKS=1;
