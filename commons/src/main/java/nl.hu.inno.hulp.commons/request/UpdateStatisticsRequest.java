package nl.hu.inno.hulp.commons.request;

import lombok.Data;

@Data
public class UpdateStatisticsRequest {
    private String examId;
    private String studentId;
    private double calculatedGrade;

    public UpdateStatisticsRequest(String examId, String studentId, double calculatedGrade) {
        this.examId = examId;
        this.studentId = studentId;
        this.calculatedGrade = calculatedGrade;
    }
}
