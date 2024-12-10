package nl.hu.inno.hulp.examination.application.service;

import nl.hu.inno.hulp.commons.enums.ExamState;
import nl.hu.inno.hulp.commons.exception.ExamSessionNotStored;
import nl.hu.inno.hulp.commons.exception.ExaminationInactiveException;
import nl.hu.inno.hulp.commons.exception.NoExamSessionFoundException;
import nl.hu.inno.hulp.commons.exception.NotAllowedException;
import nl.hu.inno.hulp.commons.request.AnswerRequest;
import nl.hu.inno.hulp.commons.request.ExamSessionRequest;
import nl.hu.inno.hulp.commons.request.StartExamSession;
import nl.hu.inno.hulp.commons.request.UpdateOpenQuestionPointsRequest;
import nl.hu.inno.hulp.commons.response.ExamSessionResponse;
import nl.hu.inno.hulp.commons.response.QuestionResponse;
import nl.hu.inno.hulp.commons.response.StudentResponse;
import nl.hu.inno.hulp.commons.response.SubmissionResponse;
import nl.hu.inno.hulp.examination.data.ExamSessionRepository;
import nl.hu.inno.hulp.examination.domain.ExamSession;
import nl.hu.inno.hulp.publisher.ExaminationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExamSessionService {
    private final ExamSessionRepository examSessionRepository;
    private final ExaminationService examinationService;
    private final RestTemplate restTemplate;
    private final ExaminationProducer examinationProducer;
    @Autowired
    public ExamSessionService(ExamSessionRepository examSessionRepository, ExaminationService examinationService, RestTemplate restTemplate,
                              ExaminationProducer examinationProducer) {
        this.examSessionRepository = examSessionRepository;
        this.examinationService = examinationService;
        this.restTemplate = restTemplate;
        this.examinationProducer = examinationProducer;
    }

    public ExamSessionResponse startExamSession(StartExamSession request) {
        if (examinationService.validatingStudent(request)) {
            ExamSession examSession = new ExamSession(request.examsessionId(),examinationService.getExaminationById(request.examinationId()),
                    request.studentId(), true);

            examSession.startSession(request.password());
            examSessionRepository.save(examSession);

            return getExamSessionResponse(examSession.getId());
        }

        throw new NotAllowedException("Student is not allowed to start session because student is not a candidate.");
    }

    public QuestionResponse seeQuestion(Long id, Long questionId)  {
        ExamSession examSession = getExamSessionById(id);

        if (examSession.getState() == ExamState.Active) {
            examinationProducer.sendQuestionRequest(examSession.getId(), examSession.getExamId(), questionId);
            return new QuestionResponse(10, "hello world");
        } else {
            throw new ExaminationInactiveException("This exam is inactive");
        }
    }

    public ExamSessionResponse enterAnswer(AnswerRequest request) {
        ExamSession examSession = getExamSessionById(request.examSessionId());

        if (examSession.getState() == ExamState.Active) {
            examinationProducer.sendAnswerRequest(request);
            examSessionRepository.save(examSession);
            return getExamSessionResponse(examSession.getId());
        } else {
            throw new ExaminationInactiveException("This exam is inactive");
        }
    }

    public ExamSessionResponse endExamSession(ExamSessionRequest request) {
        ExamSession examSession = getExamSessionById(request.getExamSessionId());

        if (examSession.getState() == ExamState.Active) {
            examSession.endSession();

            if (!examinationService.storeExamSession(examSession)) {
                throw new ExamSessionNotStored("Exam session can't be stored in examination.");
            }

            String createdSubmissionUrl = "https://inno-testvision-grading-abaybzgufxdvh5cy.northeurope-01.azurewebsites.net/submission/" + examSession.getId();
            SubmissionResponse submission = restTemplate.postForObject(createdSubmissionUrl, examSession.getId(), SubmissionResponse.class);


            assert submission != null;
            examinationProducer.sendAddSubmissionToExamRequest(examSession.getExamId(), submission.getSubmissionId());
            examinationProducer.saveSubmissionRequest(submission.getSubmissionId());

            ExamSessionResponse examSessionResponse = getExamSessionResponse(examSession.getId());
            examinationProducer.endingSessionRequest(examSessionResponse);
            return examSessionResponse;

        } else {
            throw new ExaminationInactiveException("This exam is already completed");
        }
    }

    public ExamSession getExamSessionById(Long id) {
        return examSessionRepository.findById(id)
                .orElseThrow(() -> new NoExamSessionFoundException("No exam session with id: " + id + " found!"));
    }

    public StudentResponse getStudentResponse(Long id) {
        String url = "https://userss-fje9bmb2b3gtdafe.northeurope-01.azurewebsites.net/student/" + id;
//        examinationProducer.sendStudentRequest(id);
        return restTemplate.getForObject(url, StudentResponse.class);
    }

    public ExamSessionResponse getExamSessionResponse(Long id) {
        ExamSession examSession = getExamSessionById(id);
        StudentResponse studentResponse = getStudentResponse(examSession.getStudentId());

        return new ExamSessionResponse(examSession.getId(), examSession.getState(), examSession.getDuration(), studentResponse);
    }

    // used by other modules via rpc
    public void updatePointsOpenQuestion(Long examSessionId, int questionNr, UpdateOpenQuestionPointsRequest request) {
        ExamSession examSession = getExamSessionById(examSessionId);
        Long examId = examSession.getExamId();

        examinationProducer.sendUpdateQuestionGradingRequest(examSessionId, questionNr, request);
    }

}