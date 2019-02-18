package cn.luowq.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @Author: rowan
 * @Date: 2019/2/18 17:14
 * @Description:
 */
public final class DateUtils {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    private DateUtils() {
    }

    public static String formatDate(Date d) {
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
    }

    public static String formatDateTime(Date d) {
        return formatDateTime(OffsetDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault()));
    }

    public static String formatDateTime(long ms) {
        return formatDateTime(OffsetDateTime.ofInstant(Instant.ofEpochMilli(ms), ZoneId.systemDefault()));
    }

    public static String formatDateTime(OffsetDateTime dt) {
        return DATETIME_FORMATTER.format(dt);
    }

    public static String formatDateTimeNullSafe(Date date) {
        return date == null ? "" : formatDateTime(date);
    }

    public static Date longToDate(Long time) {
        return time == null ? null : new Date(time);
    }

    public static Long dateToLong(Date date) {
        return date == null ? null : date.getTime();
    }

    public static Date parseDate(String s) {
        return Date.from(parseLocalDate(s).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date parseDateQuietly(String s) {
        Date date = null;
        if (s != null) {
            try {
                date = parseDate(s);
            } catch (RuntimeException var3) {
                ;
            }
        }

        return date;
    }

    public static LocalDate parseLocalDate(String s) {
        try {
            return LocalDate.parse(s);
        } catch (DateTimeParseException var2) {
            throw MessageException.of("The date '" + s + "' does not respect format '" + "yyyy-MM-dd" + "'", var2);
        }
    }

    public static LocalDate parseLocalDateQuietly(String s) {
        LocalDate date = null;
        if (s != null) {
            try {
                date = parseLocalDate(s);
            } catch (RuntimeException var3) {
                ;
            }
        }

        return date;
    }

    public static Date parseDateTime(String s) {
        return Date.from(parseOffsetDateTime(s).toInstant());
    }

    public static OffsetDateTime parseOffsetDateTime(String s) {
        try {
            return OffsetDateTime.parse(s, DATETIME_FORMATTER);
        } catch (DateTimeParseException var2) {
            throw MessageException.of("The date '" + s + "' does not respect format '" + "yyyy-MM-dd'T'HH:mm:ssZ" + "'", var2);
        }
    }

    public static Date parseDateTimeQuietly(String s) {
        Date datetime = null;
        if (s != null) {
            try {
                datetime = parseDateTime(s);
            } catch (RuntimeException var3) {
                ;
            }
        }

        return datetime;
    }

    public static OffsetDateTime parseOffsetDateTimeQuietly(String s) {
        OffsetDateTime datetime = null;
        if (s != null) {
            try {
                datetime = parseOffsetDateTime(s);
            } catch (RuntimeException var3) {
                ;
            }
        }

        return datetime;
    }

    public static Date parseDateOrDateTime(String stringDate) {
        if (stringDate == null) {
            return null;
        } else {
            OffsetDateTime odt = parseOffsetDateTimeQuietly(stringDate);
            if (odt != null) {
                return Date.from(odt.toInstant());
            } else {
                LocalDate ld = parseLocalDateQuietly(stringDate);
//                Preconditions.checkArgument(ld != null, "Date '%s' cannot be parsed as either a date or date+time", new Object[]{stringDate});
                return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
        }
    }

    public static Date parseStartingDateOrDateTime(String stringDate) {
        return parseDateOrDateTime(stringDate);
    }

    @Deprecated
    public static Date parseEndingDateOrDateTime(String stringDate) {
        // TODO
        return null;
    }

    public static Date addDays(Date date, int numberOfDays) {
        return Date.from(date.toInstant().plus((long)numberOfDays, ChronoUnit.DAYS));
    }

    public static Date truncateToSeconds(Date d) {
        return d == null ? null : truncateToSecondsImpl(d);
    }

    public static long truncateToSeconds(long dateTime) {
        return truncateToSecondsImpl(new Date(dateTime)).getTime();
    }

    private static Date truncateToSecondsImpl(Date d) {
        Instant instant = d.toInstant();
        instant = instant.truncatedTo(ChronoUnit.SECONDS);
        return Date.from(instant);
    }
}
