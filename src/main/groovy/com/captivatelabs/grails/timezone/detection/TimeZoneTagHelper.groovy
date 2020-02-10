package com.captivatelabs.grails.timezone.detection

import groovy.transform.CompileStatic
import org.grails.plugins.web.DefaultGrailsTagDateHelper

import java.time.ZoneId

@CompileStatic
class TimeZoneTagHelper extends DefaultGrailsTagDateHelper {
    /**
     * Override to always return a timezone, not Zone ID.
     *
     * @param timeZone
     * @return
     */
    @Override
    Object getTimeZone(Object timeZone) {
        Object timezoneObj = super.getTimeZone(timeZone)
        if (!(timezoneObj instanceof TimeZone)) {
            return TimeZone.getTimeZone(ZoneId.systemDefault())
        }
        return timezoneObj
    }
}
