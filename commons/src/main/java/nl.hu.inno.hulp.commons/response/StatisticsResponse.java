package nl.hu.inno.hulp.commons.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class StatisticsResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private int submissionCount;
    private int passCount;
    private int failCount;
    private double averageScore;

    public StatisticsResponse(int submissionCount, int passCount, int failCount, double averageScore) {
        this.submissionCount = submissionCount;
        this.passCount = passCount;
        this.failCount = failCount;
        this.averageScore = averageScore;
    }
}
