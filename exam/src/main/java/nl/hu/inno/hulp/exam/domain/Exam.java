package nl.hu.inno.hulp.exam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.hu.inno.hulp.commons.enums.ValidationStatus;
import nl.hu.inno.hulp.exam.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.exam.domain.question.OpenQuestion;
import nl.hu.inno.hulp.exam.domain.question.QuestionEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Exam {
    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    private GradingCriteria gradingCriteria;

    @Lob
    private List<Long> submissionIds = new ArrayList<>();

    private ValidationStatus validationStatus= ValidationStatus.WAITING;
    private String reason;

    @OneToMany
    private List<QuestionEntity> questions;

    private Long examValidatorId;

    @OneToOne(cascade = CascadeType.ALL)
    private Statistics statistics;

    private Long examMakerId;
    private int totalPoints;
    protected Exam(){

    }

    public Exam(Long examMakerId, Long examValidatorId){
            this.examMakerId = examMakerId;
            this.examValidatorId = examValidatorId;
            this.questions = new ArrayList<>();
            calculateTotalPoints();

    }

    public void calculateTotalPoints(){

        totalPoints = questions.stream().mapToInt(QuestionEntity::getPoints).sum();
    }

    public double calculateGrade() {
        if (questions.isEmpty() || totalPoints == 0) {
            return 1.0;
        }

        int totalOpenPoints = getTotalOpenQuestionPoints();
        int totalMultipleChoicePoints = getTotalMultipleChoiceQuestionPoints();

        double openQuestionWeight = gradingCriteria.getOpenQuestionWeight();
        double multipleChoiceWeight = gradingCriteria.getClosedQuestionWeight();

        int totalOpenGivenPoints = calculateTotalOpenGivenPoints();
        int totalMultipleChoiceGivenPoints = calculateTotalMultipleChoiceGivenPoints();

        double weightedOpenPoints = calculateWeightedPoints(totalOpenGivenPoints, openQuestionWeight);
        double weightedMultipleChoicePoints = calculateWeightedPoints(totalMultipleChoiceGivenPoints, multipleChoiceWeight);

        double grade = (weightedOpenPoints + weightedMultipleChoicePoints) / (totalOpenPoints + totalMultipleChoicePoints) * 10 * 2;
        return Math.round(grade * 10.0) / 10.0;
    }

    private int calculateTotalOpenGivenPoints() {
        int totalOpenGivenPoints = 0;
        for (QuestionEntity question : questions) {
            if (question instanceof OpenQuestion) {
                totalOpenGivenPoints += question.getGivenPoints();
            }
        }
        return totalOpenGivenPoints;
    }

    private int calculateTotalMultipleChoiceGivenPoints() {
        int totalMultipleChoiceGivenPoints = 0;
        for (QuestionEntity question : questions) {
            if (question instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
                if (mcQuestion.getCorrectAnswerIndexes().equals(mcQuestion.getGivenAnswers())) {
                    totalMultipleChoiceGivenPoints += mcQuestion.getPoints();
                }
            }
        }
        return totalMultipleChoiceGivenPoints;
    }

    private double calculateWeightedPoints(int totalGivenPoints, double weight) {
        return totalGivenPoints * weight;
    }



    public void removeQuestions(List<QuestionEntity> questions){
        this.questions.removeAll(questions);
    }

    public void addQuestions(List<QuestionEntity> questions){
        this.questions.addAll(questions);
    }


    public int getTotalOpenQuestionPoints(){
        return questions.stream()
                .filter(question -> question instanceof OpenQuestion)
                .mapToInt(QuestionEntity::getPoints)
                .sum();
    }

    public int getTotalMultipleChoiceQuestionPoints(){
        return questions.stream()
                .filter(question -> question instanceof MultipleChoiceQuestion)
                .mapToInt(QuestionEntity::getPoints)
                .sum();
    }

    public void addSubmissionId(Long submissionId) {
        this.submissionIds.add(submissionId);
    }

    public void addExamMakerId(Long examMakerId) {
        this.examMakerId = examMakerId;
    }
    public void addExamValidatorId(Long examValidatorId) {
        this.examValidatorId = examValidatorId;
    }

    public void addGradingCriteria(GradingCriteria gradingCriteria) {
        this.gradingCriteria = gradingCriteria;
    }


    public void addStatistics(Statistics statistics) {
        this.statistics = statistics;
    }


}
