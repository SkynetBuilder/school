package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyServiceImpl;

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
    private final FacultyServiceImpl FacultyServiceImpl = new FacultyServiceImpl();

    @Test
    void testAddFaculty() {
        Faculty actual = FacultyServiceImpl.addFaculty(new Faculty(1, "abc", "red"));
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testFindFaculty() {
        FacultyServiceImpl.addFaculty(new Faculty(1, "abc", "red"));
        Faculty actual = FacultyServiceImpl.findFaculty(1);
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testEditFaculty() {
        FacultyServiceImpl.addFaculty(new Faculty(2, "bcd", "blue"));
        Faculty actual = FacultyServiceImpl.editFaculty(new Faculty(1, "abc", "red"));
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testDeleteFaculty() {
        FacultyServiceImpl.addFaculty(new Faculty(1, "abc", "red"));
        Faculty actual = FacultyServiceImpl.deleteFaculty(1L);
        assertEquals(expected.get(1L), actual);
    }

    @Test
    void testFindByColor() {
        FacultyServiceImpl.addFaculty(expected.get(1L));
        FacultyServiceImpl.addFaculty(expected.get(2L));
        FacultyServiceImpl.addFaculty(expected.get(3L));
        FacultyServiceImpl.addFaculty(new Faculty(4, "fgf", "blue"));
        assertIterableEquals(expected.values(), FacultyServiceImpl.findByColor("red"));
    }
}
