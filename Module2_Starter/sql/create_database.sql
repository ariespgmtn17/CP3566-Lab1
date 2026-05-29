-- ---------------------------------------------------------------------
-- CP3566 Module 2 — student table schema + seed data
-- ---------------------------------------------------------------------
--
-- With H2 embedded you do NOT need to "CREATE DATABASE" separately.
-- H2 creates ./data/studentdb.mv.db automatically the first time any
-- Java program in this project opens a JDBC connection to it.
--
-- This file is the SQL equivalent of running SchemaSetup.java.
-- Use it when you want to inspect or reset the schema from the H2
-- web console without recompiling Java.
--
-- How to run it:
--   1. Start the H2 web console:
--        java -cp .mvn/repo/com/h2database/h2/2.3.232/h2-2.3.232.jar \
--             org.h2.tools.Console
--   2. Browser opens at http://localhost:8082. Log in with:
--        JDBC URL : jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE
--        User     : sa
--        Password : secret
--   3. Paste this file into the SQL pane and click "Run".
-- ---------------------------------------------------------------------

DROP TABLE IF EXISTS student;

CREATE TABLE student (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(80)  NOT NULL,
    program VARCHAR(12)  NOT NULL,
    gpa     DECIMAL(3,2) NOT NULL
);

INSERT INTO student (name, program, gpa) VALUES
    ('Alice Park', 'CS', 3.92),
    ('Bob Mercer', 'SE', 3.45),
    ('Cara Singh', 'IT', 3.78),
    ('Dana Luo',   'DA', 3.61);
