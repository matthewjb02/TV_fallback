package nl.hu.inno.hulp.commons.response;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class MultipleChoiceQuestionResponse extends QuestionResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> answers;
    private List<Integer> correctAnswerIndexes;
    private List<Integer> givenAnswers;

    public MultipleChoiceQuestionResponse(int points,String question,List<String> answers,List<Integer> correctAnswerIndexes,List<Integer> givenAnswers) {
        super(points, question);
        this.answers = answers;
        this.correctAnswerIndexes = correctAnswerIndexes;
        this.givenAnswers = givenAnswers;
    }
}
