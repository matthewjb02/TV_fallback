package nl.hu.inno.hulp.grading.domain;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import java.util.UUID;

@Node
@Data
public class Grading {
    @Id
    private String id= UUID.randomUUID().toString();
    private double grade = 0.0;
    private String comments;
    private String graderId;

    public Grading(double grade, String comments) {
        this.grade = grade;
        this.comments = comments;
    }

    protected Grading() {}

    public static Grading createGrading(double grade, String comments) {
        return new Grading(grade, comments);
    }

    public void addGrader(String grader) {
        this.graderId = grader;
    }
}