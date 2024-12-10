package nl.hu.inno.hulp.grading.domain;

import jakarta.persistence.*;
import lombok.Getter;
import nl.hu.inno.hulp.commons.enums.SubmissionStatus;
import nl.hu.inno.hulp.commons.messaging.ExamSessionDTO;
import nl.hu.inno.hulp.commons.messaging.GradingDTO;


@Getter
@Entity
public class Submission {

    @Id
    @GeneratedValue
    private Long id;

    @Column (name = "exam_session_id")
    private Long examSessionId;

    @OneToOne(cascade = CascadeType.ALL)
    private Grading grading;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    private Submission(Long examSessionSessionId) {
        this.examSessionId = examSessionSessionId;
        this.status = SubmissionStatus.SUBMITTED;
    }

    protected Submission() {
    }

    public static Submission createSubmission(Long examSessionId) {
        return new Submission(examSessionId);
    }

    public void addGrading(Grading grading) {
        this.grading = grading;
        this.status = SubmissionStatus.GRADED;
    }
}