package nl.hu.inno.hulp.users.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.aerospike.mapping.Document;
import org.springframework.data.aerospike.mapping.Field;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Document
public class Teacher extends User {
    @Id
    private String id = UUID.randomUUID().toString();
    @Field("email")
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