package nl.hu.inno.hulp.exam.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Getter
@Embeddable
public class GradingCriteria {

    private double openQuestionWeight;
    private double closedQuestionWeight;

    public GradingCriteria(double openQuestionWeight, double closedQuestionWeight) {
        if (openQuestionWeight + closedQuestionWeight != 1.0 || openQuestionWeight < 0 || closedQuestionWeight < 0) {
            throw new IllegalArgumentException("The sum of the weights must be 1.0 and both weights must be positive");
        }

        this.openQuestionWeight = openQuestionWeight;
        this.closedQuestionWeight = closedQuestionWeight;
    }

    protected GradingCriteria() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GradingCriteria that)) return false;
        return Double.compare(openQuestionWeight, that.openQuestionWeight) == 0 && Double.compare(closedQuestionWeight, that.closedQuestionWeight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(openQuestionWeight, closedQuestionWeight);
    }
}