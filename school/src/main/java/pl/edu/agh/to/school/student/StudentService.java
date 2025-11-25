package pl.edu.agh.to.school.student;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

//    public List<Student> getStudents(){
//        return List.of(
//                new Student("Jan", "Kowalski", LocalDate.of(2000, 1, 15), "123456"),
//                new Student("Anna", "Nowak", LocalDate.of(1999, 5, 30), "654321")
//        );
//    }

    private final IStudentRepository studentRepository;

    public StudentService(IStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
}
