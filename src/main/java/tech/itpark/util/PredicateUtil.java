package tech.itpark.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@UtilityClass
public class PredicateUtil {

    public static Object castToRequiredType(Class fieldType, String value) {
        if (UUID.class.isAssignableFrom(fieldType)) {
            return UUID.fromString(value);
        } else if (Double.class.isAssignableFrom(fieldType)) {
            return Double.valueOf(value);
        } else if (Integer.class.isAssignableFrom(fieldType)) {
            return Integer.valueOf(value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, value);
        } else if (Instant.class.isAssignableFrom(fieldType)) {
            return Instant.parse(value);
        } else if (LocalDateTime.class.isAssignableFrom(fieldType)) {
            return LocalDateTime.parse(value);
        } else if (LocalDate.class.isAssignableFrom(fieldType)) {
            return LocalDate.parse(value);
        }

        throw new ClassCastException();
    }
}
