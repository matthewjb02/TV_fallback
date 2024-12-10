package nl.hu.inno.hulp.users.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Teacher extends User {
    @GeneratedValue
    @Id
    private long id;

    @Embedded
    private TeacherEmail email;

    protected Teacher() {
        super();
    }

    public Teacher(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = new TeacherEmail(email);
    }
}