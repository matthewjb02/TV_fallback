package nl.hu.inno.hulp.commons.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class QuestionResponse implements Serializable {
    private String id;

    private int points;
    private String question;

    public QuestionResponse(int points, String question) {
        this.id=id;
        this.points = points;
        this.question = question;
    }
}
