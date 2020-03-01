//
CREATE PROCEDURE vst.audit_columns(IN param1 VARCHAR(50))
 BEGIN
  EXECUTE IMMEDIATE CONCAT('alter table ', param1  ,' ADD COLUMN (created_on DATETIME)');
  EXECUTE IMMEDIATE CONCAT('alter table ', param1  ,' ADD COLUMN (updated_on DATETIME)');

  EXECUTE IMMEDIATE CONCAT('update ', param1  ,' SET created_on=NOW()');
  EXECUTE IMMEDIATE CONCAT('update ', param1  ,' SET updated_on=NOW()');

  EXECUTE IMMEDIATE CONCAT('alter table ', param1  ,' MODIFY created_on datetime NOT NULL');
  EXECUTE IMMEDIATE CONCAT('alter table ', param1  ,' MODIFY updated_on datetime NOT NULL');
END //

CALL vst.audit_columns('audio') //
CALL vst.audit_columns('brail_character') //
CALL vst.audit_columns('examination') //
CALL vst.audit_columns('examination_step') //
CALL vst.audit_columns('student') //
CALL vst.audit_columns('teacher') //
CALL vst.audit_columns('user') //
CALL vst.audit_columns('course') //

DROP PROCEDURE IF EXISTS vst.audit_columns //
