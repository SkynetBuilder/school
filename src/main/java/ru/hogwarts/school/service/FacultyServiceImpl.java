package ru.hogwarts.school.service;

import java.util.*;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

@Service
public class FacultyServiceImpl implements FacultyService{

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty editFaculty(long id, Faculty faculty) {
        return facultyRepository.findById(id)
                .map(foundedFaculty -> {
                    foundedFaculty.setName(faculty.getName());
                    foundedFaculty.setColor(faculty.getColor());
                    return facultyRepository.save(foundedFaculty);
                }).orElse(null);
    }

    @Override
    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> findByNameOrColor(String name, String color) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}