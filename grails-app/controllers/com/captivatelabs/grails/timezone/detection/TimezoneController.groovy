package com.captivatelabs.grails.timezone.detection

import grails.converters.JSON

class TimezoneController {

    def set() {
        session.timeZone = TimeZone.getTimeZone(params.tz) ?: "UNKNOWN"
        log.debug "Timezone set to ${session.timeZone}."
        if (params.r) {
            redirect url: params.r.replace(request.contextPath, '')
        } else if (request.header('referer')) {
            redirect url: request.getHeader('referer')
        } else if (grailsApplication.config.grails.serverURL) {
            redirect url: grailsApplication.config.grails.serverURL
        } else {
            redirect url: '/'
        }
    }

    def show() {
        render text: session.timeZone as JSON
    }
}
