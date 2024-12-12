package nl.hu.inno.hulp.commons.request;

public record CreateExamination(String id, String name, String courseId, String examId, String password, ExamDateDTO examDate, int duration, int extraTime) {
}
