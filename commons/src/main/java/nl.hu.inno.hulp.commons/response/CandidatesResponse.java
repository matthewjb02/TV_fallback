package nl.hu.inno.hulp.commons.response;

import java.util.List;

public class CandidatesResponse {
    public final Long examinationId;
    public final String name;
    public final List<StudentResponse> candidates;
    public CandidatesResponse(Long examinationId, String name, List<StudentResponse> candidates) {
        this.examinationId = examinationId;
        this.name = name;
        this.candidates = candidates;
    }

    public Long getExaminationId() {
        return examinationId;
    }

    public String getName() {
        return name;
    }

    public List<StudentResponse> getCandidates() {
        return candidates;
    }
}

