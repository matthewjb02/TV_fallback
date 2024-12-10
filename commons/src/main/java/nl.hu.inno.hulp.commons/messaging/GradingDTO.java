package nl.hu.inno.hulp.commons.messaging;

import lombok.Getter;

@Getter
public class GradingDTO {
    private Long id;
    private double grade;
    private String comments;
    private Long grader;

    public GradingDTO(Long id, double grade, String comments, Long grader) {
        this.id = id;
        this.grade = grade;
        this.comments = comments;
        this.grader = grader;
    }
}