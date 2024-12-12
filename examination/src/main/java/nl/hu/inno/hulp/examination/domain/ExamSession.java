package nl.hu.inno.hulp.examination.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AccessLevel;
import lombok.Getter;
import nl.hu.inno.hulp.commons.enums.ExamState;
import nl.hu.inno.hulp.commons.exception.PasswordIncorrectException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@Document(collection = "Examsession")
@Getter
public class ExamSession {
    @Id
    private String id = UUID.randomUUID().toString();

    @Field("examination_id")
    private String examinationId;

    @Field("student_id")
    private String studentId;

    private String examId;

    private ExamDate examDate;

    private ExamState state;
    private int duration;

    @Getter(AccessLevel.NONE)
    private String securedPassword;

    protected ExamSession() {
    }

    public ExamSession(String id, Examination context, String studentId, boolean extraTimeRight) {
        this.id = id;
        this.state = ExamState.Published;
        this.examinationId = context.getId();
        this.duration = context.totalDuration(extraTimeRight);
        this.examId = context.getExamId();
        this.securedPassword = hashPassword(context.getPassword());
        this.examDate = context.getExamDate();
        this.studentId = studentId;
    }

    public ExamSession startSession(String password) {
        if (verifyPassword(password)) {
            changeState(ExamState.Active);
            return this;
        }
        throw new PasswordIncorrectException("This password is incorrect.");
    }

    public ExamSession endSession() {
        changeState(ExamState.Completed);
        return this;
    }

    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public boolean verifyPassword(String inputPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(inputPassword, securedPassword);
    }

    public void changeState(ExamState state) {
        this.state = state;
    }
}