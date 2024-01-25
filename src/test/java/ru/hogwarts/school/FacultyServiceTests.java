package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTests {
    private final Faculty EXPECTED_FACULTY = new Faculty(1, "name", "red");
    @Mock
    private FacultyRepository facultyRepository;
    @InjectMocks
    private FacultyServiceImpl facultyServiceImpl;

    @Test
    void testAddFaculty() {
        when(facultyRepository.save(EXPECTED_FACULTY)).thenReturn(EXPECTED_FACULTY);
        Faculty actual = facultyServiceImpl.addFaculty(EXPECTED_FACULTY);
        assertEquals(EXPECTED_FACULTY, actual);
    }

    @Test
    void testFindFaculty() {
        when(facultyRepository.save(EXPECTED_FACULTY)).thenReturn(EXPECTED_FACULTY);
        facultyServiceImpl.addFaculty(EXPECTED_FACULTY);
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(EXPECTED_FACULTY));
        Faculty actual = facultyServiceImpl.findFaculty(1);
        assertEquals(EXPECTED_FACULTY, actual);
    }

    @Test
    void testEditFaculty() {
        Faculty actual = new Faculty(1, "name", "blue");
        when(facultyRepository.save(actual)).thenReturn(actual);
        facultyServiceImpl.addFaculty(actual);
        when(facultyRepository.save(EXPECTED_FACULTY)).thenReturn(EXPECTED_FACULTY);
        actual = facultyServiceImpl.editFaculty(EXPECTED_FACULTY);
        assertEquals(EXPECTED_FACULTY, actual);
    }

    @Test
    void testDeleteFaculty() {
        facultyServiceImpl.deleteFaculty(1L);
        verify(facultyRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByColor() {
        List<Faculty> expectedList = new ArrayList<>(List.of(
                EXPECTED_FACULTY,
                new Faculty(2, "name1", "red"),
                new Faculty(3, "name2", "red")
        ));
        when(facultyRepository.findAll()).thenReturn(expectedList);
        assertIterableEquals(expectedList, facultyServiceImpl.findByColor("red"));
    }
}
