package nl.hu.inno.hulp.commons.response;

import lombok.Getter;
import nl.hu.inno.hulp.commons.response.ExamResponse;
import nl.hu.inno.hulp.commons.response.TeacherResponse;

import java.util.List;

@Getter
public class CourseResponse {
    private Long id;
    private String name;
    private List<TeacherResponse> teachers;
    private List<ExamResponse> approvedExams;
    private List<ExamResponse> rejectedExams;
    private List<ExamResponse> validatingExams;

    protected CourseResponse() {
    }

    public CourseResponse(Long id, String name, List<TeacherResponse> teachers, List<ExamResponse> approvedExams, List<ExamResponse> rejectedExams, List<ExamResponse> validatingExams) {
        this.id = id;
        this.name = name;
        this.teachers = teachers;
        this.approvedExams = approvedExams;
        this.rejectedExams = rejectedExams;
        this.validatingExams = validatingExams;
    }
}
