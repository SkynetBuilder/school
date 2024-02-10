package ru.hogwarts.school.controller;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestsWithRestTemplate {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private TestRestTemplate restTemplate;
    private Faculty faculty = new Faculty();

    @BeforeEach
    void setUp() {
        faculty.setName("name");
        faculty.setColor("color");
        facultyRepository.deleteAll();
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(facultyController).isNotNull();
    }

    @Test
    void testGetFacultyInfo() {
        faculty = facultyRepository.save(faculty);
        ResponseEntity<Faculty> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getBody(), faculty);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testCreateFaculty() {
        ResponseEntity<Faculty> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getBody().getName(), faculty.getName());
        assertEquals(responseEntity.getBody().getColor(), faculty.getColor());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testEditFaculty() {
        faculty = facultyRepository.save(faculty);
        Faculty updatedFaculty = new Faculty("new name", "new color");
        HttpEntity<Faculty> entity = new HttpEntity<>(updatedFaculty);
        ResponseEntity<Faculty> responseEntity = this.restTemplate.exchange("http://localhost:" + port + "/faculty?id=" + faculty.getId(),
                HttpMethod.PUT, entity, Faculty.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getBody().getName(), updatedFaculty.getName());
        assertEquals(responseEntity.getBody().getColor(), updatedFaculty.getColor());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
    @Test
    void testDeleteFaculty(){
        faculty = facultyRepository.save(faculty);
        HttpEntity<Faculty> entity = new HttpEntity<>(faculty);
        ResponseEntity<Faculty> responseEntity = this.restTemplate.exchange("http://localhost:"+ port + "/faculty/" + faculty.getId(),
                HttpMethod.DELETE, entity, Faculty.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertThat(facultyRepository.findById(faculty.getId())).isEmpty();
    }
    @Test
    void testFindFaculties(){
        faculty = facultyRepository.save(faculty);
        ResponseEntity<Collection> responseEntity = this.restTemplate.getForEntity("http://localhost:"+ port + "/faculty?name=" + faculty.getName(),
                 Collection.class);;
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertTrue(responseEntity.toString().contains(faculty.getName()));
    }
}
