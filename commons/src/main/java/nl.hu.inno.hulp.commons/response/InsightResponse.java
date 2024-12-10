package nl.hu.inno.hulp.commons.response;

import lombok.Getter;

import java.util.List;

@Getter
public class InsightResponse {
    private Long id;
    private boolean canStudentsViewIt;
    private Long examGraderId;
    private List<Long> candidateIds;

    private List<Long> submissionIds;
    private List<IndividualInsightResponse> individualInsights;

    public InsightResponse(Long id, boolean canStudentsViewIt, Long examGraderId, List<Long> candidateIds, List<Long> submissionIds, List<IndividualInsightResponse> individualInsights) {
        this.id = id;
        this.canStudentsViewIt = canStudentsViewIt;
        this.examGraderId = examGraderId;
        this.candidateIds = candidateIds;
        this.submissionIds = submissionIds;
        this.individualInsights = individualInsights;
    }
}
