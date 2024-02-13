package ru.hogwarts.school.service;

import java.util.*;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final FacultyService facultyService;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyService facultyService) {
        this.studentRepository = studentRepository;
        this.facultyService = facultyService;
    }


    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student editStudent(long id, Student student) {
        return studentRepository.findById(id)
                .map(foundStudent -> {
                    foundStudent.setName(student.getName());
                    foundStudent.setAge(student.getAge());
                    return studentRepository.save(foundStudent);
                }).orElse(null);
    }

    @Override
    public Student addFaculty(long studentId, long facultyId) {
        Student student = findStudent(studentId);
        if (student != null){
        student.setFaculty(facultyService.findFaculty(facultyId));
        } else return null;
        return student;
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }
}