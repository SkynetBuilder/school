package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);
    Collection<Student> findByAgeBetween(int ageFrom, int ageTo);
    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Long getStudentCount();
    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Integer getAverageAge();
    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> getLastFiveStudents();
}
