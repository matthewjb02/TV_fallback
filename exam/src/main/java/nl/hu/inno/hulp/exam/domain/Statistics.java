package nl.hu.inno.hulp.exam.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int submissionCount;
    private int passCount;
    private int failCount;
    private double averageScore;


    public Statistics(int submissionCount, int passCount, int failCount, double averageScore) {
        this.submissionCount = submissionCount;
        this.passCount = passCount;
        this.failCount = failCount;
        this.averageScore = averageScore;
    }

    protected Statistics() {
    }

    public static Statistics createStatistics(int submissionCount, int passCount, int failCount, double averageScore) {
        return new Statistics(submissionCount, passCount, failCount, averageScore);
    }


}