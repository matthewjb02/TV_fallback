package nl.hu.inno.hulp.commons.request;

public record AnswerRequest(Long examSessionId, int questionNr, Object answer) {
}
