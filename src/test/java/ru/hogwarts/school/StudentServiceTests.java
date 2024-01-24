package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentServiceImpl;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {
    private final Student EXPECTED_STUDENT = new Student(1, "name", 20);
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl studentServiceImpl;

    @Test
    void testAddStudent() {
        when(studentRepository.save(EXPECTED_STUDENT)).thenReturn(EXPECTED_STUDENT);
        Student actual = studentServiceImpl.addStudent(EXPECTED_STUDENT);
        assertEquals(EXPECTED_STUDENT, actual);
    }

    @Test
    void testFindStudent() {
        when(studentRepository.save(EXPECTED_STUDENT)).thenReturn(EXPECTED_STUDENT);
        studentServiceImpl.addStudent(EXPECTED_STUDENT);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(EXPECTED_STUDENT));
        Student actual = studentServiceImpl.findStudent(1);
        assertEquals(EXPECTED_STUDENT, actual);
    }

    @Test
    void testEditStudent() {
        Student actual = new Student(1, "name", 23);
        when(studentRepository.save(actual)).thenReturn(actual);
        studentServiceImpl.addStudent(actual);
        when(studentRepository.save(EXPECTED_STUDENT)).thenReturn(EXPECTED_STUDENT);
        actual = studentServiceImpl.editStudent(EXPECTED_STUDENT);
        assertEquals(EXPECTED_STUDENT, actual);
    }

    @Test
    void testDeleteStudent() {
        studentServiceImpl.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByColor() {
        List<Student> expectedList = new ArrayList<>(List.of(
                EXPECTED_STUDENT,
                new Student(2, "name1", 20),
                new Student(3, "name2", 20)
        ));
        when(studentRepository.findAll()).thenReturn(expectedList);
        assertIterableEquals(expectedList, studentServiceImpl.findByAge(20));
    }
}
