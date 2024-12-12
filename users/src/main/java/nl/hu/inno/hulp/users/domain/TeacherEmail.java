package nl.hu.inno.hulp.users.domain;

import lombok.Data;
import org.springframework.data.aerospike.mapping.Document;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Document
public class TeacherEmail {
    private String email;
    private final static String emailRegex = "^[a-z]+\\.[a-z]+@hu\\.nl$";

    public TeacherEmail(String email) {
     Pattern EmailPattern= Pattern.compile(emailRegex);
     Matcher EmailMatcher = EmailPattern.matcher(email);
    if (EmailMatcher.matches()) {
        this.email = email;
    } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public String getEmailString() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherEmail that = (TeacherEmail) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    public TeacherEmail() {
    }
}
