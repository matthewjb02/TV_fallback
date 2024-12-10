package nl.hu.inno.hulp.commons.response;

import lombok.Getter;

@Getter
public class TeacherResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    protected TeacherResponse() {

    }
    public TeacherResponse(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}