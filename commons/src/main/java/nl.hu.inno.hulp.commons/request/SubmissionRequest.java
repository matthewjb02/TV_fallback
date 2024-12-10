package nl.hu.inno.hulp.commons.request;

import lombok.Data;

@Data
public class SubmissionRequest {
    private Long examId;
    private Long studentId;

    public SubmissionRequest(Long id, Long studentId) {
        this.examId = id;
        this.studentId = studentId;
    }

}
