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
    private final Faculty EXPECTED_FACULTY = new Faculty("name", "red");
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
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(EXPECTED_FACULTY));
        when(facultyRepository.save(EXPECTED_FACULTY)).thenReturn(EXPECTED_FACULTY);
        Faculty actual = facultyServiceImpl.editFaculty(1,EXPECTED_FACULTY);
        assertEquals(EXPECTED_FACULTY, actual);
    }

    @Test
    void testDeleteFaculty() {
        facultyServiceImpl.deleteFaculty(1L);
        verify(facultyRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByNameOrColor() {
        List<Faculty> expectedList = new ArrayList<>(List.of(
                EXPECTED_FACULTY
        ));
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(any(), any())).thenReturn(expectedList);
        assertEquals(expectedList, facultyServiceImpl.findByNameOrColor("name","red"));
    }
}
