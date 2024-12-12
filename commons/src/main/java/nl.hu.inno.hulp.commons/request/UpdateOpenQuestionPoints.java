package nl.hu.inno.hulp.commons.request;

import lombok.Data;

@Data
public class UpdateOpenQuestionPoints {

    public String examId;
    public int questionNr;
    public UpdateOpenQuestionPointsRequest grading;

    public UpdateOpenQuestionPoints(String examId, int questionNr, UpdateOpenQuestionPointsRequest grading) {
        this.examId = examId;
        this.questionNr = questionNr;
        this.grading = grading;
    }
}