package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class FacultyServiceTests {
    private Map<Long, Faculty> expected = new HashMap<>(Map.of(
            1L, new Faculty(1, "abc", "red"),
            2L, new Faculty(2, "cba", "red"),
            3L, new Faculty(3, "abc", "red")
    ));
    private final FacultyService FacultyService = new FacultyService();

    @Test
    void testAddFaculty() {
        Faculty actual = FacultyService.addFaculty(new Faculty(1, "abc", "red"));
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testFindFaculty() {
        FacultyService.addFaculty(new Faculty(1, "abc", "red"));
        Faculty actual = FacultyService.findFaculty(1);
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testEditFaculty() {
        FacultyService.addFaculty(new Faculty(2, "bcd", "blue"));
        Faculty actual = FacultyService.editFaculty(new Faculty(1, "abc", "red"));
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testDeleteFaculty() {
        FacultyService.addFaculty(new Faculty(1, "abc", "red"));
        Faculty actual = FacultyService.deleteFaculty(1L);
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testFindByColor() {
        FacultyService.addFaculty(expected.get(1L));
        FacultyService.addFaculty(expected.get(2L));
        FacultyService.addFaculty(expected.get(3L));
        FacultyService.addFaculty(new Faculty(4, "fgf", "blue"));
        assertIterableEquals(expected.values(), FacultyService.findByColor("red"));
    }
}
