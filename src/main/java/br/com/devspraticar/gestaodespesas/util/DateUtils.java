package br.com.devspraticar.gestaodespesas.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@UtilityClass
public class DateUtils {

    public static LocalDateTime formatLocalDateTime(LocalDateTime localDateTime, String formatDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate);
        return Optional.ofNullable(localDateTime)
            .map(dateFormatted -> LocalDateTime.parse(localDateTime.format(formatter), formatter))
            .orElse(null);
    }

}