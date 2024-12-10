package nl.hu.inno.hulp.insight.data;

import nl.hu.inno.hulp.insight.domain.IndividualInsight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualInsightRepository extends JpaRepository<IndividualInsight, Long> {
}
