package nl.hu.inno.hulp.commons.response;


import lombok.Getter;

@Getter
public class IndividualInsightResponse {
    private Long id;
    private Long candidateId;
    private String feedBack;
    private Long submissionId;

    public IndividualInsightResponse(Long id, Long candidateId, String feedBack,Long submissionId) {
        this.id = id;
        this.candidateId = candidateId;
        this.feedBack = feedBack;
        this.submissionId = submissionId;
    }
}
