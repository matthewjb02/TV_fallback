package nl.hu.inno.hulp.users.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Student extends User {
    @Id
    @GeneratedValue
    private Long id;
    private boolean extraTimeRight = false;
    @Embedded
    private StudentEmail email;

    protected Student() {
        super();
    }

    public Student(String firstName, String lastName, boolean extraTimeRight,String email) {
        super(firstName, lastName);
        this.extraTimeRight = extraTimeRight;
        this.email = new StudentEmail(email);
    }

    public void changeExtraTimeRight(boolean right) {
        this.extraTimeRight = right;
    }

    public boolean isExtraTimeRight() {
        return extraTimeRight;
    }
}
