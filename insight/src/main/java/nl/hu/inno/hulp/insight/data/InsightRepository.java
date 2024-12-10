package nl.hu.inno.hulp.insight.data;

import nl.hu.inno.hulp.insight.domain.Insight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsightRepository extends JpaRepository<Insight, Long> {
}
