package com.captivatelabs.grails.timezone.detection

final class TimeZoneUtil {

    static Date offsetDate(TimeZone fromTimezone, TimeZone toTimezone, Date dateToOffset) {
        long fromOffset = fromTimezone.getRawOffset() + (fromTimezone.inDaylightTime(dateToOffset) ? fromTimezone.getDSTSavings() : 0)
        long toOffset = toTimezone.getRawOffset() + (toTimezone.inDaylightTime(dateToOffset) ? toTimezone.getDSTSavings() : 0)
        long adjustment = toOffset - fromOffset
        return new Date(dateToOffset.time + adjustment)
    }
}
