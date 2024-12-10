package nl.hu.inno.hulp.commons.request;

import lombok.Getter;

@Getter
public class OpenQuestionRequest extends QuestionRequest{

    private String correctAnswer;

    public OpenQuestionRequest(int points, String question, String correctAnswer) {
        super(points, question);
        this.correctAnswer = correctAnswer;
    }
}
