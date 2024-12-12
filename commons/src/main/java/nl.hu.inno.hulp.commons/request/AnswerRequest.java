package nl.hu.inno.hulp.commons.request;

public record AnswerRequest(String examSessionId, int questionNr, Object answer) {
}
