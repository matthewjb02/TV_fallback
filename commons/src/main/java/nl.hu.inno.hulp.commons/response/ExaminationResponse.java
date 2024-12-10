package nl.hu.inno.hulp.commons.response;

import lombok.Getter;
import nl.hu.inno.hulp.commons.request.ExamDateDTO;
import java.util.List;

@Getter
public class ExaminationResponse {
    private final Long id;

    private final List<StudentResponse> candidates;

    private final List<ExamSessionResponse> examSessions;

    private final ExamResponse exam;

    private final CourseResponse course;

    private final String name;
    private final String password;

    private final ExamDateDTO examDate;

    private final int duration;
    private final int extraTime;

    public ExaminationResponse(Long id, List<StudentResponse> candidates, List<ExamSessionResponse> examSessions, ExamResponse exam, String name, String password, ExamDateDTO examDate, int duration, int extraTime,CourseResponse course) {
        this.id = id;
        this.candidates = candidates;
        this.examSessions = examSessions;
        this.exam = exam;
        this.name = name;
        this.password = password;
        this.examDate = examDate;
        this.duration = duration;
        this.extraTime = extraTime;this.course = course;
    }

    public ExamResponse getExam() {
        return exam;
    }
}