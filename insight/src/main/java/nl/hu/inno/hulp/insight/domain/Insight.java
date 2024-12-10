package nl.hu.inno.hulp.insight.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Insight {

    @Id
    @GeneratedValue
    private Long id;
    private boolean canStudentsViewIt;
    private Long examGraderId;
    @ElementCollection
    private List<Long> candidateIds;
    @ElementCollection
    private List<Long> submissionIds;

    @OneToMany
    private List<IndividualInsight> individualInsights;




    protected Insight() {}
    public Insight(List<Long> submissionIds,List<Long> candidateIds ) {
        this.submissionIds = submissionIds;
        this.canStudentsViewIt = false;
        this.candidateIds = candidateIds;
        this.individualInsights = new ArrayList<>();
        int index=0;
        for (Long submissionId : submissionIds) {
            IndividualInsight individualInsight = new IndividualInsight(candidateIds.get(index),this,submissionId);
            individualInsights.add(individualInsight);
            index++;
        }

    }
    public void letStudentsViewIt() {
        this.canStudentsViewIt = true;
    }
    public void addInsightAfterStudentFeedBack(IndividualInsight insight) {
        individualInsights.remove(insight);
        individualInsights.add(insight);
    }

}
