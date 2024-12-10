package nl.hu.inno.hulp.commons.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class  ExamSessionRequest implements Serializable {
    public Long examSessionId;

    public ExamSessionRequest(Long examSessionId) {
        this.examSessionId = examSessionId;

    }

}
