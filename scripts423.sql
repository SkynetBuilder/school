SELECT student.name, student.age, faculty.name AS faculty_name
FROM student
LEFT JOIN faculty ON student.faculty_id = faculty.id;
select student.name, student.age FROM avatar
INNER JOIN student ON avatar.student_id = student.id;