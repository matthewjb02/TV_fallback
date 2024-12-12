package nl.hu.inno.hulp.commons.request;

import lombok.Data;

@Data
public class AddSubmissionToExam {

    String examId;
    String SubmissionId;

    public AddSubmissionToExam(String examId, String submissionId) {
        this.examId = examId;
        this.SubmissionId = submissionId;
    }
}
