package nl.hu.inno.hulp.exam.application.service;

import nl.hu.inno.hulp.commons.dto.GradingCriteriaDTO;
import nl.hu.inno.hulp.commons.request.UpdateOpenQuestionPointsRequest;
import nl.hu.inno.hulp.commons.response.*;
import nl.hu.inno.hulp.exam.ExamProducer;
import nl.hu.inno.hulp.exam.data.CourseRepository;
import nl.hu.inno.hulp.exam.data.ExamRepository;
import nl.hu.inno.hulp.exam.data.QuestionRepository;
import nl.hu.inno.hulp.exam.domain.Course;
import nl.hu.inno.hulp.exam.domain.Exam;
import nl.hu.inno.hulp.exam.domain.GradingCriteria;
import nl.hu.inno.hulp.exam.domain.Statistics;
import nl.hu.inno.hulp.exam.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.exam.domain.question.OpenQuestion;
import nl.hu.inno.hulp.exam.domain.question.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;
    private final RestTemplate restTemplate;
    private final ExamProducer examProducer;

    @Autowired
    public ExamService(ExamRepository examRepository, QuestionRepository questionRepository, CourseRepository courseRepository,
                       RestTemplate restTemplate, ExamProducer examProducer) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.courseRepository = courseRepository;
        this.restTemplate = restTemplate;
        this.examProducer = examProducer;
    }

    public ExamResponse addExam(long examMakerId, long examValidatorId) {

        Exam exam = new Exam(getTeacherById(examMakerId), getTeacherById(examValidatorId));

        Statistics statistics = Statistics.createStatistics(0, 0, 0, 0);
        exam.setStatistics(statistics);
        Exam savedExam = examRepository.save(exam);

        return toExamResponse(savedExam);
    }

    public ExamResponse deleteExam(Long id) {
        ExamResponse oldExamDTO = getExamById(id);
        examRepository.deleteById(id);
        return oldExamDTO;
    }

    public List<ExamResponse> getAllExams() {
        List<Exam> allExams = examRepository.findAll();

        List<ExamResponse> examDTOS = new ArrayList<>();
        for (Exam exam : allExams) {
            examDTOS.add(toExamResponse(exam));
        }

        return examDTOS;
    }

    public ExamResponse getExamById(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No exam with id: " + id + " found!"));

        ExamResponse response = toExamResponse(exam);
        return response;
    }
public void sendAndProcessExam(Long id) {
        this.examProducer.sendExamResponse(getExamById(id));
}

    public Exam getExam(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No exam with id: " + id + " found!"));
    }

    public ExamResponse addQuestionsByIdsToExam(Long examId, List<Long> questionIds) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam not found"));
        List<QuestionEntity> newQuestions = questionRepository.findAllById(questionIds);
        exam.getQuestions().addAll(newQuestions);
        exam.calculateTotalPoints();
        examRepository.save(exam);
        return toExamResponse(exam);
    }


    public ExamResponse addGradingCriteriaToExam(Long examId, GradingCriteriaDTO gradingCriteriaDTO) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam not found"));

        GradingCriteria gradingCriteria = new GradingCriteria(
                gradingCriteriaDTO.openQuestionWeight(),
                gradingCriteriaDTO.closedQuestionWeight()
        );

        exam.setGradingCriteria(gradingCriteria);
        examRepository.save(exam);

        return toExamResponse(exam);
    }

    private ExamResponse toExamResponse(Exam exam) {

        GradingCriteriaDTO gradingCriteriaDTO = new  GradingCriteriaDTO(0,0);
        if (exam.getGradingCriteria() != null) {
            gradingCriteriaDTO = new GradingCriteriaDTO(
                    exam.getGradingCriteria().getOpenQuestionWeight(),
                    exam.getGradingCriteria().getClosedQuestionWeight()
            );
        }

        List<SubmissionResponse> submissionResponses = exam.getSubmissionIds().stream()
                .map(this::getSubmissionById)
                .collect(Collectors.toList());

        StatisticsResponse statisticsResponse = new StatisticsResponse(0, 0, 0, 0);
        if (exam.getStatistics() != null) {
            statisticsResponse = new StatisticsResponse(exam.getStatistics().getSubmissionCount(),
                    exam.getStatistics().getPassCount(),
                    exam.getStatistics().getFailCount(),
                    exam.getStatistics().getAverageScore()
            );
        }
       
        return new ExamResponse(exam.getId(), exam.getTotalPoints(),
              getQuestionResponses(exam.getQuestions()), submissionResponses, gradingCriteriaDTO, statisticsResponse);
    }

    private List<QuestionResponse> getQuestionResponses(List<QuestionEntity> questions) {
        List<QuestionResponse> responses = new ArrayList<>();

        for (QuestionEntity question : questions){
            if (question.getClass().equals(MultipleChoiceQuestion.class)){
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion)question;

                responses.add(new MultipleChoiceQuestionResponse(mcQuestion.getPoints(),
                        mcQuestion.getQuestion(), mcQuestion.getAnswers(),
                        mcQuestion.getCorrectAnswerIndexes(), mcQuestion.getGivenAnswers()));
            } else {
                OpenQuestion openQuestion = (OpenQuestion)question;

                responses.add(new OpenQuestionResponse(openQuestion.getPoints(),
                        openQuestion.getQuestion(), openQuestion.getCorrectAnswer(),
                        openQuestion.getAnswer(), openQuestion.getTeacherFeedback()));
            }
        }
        return responses;
    }

    public Long getTeacherById(Long id) {
        String url = "https://userss-fje9bmb2b3gtdafe.northeurope-01.azurewebsites.net/teacher/" + id;
        examProducer.sendTeacherRequest(id);
        return restTemplate.getForObject(url, TeacherResponse.class).getId();
    }
    public SubmissionResponse getSubmissionById(Long id) {
        String url = "https://inno-testvision-grading-abaybzgufxdvh5cy.northeurope-01.azurewebsites.net/submission/" + id;
        return restTemplate.getForObject(url, SubmissionResponse.class);
    }


    // used by other modules via rpc

    public SubmissionResponse getSubmissionByExamAndStudentId(Long examId, Long studentId){
        Exam exam = getExam(examId);
        SubmissionResponse submissionResponse = exam.getSubmissionIds().stream()
                .map(submissionId -> getSubmissionById(submissionId))
                .filter(submission -> submission.getStudent().getId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No submission found for exam with id: " + examId + " and student with id: " + studentId));

        return submissionResponse;



    }

    public List<SubmissionResponse> getSubmissionsByExamId(Long examId) {

        Exam exam = getExam(examId);
        List<SubmissionResponse> submissionResponses = exam.getSubmissionIds().stream()
                .map(submissionId -> getSubmissionById(submissionId))
                .collect(Collectors.toList());

        return submissionResponses;

    }


    public void updatePointsForOpenQuestion(Long examId, int questionNr, UpdateOpenQuestionPointsRequest request) {
        Exam exam = getExam(examId);
        QuestionEntity question = exam.getQuestions().get(questionNr - 1);
        if (question != null) {
            if (request.getGivenPoints() > question.getPoints() || request.getGivenPoints() < 0) {
                throw new IllegalArgumentException("Given points must be between 0 and the maximum points of the question");
            }
            question.addGivenPoints(request.getGivenPoints());

            if (question.getClass().equals(OpenQuestion.class)) {
                OpenQuestion openQuestion = (OpenQuestion) question;
                openQuestion.addTeacherFeedback(request.getFeedback());
            }
        }
    }

    public double calculateGrade(Long examId) {
        Exam exam = getExam(examId);
        return exam.calculateGrade();

    }

    public void updateStatistics(Long examId) {
        Exam exam = getExam(examId);
        double passGrade = 5.5;

        int passCount = (int) exam.getSubmissionIds().stream()
                .map(this::getSubmissionById)
                .filter(submission -> submission.getGrading().getGrade() >= passGrade)
                .count();

        int failCount = exam.getSubmissionIds().size() - passCount;

        double averageScore = exam.getSubmissionIds().stream()
                .map(this::getSubmissionById)
                .mapToDouble(submission -> submission.getGrading().getGrade())
                .average()
                .orElse(0);

        Statistics statistics = new Statistics(exam.getSubmissionIds().size(), passCount, failCount, averageScore);
        exam.setStatistics(statistics);
        saveExam(exam);
    }

    public void addSubmission(Long examId, Long submissionId) {
        Exam exam = getExam(examId);
        exam.addSubmissionId(submissionId);

    }


// helper functions

    public void saveExam(Exam exam) {
        examRepository.save(exam);
    }

}
