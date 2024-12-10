package nl.hu.inno.hulp.commons.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class QuestionResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private int points;
    private String question;

    public QuestionResponse(int points, String question) {
        this.points = points;
        this.question = question;
    }
}
