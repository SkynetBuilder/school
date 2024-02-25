package ru.hogwarts.school.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

@Service
public class FacultyServiceImpl implements FacultyService{
    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info(("Was invoked method to create faculty"));
        facultyRepository.save(faculty);
        logger.info((faculty + " is saved"));
        return faculty;
    }

    @Override
    public Faculty findFaculty(long id) {
        logger.info(("Was invoked method to find faculty with id " + id));
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty editFaculty(long id, Faculty faculty) {
        logger.info(("Was invoked method to edit faculty with id " + id));
        return facultyRepository.findById(id)
                .map(foundFaculty -> {
                    foundFaculty.setName(faculty.getName());
                    foundFaculty.setColor(faculty.getColor());
                    facultyRepository.save(foundFaculty);
                    logger.info(foundFaculty + " is edited");
                    return foundFaculty;
                }).orElse(null);
    }

    @Override
    public void deleteFaculty(long id) {
        logger.info(("Was invoked method to delete faculty"));
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> findByNameOrColor(String name, String color) {
        logger.info(("Was invoked method to find faculty by name or color"));
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}