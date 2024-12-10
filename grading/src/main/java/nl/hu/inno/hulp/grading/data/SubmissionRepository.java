package nl.hu.inno.hulp.grading.data;


import nl.hu.inno.hulp.grading.domain.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

}