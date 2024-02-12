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
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTestsWithWebMvc {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FacultyService facultyService;

    private final Faculty faculty = new Faculty();

    @BeforeEach
    void setUp() {
        faculty.setName("name");
        faculty.setColor("color");
        faculty.setId(1);
    }

    @Test
    void testGetFacultyInfo() throws Exception {
        when(facultyService.findFaculty(faculty.getId())).thenReturn(faculty);
        mockMvc.perform(get("/faculty/{id}", faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

    @Test
    void testCreateFaculty() throws Exception {
        when(facultyService.addFaculty(faculty)).thenReturn(faculty);
        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

    @Test
    void testEditFaculty() throws Exception {
        when(facultyService.editFaculty(faculty.getId(), faculty)).thenReturn(faculty);
        mockMvc.perform(put("/faculty")
                        .param("id", String.valueOf(faculty.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

    @Test
    void testDeleteFaculty() throws Exception {
        doNothing().when(facultyService).deleteFaculty(faculty.getId());
        mockMvc.perform(delete("/faculty/{id}", faculty.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void findFacultys() throws Exception {
        when(facultyService.findByNameOrColor(faculty.getName(), null)).thenReturn(List.of(faculty));
        mockMvc.perform(get("/faculty")
                        .param("name", faculty.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(jsonPath("$[0].id").value(faculty.getId()))
                .andExpect(jsonPath("$[0].name").value(faculty.getName()))
                .andExpect(jsonPath("$[0].color").value(faculty.getColor()))
                .andDo(print());
    }
}
