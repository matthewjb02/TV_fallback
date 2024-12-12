package nl.hu.inno.hulp.users.domain;

import lombok.Data;
import org.springframework.data.aerospike.mapping.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Document
public class StudentEmail {
    private String email;
    private final static String emailRegex = "^[a-z]+\\.[a-z]+@student\\.hu\\.nl$";

    public StudentEmail(String email) {
        Pattern EmailPattern= Pattern.compile(emailRegex);
        Matcher EmailMatcher = EmailPattern.matcher(email);
        if (EmailMatcher.matches()) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    protected StudentEmail() {
    }

    public String getEmailString() {
            return email;
    }
}