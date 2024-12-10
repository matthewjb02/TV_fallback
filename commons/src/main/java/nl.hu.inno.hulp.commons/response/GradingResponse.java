package nl.hu.inno.hulp.commons.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class GradingResponse implements Serializable {
    private Long id;
    private double grade;
    private String comments;

    public GradingResponse(Long id, double grade, String comments){
        this.id = id;
        this.grade = grade;
        this.comments = comments;
    }
}
