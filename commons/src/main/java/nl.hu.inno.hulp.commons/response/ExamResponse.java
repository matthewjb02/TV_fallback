package nl.hu.inno.hulp.commons.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.hu.inno.hulp.commons.dto.GradingCriteriaDTO;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExamResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private int totalPoints;
    private List<QuestionResponse> questions;
    private List<SubmissionResponse> submissions;
    private GradingCriteriaDTO gradingCriteria;
    private StatisticsResponse statisticsResponse;
}