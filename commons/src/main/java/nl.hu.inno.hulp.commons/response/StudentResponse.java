package nl.hu.inno.hulp.commons.response;

import java.io.Serializable;

public class StudentResponse implements Serializable {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final boolean extraTimeRight;
    private final String email;

    public StudentResponse(Long id, String firstName, String lastName, boolean extraTimeRight, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.extraTimeRight = extraTimeRight;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isExtraTimeRight() {
        return extraTimeRight;
    }

    public String getEmail() {
        return email;
    }
}
