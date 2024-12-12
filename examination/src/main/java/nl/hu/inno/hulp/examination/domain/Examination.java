package nl.hu.inno.hulp.examination.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "Examination")
@Getter
public class Examination {
    @Id
    private String id = UUID.randomUUID().toString();

    @Field("candidates")
    private List<String> candidates = new ArrayList<>();

    @Field("exam_sessions")
    private List<ExamSession> examSessions = new ArrayList<>();

    @Field("exam_id")
    private String examId;

    @Field("course_id")
    private String courseId;

    private String name;
    private String password;

    private ExamDate examDate;

    private int duration;
    private int extraTime;

    protected Examination() {
    }

    public Examination(String id, String name, String examId, String password, ExamDate examDate, int duration, int extraTime, String courseId) {
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

    public List<String> selectCandidates(List<String> candidates) {
        this.candidates.addAll(candidates);
        return this.candidates;
    }

    public List<String> selectCandidate(String studentId) {
        if (!candidates.contains(studentId)) {
            this.candidates.add(studentId);
        }
        return this.candidates;
    }

    public List<String> removeCandidates(List<String> candidates) {
        this.candidates.removeAll(candidates);
        return this.candidates;
    }

    public List<String> removeCandidate(String studentId) {
        this.candidates.remove(studentId);
        return this.candidates;
    }

    public boolean storeExamSession(ExamSession examSession) {
        return this.examSessions.add(examSession);
    }

    public boolean validateStudent(String studentId) {
        return candidates.contains(studentId);
    }
}