package nl.hu.inno.hulp.commons.dto;

public class OpenQuestionDTO extends QuestionDTO{

    private String correctAnswer;
    private String answer;
    private String teacherFeedback;

    public OpenQuestionDTO() {
    }

    public OpenQuestionDTO(Long id, int points, String question, double givenPoints, String teacherFeedback, String correctAnswer, String answer) {
        super(id, points, question, givenPoints);
        this.correctAnswer = correctAnswer;
        this.answer = answer;
        this.teacherFeedback = teacherFeedback;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public String getTeacherFeedback() {
        return teacherFeedback;
    }
}
