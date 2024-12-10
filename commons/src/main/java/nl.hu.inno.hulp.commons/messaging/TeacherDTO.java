package nl.hu.inno.hulp.commons.messaging;


import lombok.Data;
import lombok.Getter;

@Data
public class TeacherDTO {
    private Long id;

    public TeacherDTO(Long id) {
        this.id = id;
    }

}