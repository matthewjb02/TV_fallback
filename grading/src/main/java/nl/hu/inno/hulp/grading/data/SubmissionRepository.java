package nl.hu.inno.hulp.grading.data;

import nl.hu.inno.hulp.grading.domain.Submission;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SubmissionRepository extends Neo4jRepository<Submission, String> {
}