package nl.hu.inno.hulp.commons.response;

import lombok.Getter;
import nl.hu.inno.hulp.commons.enums.ExamState;
import nl.hu.inno.hulp.commons.enums.SubmissionStatus;

import java.io.Serializable;

@Getter
public class ExamSessionResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final ExamState status;
    private final int duration;
    private final StudentResponse student;

    public ExamSessionResponse(String id, ExamState status, int duration, StudentResponse student) {
        this.id = id;
        this.status = status;
        this.duration = duration;
        this.student = student;
    }
}