package pl.edu.agh.to.school.course;

import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.student.Student;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final ICourseRepository courseRepository;

    public CourseService(ICourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Integer id) {
        return courseRepository.findById(id);
    }

    public List<Student> getStudentsInCourse(int courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        return courseOptional.map(Course::getStudents).orElse(List.of());
    }


}
