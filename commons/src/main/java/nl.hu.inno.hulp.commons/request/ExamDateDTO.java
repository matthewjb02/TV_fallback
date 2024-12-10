package nl.hu.inno.hulp.commons.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ExamDateDTO {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    public Date startDate;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    public Date endDate;
}
