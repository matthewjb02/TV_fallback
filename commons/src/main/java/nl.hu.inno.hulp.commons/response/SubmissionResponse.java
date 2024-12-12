package nl.hu.inno.hulp.commons.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import nl.hu.inno.hulp.commons.enums.SubmissionStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmissionResponse extends ExamSessionResponse {
    @JsonProperty("submissionId")
    private String submissionId;

    @JsonProperty("submissionStatus")
    private SubmissionStatus submissionStatus;

    @JsonProperty("grading")
    private GradingResponse grading;

    public SubmissionResponse(ExamSessionResponse examSession, String submissionId, SubmissionStatus submissionStatus, GradingResponse grading) {
        super(examSession.getId(), examSession.getStatus(), examSession.getDuration(), examSession.getStudent());
        this.submissionId = submissionId;
        this.submissionStatus = submissionStatus;
        this.grading = new GradingResponse(grading.getId(),grading.getGrade(), grading.getComments());
    }

    public SubmissionResponse(ExamSessionResponse examSession, String submissionId, SubmissionStatus submissionStatus) {
        super(examSession.getId(), examSession.getStatus(), examSession.getDuration(), examSession.getStudent());
        this.submissionId = submissionId;
        this.submissionStatus = submissionStatus;
    }
}