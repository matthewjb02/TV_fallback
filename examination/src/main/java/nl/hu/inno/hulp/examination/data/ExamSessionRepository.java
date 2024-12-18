package nl.hu.inno.hulp.examination.data;

import nl.hu.inno.hulp.examination.domain.ExamSession;
import nl.hu.inno.hulp.examination.domain.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamSessionRepository extends MongoRepository<ExamSession, Long> {

}