package nl.hu.inno.hulp.commons.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class GradingResponse implements Serializable {
    private String id;
    private double grade;
    private String comments;

    public GradingResponse(String id, double grade, String comments){
        this.id = id;
        this.grade = grade;
        this.comments = comments;
    }
}
