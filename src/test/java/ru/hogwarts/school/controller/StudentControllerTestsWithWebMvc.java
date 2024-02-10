package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTestsWithWebMvc {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StudentService studentService;

    private final Student student = new Student();

    @BeforeEach
    void setUp() {
        student.setName("name");
        student.setAge(25);
        student.setId(1);
        student.setFaculty(new Faculty("name", "color"));
    }

    @Test
    void testGetStudentInfo() throws Exception {
        when(studentService.findStudent(student.getId())).thenReturn(student);
        mockMvc.perform(get("/student/{id}", student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void testCreateStudent() throws Exception {
        when(studentService.addStudent(student)).thenReturn(student);
        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void testEditStudent() throws Exception {
        when(studentService.editStudent(student.getId(), student)).thenReturn(student);
        mockMvc.perform(put("/student")
                        .param("id", String.valueOf(student.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void testDeleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent(student.getId());
        mockMvc.perform(delete("/student/{id}", student.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void findStudents() throws Exception {
        when(studentService.findByAge(student.getAge())).thenReturn(List.of(student));
        mockMvc.perform(get("/student")
                        .param("ageFrom", String.valueOf(student.getAge()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void testAddFaculty() throws Exception {
        when(studentService.addFaculty(student.getId(), student.getFaculty().getId())).thenReturn(student);
        when(studentService.findStudent(student.getId())).thenReturn(student);
        mockMvc.perform(put("/student/{studentId}", student.getId())
                        .param("facultyId", String.valueOf(student.getFaculty().getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andExpect(jsonPath("$.faculty.id").value(student.getFaculty().getId()))
                .andExpect(jsonPath("$.faculty.name").value(student.getFaculty().getName()))
                .andExpect(jsonPath("$.faculty.color").value(student.getFaculty().getColor()))
                .andDo(print());
    }
}
