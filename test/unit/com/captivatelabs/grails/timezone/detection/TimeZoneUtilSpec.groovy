package com.captivatelabs.grails.timezone.detection

import spock.lang.Specification

class TimeZoneUtilSpec extends Specification{

    void "test central to UTC during DST"() {
        when:
        TimeZone fromTimezone = TimeZone.getTimeZone("America/Chicago")
        TimeZone toTimezone = TimeZone.getTimeZone("UTC")
        Date date = new Date(116, 5, 1, 8, 0)
        Date expectedDate = new Date(116, 5, 1, 13, 0)

        then:
        TimeZoneUtil.offsetDate(fromTimezone, toTimezone, date) == expectedDate
    }

    void "test central to UTC outside of DST"() {
        when:
        TimeZone fromTimezone = TimeZone.getTimeZone("America/Chicago")
        TimeZone toTimezone = TimeZone.getTimeZone("UTC")
        Date date = new Date(116, 12, 1, 8, 0)
        Date expectedDate = new Date(116, 12, 1, 14, 0)

        then:
        TimeZoneUtil.offsetDate(fromTimezone, toTimezone, date) == expectedDate
    }

    void "test UTC to central outside of DST"() {
        when:
        TimeZone fromTimezone = TimeZone.getTimeZone("UTC")
        TimeZone toTimezone = TimeZone.getTimeZone("America/Chicago")
        Date date = new Date(116, 12, 1, 14, 0)
        Date expectedDate = new Date(116, 12, 1, 8, 0)

        then:
        TimeZoneUtil.offsetDate(fromTimezone, toTimezone, date) == expectedDate
    }

    void "test UTC to central during DST"() {
        when:
        TimeZone fromTimezone = TimeZone.getTimeZone("UTC")
        TimeZone toTimezone = TimeZone.getTimeZone("America/Chicago")
        Date date = new Date(116, 5, 1, 13, 0)
        Date expectedDate = new Date(116, 5, 1, 8, 0)

        then:
        TimeZoneUtil.offsetDate(fromTimezone, toTimezone, date) == expectedDate
    }

    void "test UTC to central, crossing end of DST"() {
        when:
        TimeZone fromTimezone = TimeZone.getTimeZone("UTC")
        TimeZone toTimezone = TimeZone.getTimeZone("America/Chicago")
        Date date = new Date(116, 10, 6, 3, 0)
        Date expectedDate = new Date(116, 10, 5, 22, 0)

        then:
        TimeZoneUtil.offsetDate(fromTimezone, toTimezone, date) == expectedDate
    }

    void "test UTC to central, crossing beginning of DST"() {
        when:
        TimeZone fromTimezone = TimeZone.getTimeZone("UTC")
        TimeZone toTimezone = TimeZone.getTimeZone("America/Chicago")
        Date date = new Date(116, 2, 13, 3, 0)
        Date expectedDate = new Date(116, 2, 12, 21, 0)

        then:
        TimeZoneUtil.offsetDate(fromTimezone, toTimezone, date) == expectedDate
    }

    void "test no null pointers"() {
        expect:
        TimeZoneUtil.offsetDate(null, null, null) == null
    }



}
