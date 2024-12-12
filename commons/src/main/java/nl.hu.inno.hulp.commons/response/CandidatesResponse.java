package nl.hu.inno.hulp.commons.response;

import java.util.List;

public class CandidatesResponse {
    public final String examinationId;
    public final String name;
    public final List<StudentResponse> candidates;
    public CandidatesResponse(String examinationId, String name, List<StudentResponse> candidates) {
        this.examinationId = examinationId;
        this.name = name;
        this.candidates = candidates;
    }

    public String getExaminationId() {
        return examinationId;
    }

    public String getName() {
        return name;
    }

    public List<StudentResponse> getCandidates() {
        return candidates;
    }
}

