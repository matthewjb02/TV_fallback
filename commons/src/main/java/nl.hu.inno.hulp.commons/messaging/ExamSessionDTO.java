package nl.hu.inno.hulp.commons.messaging;

import lombok.Data;
import lombok.Getter;
import nl.hu.inno.hulp.commons.enums.ExamState;

@Data
public class ExamSessionDTO {
    private Long id;
    private Long examination;
    private Long student;
    private Long exam;
    private ExamState state;
    private int duration;


    public ExamSessionDTO(Long id, Long examination, Long student, Long exam, ExamState state, int duration) {
        this.id = id;
        this.examination = examination;
        this.student = student;
        this.exam = exam;
        this.state = state;
        this.duration = duration;
    }

}