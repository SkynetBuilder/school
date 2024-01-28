package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student addStudent(Student student);

    Student findStudent(long id);

    Student editStudent(long id, Student student);
    Student addFaculty(long studentId, long facultyId);

    void deleteStudent(long id);

    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);
}
