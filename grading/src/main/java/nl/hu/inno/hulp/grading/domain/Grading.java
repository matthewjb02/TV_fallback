package nl.hu.inno.hulp.grading.domain;

import jakarta.persistence.*;
import lombok.Getter;
import nl.hu.inno.hulp.commons.messaging.TeacherDTO;


@Entity
@Getter
public class Grading {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double grade = 0.0;
    private String comments;

    @Transient
    private Long graderId;

    public Grading(double grade, String comments) {
        this.grade = grade;
        this.comments = comments;
    }

    protected Grading() {}

    public static Grading createGrading(double grade, String comments) {
        return new Grading(grade, comments);
    }

    public void addGrader(Long grader) {
        this.graderId = grader;

    }


}