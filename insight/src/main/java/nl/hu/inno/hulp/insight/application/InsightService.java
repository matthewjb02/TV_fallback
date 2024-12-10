package nl.hu.inno.hulp.insight.application;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.commons.request.InsightRequest;
import nl.hu.inno.hulp.commons.response.IndividualInsightResponse;
import nl.hu.inno.hulp.commons.response.InsightResponse;
import nl.hu.inno.hulp.insight.data.IndividualInsightRepository;
import nl.hu.inno.hulp.insight.data.InsightRepository;
import nl.hu.inno.hulp.insight.domain.IndividualInsight;
import nl.hu.inno.hulp.insight.domain.Insight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InsightService {
    private final InsightRepository insightRepository;
    private final IndividualInsightRepository individualInsightRepository;

    @Autowired
    public InsightService(InsightRepository insightRepository, IndividualInsightRepository individualInsightRepository) {
        this.insightRepository = insightRepository;
        this.individualInsightRepository = individualInsightRepository;
    }
    public InsightResponse createInsight(InsightRequest insightRequest) {


        Insight insight=new Insight(insightRequest.submisisonIds(),insightRequest.candidateIds());
        individualInsightRepository.saveAll(insight.getIndividualInsights());
        Insight insight1= insightRepository.save(insight);
        return toInsightResponse(insight1.getId());
    }
    public InsightResponse getInsight(Long id) {
        Insight insight= insightRepository.findById(id).orElseThrow();
        return toInsightResponse(insight.getId());
    }
    public void deleteInsight(Long id) {
        insightRepository.deleteById(id);
    }
    public InsightResponse letStudentsViewInsight(Long id) {
        Insight insight= insightRepository.findById(id).orElseThrow();
        insight.letStudentsViewIt();
        insightRepository.save(insight);
        return toInsightResponse(insight.getId());
    }
    public IndividualInsightResponse studentViewsInsight(Long id) {
        IndividualInsight insight= individualInsightRepository.findById(id).orElseThrow();
        return toIndividualInsightResponse(insight.getId());
    }
    public IndividualInsightResponse studentFeedbackOnInsight(Long insightId,Long id,String feedback) {
        Insight insight= insightRepository.findById(insightId).orElseThrow();
        IndividualInsight insight1= individualInsightRepository.findById(id).orElseThrow();
        insight1.addFeedback(insight,feedback);
        insight.addInsightAfterStudentFeedBack(insight1);
        return toIndividualInsightResponse(insight1.getId());
    }

    private List<IndividualInsightResponse> toIndividualInsights(Long insightId) {
        List<IndividualInsightResponse> individualInsightResponses=new ArrayList<>();
        Insight insight= insightRepository.findById(insightId).orElseThrow();
        for (IndividualInsight individualInsight : insight.getIndividualInsights()) {
            individualInsightResponses.add(new  IndividualInsightResponse(individualInsight.getId(), individualInsight.getCandidateId(), individualInsight.getFeedBack(),individualInsight.getSubmissionId()));
        }
        return individualInsightResponses;
    }
    private IndividualInsightResponse toIndividualInsightResponse(Long insightId) {
        IndividualInsight individualInsight= individualInsightRepository.findById(insightId).orElseThrow();
        return new IndividualInsightResponse(individualInsight.getId(),individualInsight.getCandidateId(),individualInsight.getFeedBack(),individualInsight.getSubmissionId());
    }
    private InsightResponse toInsightResponse(Long insightId) {
        Insight insight= insightRepository.findById(insightId).orElseThrow();
        return new InsightResponse(insight.getId(), insight.isCanStudentsViewIt(),insight.getExamGraderId(),insight.getCandidateIds(),insight.getSubmissionIds(),toIndividualInsights(insight.getId()));
    }
}
