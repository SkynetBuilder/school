package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class StudentServiceTests {
    private Map<Long, Student> expected = new HashMap<>(Map.of(
            1L, new Student(1, "abc", 20),
            2L, new Student(2, "cba", 20),
            3L, new Student(3, "abc", 20)
    ));
    private final StudentService studentService = new StudentService();

    @Test
    void testAddStudent() {
        Student actual = studentService.addStudent(new Student(1, "abc", 20));
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testFindStudent() {
        studentService.addStudent(new Student(1, "abc", 20));
        Student actual = studentService.findStudent(1);
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testEditStudent() {
        studentService.addStudent(new Student(2, "bcd", 20));
        Student actual = studentService.editStudent(new Student(1, "abc", 20));
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testDeleteStudent() {
        studentService.addStudent(new Student(1, "abc", 20));
        Student actual = studentService.deleteStudent(1L);
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testFindByAge() {
        studentService.addStudent(expected.get(1L));
        studentService.addStudent(expected.get(2L));
        studentService.addStudent(expected.get(3L));
        studentService.addStudent(new Student(4, "fgf", 50));
        assertIterableEquals(expected.values(), studentService.findByAge(20));
    }
}
