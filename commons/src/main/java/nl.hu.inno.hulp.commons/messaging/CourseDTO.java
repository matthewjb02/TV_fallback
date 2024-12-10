package nl.hu.inno.hulp.commons.messaging;

import lombok.Data;

import java.util.List;

@Data
public class CourseDTO {
    private Long id;
    private List<TeacherDTO> teachers;

    public CourseDTO(Long id, List<TeacherDTO> teachers) {
        this.id = id;
        this.teachers = teachers;
    }
}
