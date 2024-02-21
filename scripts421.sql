ALTER TABLE student
ADD CONSTRAINT age_constraint CHECK (age > 0),
ALTER COLUMN name SET NOT NULL,
ADD CONSTRAINT name_unique UNIQUE (name),
ALTER age SET DEFAULT 20;
ALTER table faculty
ADD CONSTRAINT name_color_unique UNIQUE (name, color);