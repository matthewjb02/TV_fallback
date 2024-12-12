package nl.hu.inno.hulp.grading.domain;

import lombok.Data;
import nl.hu.inno.hulp.commons.enums.SubmissionStatus;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import java.util.UUID;

@Node
@Data
public class Submission {
    @Id
    private String id= UUID.randomUUID().toString();
    private String examSessionId;
    private Grading grading;
    private SubmissionStatus status;

    private Submission(String examSessionSessionId) {
        this.examSessionId = examSessionSessionId;
        this.status = SubmissionStatus.SUBMITTED;
    }

    protected Submission() {
    }

    public static Submission createSubmission(String examSessionId) {
        return new Submission(examSessionId);
    }

    public void addGrading(Grading grading) {
        this.grading = grading;
        this.status = SubmissionStatus.GRADED;
    }
}