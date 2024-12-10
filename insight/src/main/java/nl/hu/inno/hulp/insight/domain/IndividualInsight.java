package nl.hu.inno.hulp.insight.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class IndividualInsight {
    @Id
    @GeneratedValue
    private Long id;
    private Long candidateId;
    private String feedBack;
    private Long submissionId;
protected IndividualInsight() {}
    public IndividualInsight(Long candidateId, Insight insight,Long submissionId) {
    if (insight.getCandidateIds().contains(candidateId)){
    this.candidateId = candidateId;
    this.submissionId=submissionId;
    }
    }
    public void addFeedback(Insight insight, String feedback) {
    if (insight.isCanStudentsViewIt()) {
    this.feedBack = feedback;}
    }

}
