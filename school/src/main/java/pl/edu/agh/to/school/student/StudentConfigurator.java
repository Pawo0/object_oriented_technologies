package pl.edu.agh.to.school.student;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class StudentConfigurator {

    private final IStudentRepository studentRepository;

    public StudentConfigurator(IStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostConstruct
    private void initStudents() {
        if (studentRepository.count() == 0) {
            Student kowalski = new Student("Jan", "Kowalski", LocalDate.now(), "123456");
            studentRepository.save(kowalski);
        }
    }
}