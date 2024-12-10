package nl.hu.inno.hulp.commons.messaging;

import lombok.Data;

@Data
public class SubmissionDTO {
    private Long id;

    public SubmissionDTO(Long id) {
        this.id = id;
    }


}
