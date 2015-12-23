package com.captivatelabs.grails.timezone.detection

class TimeZoneTagLib {
    static namespace = 'tz'

    def detect = {
        //Don't do this on a POST because posted data will be lost.
        if (!session.timeZone && request.method == 'GET') {
            out << asset.javascript(src: 'timezone-detection.js')
            out << "<script type='text/javascript'>"
            out << "doTimezoneDetection('${createLink(controller: 'timezone', action: 'set')}/', '${request.forwardURI}');"
            out << "</script>"
        }
    }

    def show = { final attrs ->
        TimeZone timeZone = session.timeZone
        if (!timeZone) {
            return
        }
        if (attrs.attribute) {
            out << timeZone."${attrs.attribute}"
        } else {
            out << timeZone.getDisplayName(timeZone.inDaylightTime(new Date()), TimeZone.SHORT, request.getLocale())
        }
    }

    def datePicker = { attrs ->
        def formTagLib = getDefaultFormTagLib()
        if (session.timeZone) {
            attrs.value = TimeZoneUtil.offsetDate(Calendar.getInstance().timeZone, session.timeZone, attrs.value)
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
            attrs.timeZone = session.timeZone
        }
        out << formatTagLib.formatDate.call(attrs)
    }

    private def getDefaultFormTagLib() {
        return grailsApplication.mainContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.FormTagLib')
    }

    private getDefaultFormatTagLib() {
        return grailsApplication.mainContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.FormatTagLib')
    }
}
