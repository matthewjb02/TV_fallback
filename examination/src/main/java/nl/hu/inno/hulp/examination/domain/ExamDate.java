package nl.hu.inno.hulp.examination.domain;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Getter
public class ExamDate {
    @Field("begin_date")
    private Date beginDate;

    @Field("end_date")
    private Date endDate;

    protected ExamDate() {
    }

    public ExamDate(Date beginDate, Date endDate) {
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExamDate examDate)) return false;
        return Objects.equals(beginDate, examDate.beginDate) &&
                Objects.equals(endDate, examDate.endDate);
    }

    public boolean checkDate() {
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return currentDate.before(endDate) && currentDate.after(beginDate);
    }

}