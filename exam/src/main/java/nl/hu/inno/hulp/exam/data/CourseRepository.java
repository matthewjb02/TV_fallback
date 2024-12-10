package nl.hu.inno.hulp.exam.data;


import nl.hu.inno.hulp.exam.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByApprovedExamsId(Long examId);
}
