package nl.hu.inno.hulp.users.domain;

import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.aerospike.mapping.Document;
import org.springframework.data.aerospike.mapping.Field;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Document
public class Student extends User {
    @Id
    private String id = UUID.randomUUID().toString();
    private boolean extraTimeRight = false;
    @Field("email")
    private StudentEmail email;

    protected Student() {
        super();
    }

    public Student(String firstName, String lastName, boolean extraTimeRight, String email) {
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
