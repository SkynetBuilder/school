package ru.hogwarts.school.controller;

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
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestsWithRestTemplate {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestRestTemplate restTemplate;
    private Student student = new Student();

    @BeforeEach
    void setUp() {
        student.setName("name");
        student.setAge(25);
        studentRepository.deleteAll();
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    void testGetStudentInfo() {
        student = studentRepository.save(student);
        ResponseEntity<Student> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/student/" + student.getId(), Student.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getBody(), student);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testCreateStudent() {
        ResponseEntity<Student> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getBody().getName(), student.getName());
        assertEquals(responseEntity.getBody().getAge(), student.getAge());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testEditStudent() {
        student = studentRepository.save(student);
        Student updatedStudent = new Student("new name", 26);
        HttpEntity<Student> entity = new HttpEntity<>(updatedStudent);
        ResponseEntity<Student> responseEntity = this.restTemplate.exchange("http://localhost:" + port + "/student?id=" + student.getId(),
                HttpMethod.PUT, entity, Student.class);
        assertNotNull(responseEntity);
        assertEquals(updatedStudent.getName(), responseEntity.getBody().getName());
        assertEquals(updatedStudent.getAge(), responseEntity.getBody().getAge());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
    @Test
    void testDeleteStudent(){
        student = studentRepository.save(student);
        HttpEntity<Student> entity = new HttpEntity<>(student);
        ResponseEntity<Student> responseEntity = this.restTemplate.exchange("http://localhost:"+ port + "/student/" + student.getId(),
                HttpMethod.DELETE, entity, Student.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertThat(studentRepository.findById(student.getId())).isEmpty();
    }
    @Test
    void testFindStudents(){
        student = studentRepository.save(student);
        ResponseEntity<Collection> responseEntity = this.restTemplate.getForEntity("http://localhost:"+ port + "/student?ageFrom=" + student.getAge(),
                Collection.class);;
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertTrue(responseEntity.toString().contains(String.valueOf(student.getAge())));
    }
    @Test
    void testAddFaculty(){
        student = studentRepository.save(student);
        Faculty faculty = new Faculty("facultyName", "red");
        student.setFaculty(faculty);
        HttpEntity<Student> entity = new HttpEntity<>(student);
        ResponseEntity<Student> responseEntity = this.restTemplate.exchange("http://localhost:"+ port + "/student/" + student.getId() + "?facultyId=" + 1,
                HttpMethod.PUT, entity, Student.class);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), student);
    }
}
