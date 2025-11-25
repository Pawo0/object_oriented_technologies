package pl.edu.agh.to.school.grade;

import org.springframework.stereotype.Service;

@Service
public class GradeService {

    private final IGradeRepository gradeRepository;

    public GradeService(IGradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public Iterable<Grade> getGrades() {
        return gradeRepository.findAll();
    }

}
