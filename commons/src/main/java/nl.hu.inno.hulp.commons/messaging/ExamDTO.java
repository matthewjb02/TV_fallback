package nl.hu.inno.hulp.commons.messaging;


import lombok.Data;
import nl.hu.inno.hulp.commons.enums.ValidationStatus;

import java.util.List;

@Data
public class ExamDTO {
    private Long id;
    private Long gradingCriteriaId;
    private List<Long> submissionIds;
    private ValidationStatus validationStatus = ValidationStatus.WAITING;
    private String reason;
    private List<Long> questionIds;
    private Long examValidatorId;
    private Long statisticsId;
    private Long examMakerId;
    private int totalPoints;

    public ExamDTO(Long id, Long gradingCriteriaId, List<Long> submissionIds, ValidationStatus validationStatus, String reason,
                   List<Long> questionIds, Long examValidatorId, Long statisticsId, Long examMakerId, int totalPoints) {
        this.id = id;
        this.gradingCriteriaId = gradingCriteriaId;
        this.submissionIds = submissionIds;
        this.validationStatus = validationStatus;
        this.reason = reason;
        this.questionIds = questionIds;
        this.examValidatorId = examValidatorId;
        this.statisticsId = statisticsId;
        this.examMakerId = examMakerId;
        this.totalPoints = totalPoints;
    }

}