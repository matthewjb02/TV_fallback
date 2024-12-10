package nl.hu.inno.hulp.exam.application.service;

import nl.hu.inno.hulp.commons.dto.GradingCriteriaDTO;
import nl.hu.inno.hulp.commons.request.CourseRequest;
import nl.hu.inno.hulp.commons.response.CourseResponse;
import nl.hu.inno.hulp.commons.response.*;
import nl.hu.inno.hulp.exam.ExamProducer;
import nl.hu.inno.hulp.exam.data.CourseRepository;
import nl.hu.inno.hulp.exam.data.ExamRepository;
import nl.hu.inno.hulp.exam.data.QuestionRepository;
import nl.hu.inno.hulp.exam.domain.Course;
import nl.hu.inno.hulp.exam.domain.Exam;
import nl.hu.inno.hulp.exam.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.exam.domain.question.OpenQuestion;
import nl.hu.inno.hulp.exam.domain.question.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final RestTemplate restTemplate;
    private final ExamProducer examProducer;


    @Autowired
    public CourseService(CourseRepository courseRepository, ExamRepository examRepository, QuestionRepository questionRepository, RestTemplate restTemplate, ExamProducer examProducer) {
        this.courseRepository = courseRepository;
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.restTemplate = restTemplate;
        this.examProducer = examProducer;
    }

    public List<CourseResponse> getAllCourses() {
            List<Course> allCourses = courseRepository.findAll();
        List<CourseResponse> courseDTOs = new ArrayList<>();
        for (Course course : allCourses){
            courseDTOs.add(getCourseResponse(course));
        }

        return courseDTOs;
    }

    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course with id: " + id + " found!"));

        return getCourseResponse(course);
    }

    public CourseResponse addCourse(CourseRequest course) {
        Course savedCourse = new Course(course.name());
        courseRepository.save(savedCourse);
        return getCourseResponse(savedCourse);
    }

    public CourseResponse deleteCourse(Long id) {
        CourseResponse oldDTO = getCourseById(id);
        courseRepository.deleteById(id);
        return oldDTO;
    }

    public CourseResponse addTeacherToCourse(Long courseId, Long teacherId) {
        Course course=courseRepository.findById(courseId).orElseThrow();
        course.addTeacher(teacherId);
        courseRepository.save(course);
        return getCourseResponse(course);
    }

    public CourseResponse addTestToCourse(Long courseId, Long testId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        Exam exam = examRepository.findById(testId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test not found"));

        course.addExam(exam);
        courseRepository.save(course);

        return getCourseResponse(course);
    }

    public List<ExamResponse> getAllTestsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course with id: " + courseId + " found!"));

        List<ExamResponse> examResponses = new ArrayList<>();
        for (Exam exam : course.getApprovedExams()) {
            examResponses.add(getExamResponse(exam));
        }

        return examResponses;
    }
    public ExamResponse getApprovedExamByCourse(Long courseId,Long examId){
            Course course=courseRepository.findById(courseId).orElseThrow();
            Exam exam=examRepository.findById(examId).orElseThrow();

            if(course.getApprovedExams().contains(exam)){
                return getExamResponse(exam);
            }
            else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Exam not Approved");
    }

    public ExamResponse acceptExam(long examId, Long courseId) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();

        course.approveExam(exam, exam.getExamValidatorId());
        examRepository.save(exam);

        return getExamResponse(exam);
    }

    public ExamResponse rejectExam(long examId, Long courseId, String reason) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        course.rejectExam(exam,exam.getExamValidatorId(),reason);
        examRepository.save(exam);

        return getExamResponse(exam);
    }

    public ExamResponse viewDeniedExam(long examId, Long courseId) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        course.viewWrongExam(exam);
        return getExamResponse(exam);
    }
  
    public ExamResponse modifyWrongExam(long examId, Long courseId, List<QuestionEntity>newQuestions) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        course.modifyQuestions(exam,exam.getQuestions(),newQuestions);

        questionRepository.saveAll(exam.getQuestions());
        examRepository.save(exam);
        return getExamResponse(exam);
    }

    private TeacherResponse getTeacherResponse(Long teacherId) {
        return getTeacherById(teacherId);
    }
    public void sendAndProcessCourse(Long id) {

        this.examProducer.sendCourseResponse((getCourseById(id)));
    }

    private CourseResponse getCourseResponse(Course course){
        List<ExamResponse> approvedExamResponses = new ArrayList<>();
        for (Exam exam : course.getApprovedExams()){
            approvedExamResponses.add(getExamResponse(exam));
        }
        List<ExamResponse> rejectedExamResponses = new ArrayList<>();
        for (Exam exam : course.getRejectedExams()){
            rejectedExamResponses.add(getExamResponse(exam));
        }
        List<ExamResponse> validatingExamResponses = new ArrayList<>();
        for (Exam exam : course.getValidatingExams()){
            validatingExamResponses.add(getExamResponse(exam));
        }

        List<TeacherResponse> teacherResponses = new ArrayList<>();
        for (Long teacherId : course.getTeacherIds()){
            teacherResponses.add(getTeacherResponse(teacherId));
        }
      
        return new CourseResponse(course.getId(), course.getName(), teacherResponses,
                approvedExamResponses, rejectedExamResponses, validatingExamResponses);
    }

    private ExamResponse getExamResponse(Exam exam) {

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

    public SubmissionResponse getSubmissionById(Long id) {
        String url = "https://inno-testvision-grading-abaybzgufxdvh5cy.northeurope-01.azurewebsites.net/submission/" + id;
        ResponseEntity<SubmissionResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});

        return response.getBody();
    }

    public TeacherResponse getTeacherById(Long id) {
        String url = "https://userss-fje9bmb2b3gtdafe.northeurope-01.azurewebsites.net/teacher/" + id;
        examProducer.sendTeacherRequest(id);

        return restTemplate.getForObject(url, TeacherResponse.class);
    }

    // rpc
    public CourseResponse findCourseResponseByExamId(Long examId) {

        Course course = courseRepository.findByApprovedExamsId(examId);
        return getCourseResponse(course);

    }
}
