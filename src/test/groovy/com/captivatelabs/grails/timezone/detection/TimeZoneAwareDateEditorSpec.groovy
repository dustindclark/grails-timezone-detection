package com.captivatelabs.grails.timezone.detection

import org.grails.web.util.WebUtils
import spock.lang.Specification

class TimeZoneAwareDateEditorSpec extends Specification {

    def setup() {
        WebUtils.metaClass.static.retrieveGrailsWebRequest = {
            return [session: [timeZone: TimeZone.getTimeZone("America/Chicago")]]
        }
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    void "test data binding client to server"() {
        when:
        Date date = new Date(116, 11, 1, 8, 0)
        Calendar cal = new GregorianCalendar()
        cal.setTime(date)
        Date expectedDate = new Date(116, 11, 1, 14, 0)

        then:
        (new TimeZoneAwareDateEditor()).getDate(cal) == expectedDate
    }
}
