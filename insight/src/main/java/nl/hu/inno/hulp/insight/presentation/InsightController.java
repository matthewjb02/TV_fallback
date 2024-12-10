package nl.hu.inno.hulp.insight.presentation;

import nl.hu.inno.hulp.commons.request.InsightRequest;
import nl.hu.inno.hulp.commons.response.IndividualInsightResponse;
import nl.hu.inno.hulp.commons.response.InsightResponse;
import nl.hu.inno.hulp.insight.application.InsightService;
import nl.hu.inno.hulp.insight.domain.Insight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insights")
public class InsightController {
    private final InsightService insightService;
@Autowired
    public InsightController(InsightService insightService) {
        this.insightService = insightService;
    }
    @PostMapping
    public InsightResponse createInsight(@RequestBody InsightRequest insight) {
    return this.insightService.createInsight(insight);
    }
    @GetMapping("/{id}")
    public InsightResponse getInsightById(@PathVariable Long id) {
    return this.insightService.getInsight(id);
    }
    @DeleteMapping("/{id}")
    public void deleteInsightById(@PathVariable Long id) {
    this.insightService.deleteInsight(id);
    }
    @PatchMapping("/{id}/open-candidate-insights")
    public InsightResponse letStudentsViewInsight(@PathVariable Long id) {
    return this.insightService.letStudentsViewInsight(id);
    }
    @GetMapping("/candidate-insights/view/{id}")
    public IndividualInsightResponse studentViewsInsight(@PathVariable Long id) {
    return this.insightService.studentViewsInsight(id);
    }
    @PatchMapping("/{insightId}/candidate-insights/{id}/feedback")
    public IndividualInsightResponse studentFeedBackOnInsight(@PathVariable Long insightId,@PathVariable Long id,@RequestBody String feedBack) {
    return this.insightService.studentFeedbackOnInsight(insightId,id, feedBack);
    }
}
