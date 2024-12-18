package nl.hu.inno.hulp.grading.application;


import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.commons.request.GradingRequest;
import nl.hu.inno.hulp.commons.request.UpdateOpenQuestionPointsRequest;
import nl.hu.inno.hulp.commons.response.*;
import nl.hu.inno.hulp.grading.data.SubmissionRepository;
import nl.hu.inno.hulp.grading.domain.Grading;
import nl.hu.inno.hulp.grading.domain.Submission;
import nl.hu.inno.hulp.grading.producer.RabbitMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Transactional
@Service
public class SubmissionService {


    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
    private final RabbitMQProducer rabbitMQProducer;
    private final RestTemplate restTemplate = new RestTemplate();
    private final SubmissionRepository submissionRepository;

    public SubmissionService(RabbitMQProducer rabbitMQProducer, SubmissionRepository submissionRepository) {
        this.rabbitMQProducer = rabbitMQProducer;
        this.submissionRepository = submissionRepository;
    }

    private Submission findSubmissionByExamAndStudentId(Long examId, Long studentId) {
        String submissionUrl = "http://localhost:8086/exams/" + examId + "/students/" + studentId + "/submission";
        SubmissionResponse submissionResponse = restTemplate.getForObject(submissionUrl, SubmissionResponse.class, examId, studentId);
        Long submissionId = submissionResponse.getId();
        return submissionRepository.findById(submissionId).orElseThrow();
    }

    public SubmissionResponse getSubmissionResponseById(Long id) {
        return toSubmissionResponse(id);
    }

    public List<SubmissionResponse> getSubmissionsByExamId(Long examId) {
        String submissionUrl = "http://localhost:8086/exams/{examId}/submissions";
        ResponseEntity<List<SubmissionResponse>> response = restTemplate.exchange(
                submissionUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                examId
        );
        return response.getBody();
    }

    public void updateOpenQuestionGrading(Long examId, Long studentId, int questionNr, UpdateOpenQuestionPointsRequest request) {
        Submission submission = findSubmissionByExamAndStudentId(examId, studentId);
        Long examSessionIdFromSubmission = submission.getExamSessionId();

        rabbitMQProducer.sendUpdateOpenQuestionPoints(examSessionIdFromSubmission, questionNr, request);

        submissionRepository.save(submission);
    }

    public void addGrading(Long examId, Long studentId, GradingRequest request) {
        Submission submission = findSubmissionByExamAndStudentId(examId, studentId);

        String examCourseUrl = "https://exam-aze2emf4etgrapew.northeurope-01.azurewebsites.net//courses/exams/" + examId + "/course";
        CourseResponse examCourse = restTemplate.getForObject(examCourseUrl, CourseResponse.class, examId);


        if (examCourse.getTeachers().stream().noneMatch(teacherDTO -> teacherDTO.getId() == request.getTeacherId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Teacher is not allowed to grade this exam");
        }

        String teacherUrl = "https://userss-fje9bmb2b3gtdafe.northeurope-01.azurewebsites.net/teacher/" + request.getTeacherId();
        TeacherResponse teacher = restTemplate.getForObject(teacherUrl, TeacherResponse.class);

        String calculatedGradeUrl = "https://userss-fje9bmb2b3gtdafe.northeurope-01.azurewebsites.net/exams/" + examId + "/gradeCalculation";
        double calculatedGrade = restTemplate.getForObject(calculatedGradeUrl, Double.class, examId);

        Grading grading = Grading.createGrading(calculatedGrade, request.getComments());

        grading.addGrader(teacher.getId());
        submission.addGrading(grading);

        rabbitMQProducer.sendUpdateExamStatistics(examId);

        submissionRepository.save(submission);
    }

    public SubmissionResponse createSubmission(Long examSessionId) {
        if (examSessionId == null) {
            throw new IllegalArgumentException("ExamSession ID must not be null");
        }

        Submission submission = Submission.createSubmission(examSessionId);
        submission = submissionRepository.save(submission);


        return toSubmissionResponse(submission.getId());
    }

    public void saveSubmission(Long id) {
        Submission submission = submissionRepository.findById(id).orElseThrow();
        submissionRepository.save(submission);
    }

    // helper functions

    public SubmissionResponse toSubmissionResponse(Long id){
        Submission submission = submissionRepository.findById(id).orElseThrow();
        String examSessionUrl = "https://inno-testvision-grading-abaybzgufxdvh5cy.northeurope-01.azurewebsites.net/session/" + submission.getExamSessionId();
        ExamSessionResponse examSession = restTemplate.getForObject(examSessionUrl, ExamSessionResponse.class);

        assert examSession != null;
        return new SubmissionResponse(examSession, submission.getId(), submission.getStatus());

    }

    public GradingResponse toGradingsResponse(Grading grading) {
        return new GradingResponse(grading.getId(), grading.getGrade(), grading.getComments());

    }


}

