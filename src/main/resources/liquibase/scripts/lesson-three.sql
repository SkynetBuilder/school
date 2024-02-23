-- liquibase formatted sql

-- changeset vgulenkov:1
CREATE INDEX student_name ON student (name);
CREATE INDEX faculty_name_and_color ON faculty (name, color);