package nl.hu.inno.hulp.examination.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Examination")
@Getter
public class Examination {
    @Id
    private Long id;

    @Field("candidates")
    private List<Long> candidates = new ArrayList<>();

    @Field("exam_sessions")
    private List<ExamSession> examSessions = new ArrayList<>();

    @Field("exam_id")
    private Long examId;

    @Field("course_id")
    private Long courseId;

    private String name;
    private String password;

    private ExamDate examDate;

    private int duration;
    private int extraTime;

    protected Examination() {
    }

    public Examination(Long id, String name, Long examId, String password, ExamDate examDate, int duration, int extraTime, Long courseId) {
        this.id = id;
        this.name = name;
        this.examId = examId;
        this.password = password;
        this.examDate = examDate;
        this.duration = duration;
        this.extraTime = extraTime;
        this.courseId = courseId;
    }

    public int totalDuration(boolean extraTimeRight) {
        return extraTimeRight ? duration + extraTime : duration;
    }

    public List<Long> selectCandidates(List<Long> candidates) {
        this.candidates.addAll(candidates);
        return this.candidates;
    }

    public List<Long> selectCandidate(Long studentId) {
        if (!candidates.contains(studentId)) {
            this.candidates.add(studentId);
        }
        return this.candidates;
    }

    public List<Long> removeCandidates(List<Long> candidates) {
        this.candidates.removeAll(candidates);
        return this.candidates;
    }

    public List<Long> removeCandidate(Long studentId) {
        this.candidates.remove(studentId);
        return this.candidates;
    }

    public boolean storeExamSession(ExamSession examSession) {
        return this.examSessions.add(examSession);
    }

    public boolean validateStudent(Long studentId) {
        return candidates.contains(studentId);
    }
}