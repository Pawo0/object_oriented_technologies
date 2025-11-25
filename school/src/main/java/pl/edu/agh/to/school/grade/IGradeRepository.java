package pl.edu.agh.to.school.grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGradeRepository extends JpaRepository<Grade, Integer> {
}
