package nl.hu.inno.hulp.commons.request;

public record CreateExamination(Long id, String name,Long courseId, Long examId, String password, ExamDateDTO examDate, int duration, int extraTime) {
}
