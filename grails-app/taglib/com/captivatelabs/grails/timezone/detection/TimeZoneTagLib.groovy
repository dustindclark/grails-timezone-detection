package com.captivatelabs.grails.timezone.detection

class TimeZoneTagLib {
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
        def formTagLib = getDefaultFormTagLib()
        if (getCurrentTimezone() && attrs.value) {
            attrs.value = TimeZoneUtil.offsetDate(Calendar.getInstance().timeZone, getCurrentTimezone(), attrs.value)
        }
        formTagLib.datePicker.call(attrs)
        if (!attrs.suppressTimezone) {
            out << "<span class='tz'>"
            out << tz.show()
            out << "</span>"
        }
    }

    def formatDate = { attrs ->
        def formatTagLib = getDefaultFormatTagLib()
        if (!attrs.timeZone) {
            attrs.timeZone = getCurrentTimezone()
        }
        out << formatTagLib.formatDate.call(attrs)
    }

    private TimeZone getCurrentTimezone() {
        return request.getSession(true).timeZone
    }

    private def getDefaultFormTagLib() {
        return grailsApplication.mainContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.FormTagLib')
    }

    private getDefaultFormatTagLib() {
        return grailsApplication.mainContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.FormatTagLib')
    }
}
