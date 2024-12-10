package nl.hu.inno.hulp.commons.request;

import java.util.List;

public record InsightRequest(List<Long> submisisonIds, List<Long> candidateIds) {
}
