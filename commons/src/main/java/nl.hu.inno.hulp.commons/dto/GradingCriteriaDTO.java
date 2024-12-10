package nl.hu.inno.hulp.commons.dto;

import java.io.Serializable;

public record GradingCriteriaDTO(double openQuestionWeight, double closedQuestionWeight) implements Serializable {
}