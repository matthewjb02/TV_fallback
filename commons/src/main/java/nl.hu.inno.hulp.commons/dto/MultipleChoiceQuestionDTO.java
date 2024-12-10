package nl.hu.inno.hulp.commons.dto;

import java.util.List;

public class MultipleChoiceQuestionDTO extends QuestionDTO{

    private List<String> answers;
    private List<Integer> correctAnswerIndexes;
    private List<Integer> givenAnswers;

    public MultipleChoiceQuestionDTO(Long id, int points, String question, double givenPoints, List<String> answers,
                                     List<Integer> correctAnswerIndexes, List<Integer> givenAnswers) {
        super(id, points, question, givenPoints);
        this.answers = answers;
        this.correctAnswerIndexes = correctAnswerIndexes;
        this.givenAnswers = givenAnswers;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public List<Integer> getCorrectAnswerIndexes() {
        return correctAnswerIndexes;
    }

    public List<Integer> getGivenAnswers() {
        return givenAnswers;
    }
}
