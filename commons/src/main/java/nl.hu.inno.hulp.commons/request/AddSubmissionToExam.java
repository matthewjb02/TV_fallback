package nl.hu.inno.hulp.commons.request;

import lombok.Data;

@Data
public class AddSubmissionToExam {

    Long examId;
    Long SubmissionId;

    public AddSubmissionToExam(Long examId, Long submissionId) {
        this.examId = examId;
        this.SubmissionId = submissionId;
    }
}
