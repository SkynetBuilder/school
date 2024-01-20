package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentServiceImpl;

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
    private final StudentServiceImpl studentServiceImpl = new StudentServiceImpl();

    @Test
    void testAddStudent() {
        Student actual = studentServiceImpl.addStudent(new Student(1, "abc", 20));
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testFindStudent() {
        studentServiceImpl.addStudent(new Student(1, "abc", 20));
        Student actual = studentServiceImpl.findStudent(1);
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testEditStudent() {
        studentServiceImpl.addStudent(new Student(2, "bcd", 20));
        Student actual = studentServiceImpl.editStudent(new Student(1, "abc", 20));
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testDeleteStudent() {
        studentServiceImpl.addStudent(new Student(1, "abc", 20));
        Student actual = studentServiceImpl.deleteStudent(1L);
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testFindByAge() {
        studentServiceImpl.addStudent(expected.get(1L));
        studentServiceImpl.addStudent(expected.get(2L));
        studentServiceImpl.addStudent(expected.get(3L));
        studentServiceImpl.addStudent(new Student(4, "fgf", 50));
        assertIterableEquals(expected.values(), studentServiceImpl.findByAge(20));
    }
}
