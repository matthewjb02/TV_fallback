package nl.hu.inno.hulp.commons.request;

import lombok.Data;

@Data
public class UpdateStatisticsRequest {
    private Long examId;
    private Long studentId;
    private double calculatedGrade;


    public UpdateStatisticsRequest(Long examId, Long studentId, double calculatedGrade) {
        this.examId = examId;
        this.studentId = studentId;
        this.calculatedGrade = calculatedGrade;
    }
}
