package nl.hu.inno.hulp.users.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Embeddable
public class StudentEmail {
    @Column(name = "email", unique = true, nullable = false)
        private String email;
        private final static String emailRegex = "^[a-z]+\\.[a-z]+@student\\.hu\\.nl$";

        public StudentEmail(String email) {
            Pattern EmailPattern= Pattern.compile(emailRegex);
            Matcher EmailMatcher = EmailPattern.matcher(email);
            if (EmailMatcher.matches()) {
                this.email = email;
            }
            else{
                throw new IllegalArgumentException("Invalid email format");
            }
        }

    protected StudentEmail() {

    }

    public String getEmailString() {
            return email;
        }

    }
