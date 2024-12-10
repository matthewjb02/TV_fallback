package nl.hu.inno.hulp.commons.response;

import lombok.Getter;
import nl.hu.inno.hulp.commons.dto.GradingCriteriaDTO;
import nl.hu.inno.hulp.commons.enums.ValidationStatus;

import java.util.List;

@Getter
public class ExamTeacherResponse {
    private Long id;
    private List<QuestionResponse> questions;
    private String examMaker;
    private String examValidator;
    private ValidationStatus validationStatus;
    private String reason;
    private int totalPoints;
    private GradingCriteriaDTO gradingCriteria;
    private List<SubmissionResponse> submissions;
    private StatisticsResponse statistics;

    public ExamTeacherResponse(Long id, List<QuestionResponse> questions, String examMaker, String examValidator, ValidationStatus validationStatus, String reason, int totalPoints, GradingCriteriaDTO gradingCriteria, List<SubmissionResponse> submissions, StatisticsResponse statistics) {
        this.id = id;
        this.questions = questions;
        this.examMaker = examMaker;
        this.examValidator = examValidator;
        this.validationStatus = validationStatus;
        this.reason = reason;
        this.totalPoints = totalPoints;
        this.gradingCriteria = gradingCriteria;
        this.submissions = submissions;
        this.statistics = statistics;
    }
}