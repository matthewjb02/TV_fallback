package nl.hu.inno.hulp.commons.request;

public record StartExamSession(Long examsessionId, Long examinationId, Long studentId, String password) {
}
