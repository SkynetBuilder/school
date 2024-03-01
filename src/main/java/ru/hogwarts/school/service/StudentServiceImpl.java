package ru.hogwarts.school.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;
    private final FacultyService facultyService;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyService facultyService) {
        this.studentRepository = studentRepository;
        this.facultyService = facultyService;
    }


    @Override
    public Student addStudent(Student student) {
        logger.info(("Was invoked method to create student"));
        studentRepository.save(student);
        logger.info(student + " is saved");
        return student;
    }

    @Override
    public Student findStudent(long id) {
        logger.info(("Was invoked method to find student with id " + id));
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student editStudent(long id, Student student) {
        logger.info(("Was invoked method to edit student with id " + id));
        return studentRepository.findById(id).map(foundStudent -> {
            foundStudent.setName(student.getName());
            foundStudent.setAge(student.getAge());
            studentRepository.save(foundStudent);
            logger.info(foundStudent + " is edited");
            return foundStudent;
        }).orElse(null);
    }

    @Override
    public Student addFaculty(long studentId, long facultyId) {
        logger.info(("Was invoked method to add faculty to student"));
        Student student = findStudent(studentId);
        if (student != null) {
            student.setFaculty(facultyService.findFaculty(facultyId));
        } else return null;
        studentRepository.save(student);
        logger.info("Faculty added");
        return student;
    }

    @Override
    public void deleteStudent(long id) {
        logger.info(("Was invoked method to delete student with id " + id));
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> findByAge(int age) {
        logger.info(("Was invoked method to find students by age"));
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info(("Was invoked method to find students by age between " + min + " and " + max));
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Long getStudentCount() {
        logger.info(("Was invoked method to get student count"));
        return studentRepository.getStudentCount();
    }

    @Override
    public Integer getAverageAge() {
        logger.info(("Was invoked method to get average age of students"));
        return studentRepository.getAverageAge();
    }

    @Override
    public Collection<Student> getLastFiveStudents() {
        logger.info(("Was invoked method to get five last students"));
        return studentRepository.getLastFiveStudents();
    }

    @Override
    public Collection<String> getStudentsByFirstLetterInName() {
        logger.info(("Was invoked method to get students with the first letter А in the name"));
        return studentRepository.findAll().parallelStream().map(Student::getName).map(String::toUpperCase).filter(studentName -> studentName.startsWith("А")).sorted().toList();
    }

    @Override
    public Integer getAverageAgeWithStream() {
        logger.info(("Was invoked method to get average age of students using StreamAPI"));
        Double ageAsDouble = studentRepository.findAll().parallelStream().mapToInt(Student::getAge).average().getAsDouble();
        return ageAsDouble.intValue();
//        Возможно, есть решение лучше и быстрее
    }

    @Override
    public void studentPrintParallel() {
        logger.info("Was invoked method to print out 6 students in parallel threads");
        print(0);
        print(1);
        new Thread(() -> {
            print(2);
            print(3);
        }).start();
        new Thread(() -> {
            print(4);
            print(5);
        }).start();
    }

    @Override
    public void studentPrintSync() {
        logger.info("Was invoked method to print out 6 students in synchronized threads");
        print(0);
        print(1);
        new Thread(() -> {
            printSync(2);
            printSync(3);
        }).start();
        new Thread(() -> {
            printSync(4);
            printSync(5);
        }).start();
    }

    private void print(int i) {
        System.out.println(studentRepository.findAll().get(i));
    }

    private synchronized void printSync(int i) {
        print(i);
    }
}