package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestParam Long id, @RequestBody Student student) {
        Student foundStudent = studentService.editStudent(id, student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(required = false) Integer ageFrom,
                                                            @RequestParam(required = false) Integer ageTo) {
        if (ageFrom > 0) {
            if (ageTo != null) {
                return ResponseEntity.ok(studentService.findByAgeBetween(ageFrom, ageTo));
            }
            return ResponseEntity.ok(studentService.findByAge(ageFrom));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    //Если второй параметр не вносится, то ищутся студенты с указанным в первом параметре возрастом
    @PutMapping("{studentId}")
    public ResponseEntity<Student> addFaculty(@PathVariable Long studentId, @RequestParam Long facultyId) {
        if (studentService.findStudent(studentId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.addFaculty(studentId, facultyId));
    }

    @GetMapping("student-count")
    public Long getStudentCount() {
        return studentService.getStudentCount();
    }

    @GetMapping("average-age")
    public Integer getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("last-5-students")
    public Collection<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("students-by-first-letter")
    public Collection<String> getStudentsByFirstLetterInName() {
        return studentService.getStudentsByFirstLetterInName();
    }

    @GetMapping("average-age-with-stream")
    public Integer getAverageAgeWithStream() {
        return studentService.getAverageAgeWithStream();
    }

    @GetMapping("print-parallel")
    public void printParallel() {
        studentService.studentPrintParallel();
    }

    @GetMapping("print-synchronized")
    public void printSynchronized() {
        studentService.studentPrintSync();
    }
}
