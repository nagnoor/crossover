package com.crossover.techtrial;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Util {

    public LocalDateTime convertStringToLocalDateTime(String strdateTime) {
        return LocalDateTime.parse(strdateTime, DateTimeFormatter.ISO_DATE_TIME);

    }

    public boolean isEndTimeAfterStartTime(String startTime, String endTime) {
        LocalDateTime startTm = convertStringToLocalDateTime(startTime);
        LocalDateTime endTm = convertStringToLocalDateTime(endTime);
        return endTm.isAfter(startTm);
    }
}
