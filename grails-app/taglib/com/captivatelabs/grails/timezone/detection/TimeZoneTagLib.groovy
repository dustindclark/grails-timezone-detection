package com.captivatelabs.grails.timezone.detection

import org.grails.plugins.web.taglib.FormTagLib as GrailsFormTagLib
import org.grails.plugins.web.taglib.FormatTagLib as GrailsFormatTagLib

import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.Temporal

class TimeZoneTagLib {
    public static final String TIMEZONE_FIELD = "zoneid"
    static namespace = 'tz'

    def detect = {
        try {
            //Don't do this on a POST because posted data will be lost.
            if (!getCurrentTimezone() && request.method == 'GET') {
                out << asset.javascript(src: 'timezone-detection.js')
                out << "<script type='text/javascript'>"
                out << "doTimezoneDetection('${createLink(controller: 'timezone', action: 'set')}/', '${request.forwardURI}');"
                out << "</script>"
            }
        } catch (Exception ex) {
            log.error("An error occurred attempting to detect the timezone: ${ex.message}.", ex)
        }
    }

    def show = { final attrs ->
        try {
            TimeZone timeZone = getCurrentTimezone()
            if (!timeZone) {
                return
            }
            if (attrs.attribute) {
                out << timeZone."${attrs.attribute}"
            } else {
                out << timeZone.getDisplayName(timeZone.inDaylightTime(new Date()), TimeZone.SHORT, request.getLocale())
            }
        } catch (Exception ex) {
            log.error("An error occurred attempting to show the timezone: ${ex.message}.", ex)
        }
    }

    def datePicker = { attrs ->
        GrailsFormTagLib formTagLib = getDefaultFormTagLib()
        TimeZone timezone = getCurrentTimezone()
        if (timezone) {
            out << g.hiddenField(name: "${attrs.name}_${TIMEZONE_FIELD}", value: getCurrentTimezone().toZoneId().id)
            if (attrs.value) {
                if (attrs.value instanceof Date) {
                    attrs.value = TimeZoneUtil.offsetDate(Calendar.getInstance().timeZone, timezone, attrs.value)
                } else if (attrs.value instanceof Instant) {
                    attrs.value = LocalDateTime.ofInstant(attrs.value, timezone.toZoneId())
                }
            }
        }

        formTagLib.datePicker.call(attrs)

        if (!attrs.suppressTimezone) {
            out << "<span class='tz'>"
            out << tz.show()
            out << "</span>"
        }
    }

    def formatDate = { attrs ->
        GrailsFormatTagLib formatTagLib = getDefaultFormatTagLib()
        if (!attrs.timeZone) {
            attrs.timeZone = getCurrentTimezone()?.toZoneId()
        }
        out << formatTagLib.formatDate.call(attrs)
    }

    private TimeZone getCurrentTimezone() {
        return request.getSession(true).timeZone
    }

    private def getDefaultFormTagLib() {
        return grailsApplication.mainContext.getBean(GrailsFormTagLib.class.name)
    }

    private getDefaultFormatTagLib() {
        return grailsApplication.mainContext.getBean(GrailsFormatTagLib.class.name)
    }
}
