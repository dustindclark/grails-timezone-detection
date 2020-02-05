package com.captivatelabs.grails.timezone.detection

import asset.pipeline.grails.AssetsTagLib
import grails.core.support.GrailsConfigurationAware
import grails.testing.web.taglib.TagLibUnitTest
import org.grails.plugins.web.DefaultGrailsTagDateHelper
import org.grails.plugins.web.GrailsTagDateHelper
import org.grails.plugins.web.taglib.FormTagLib
import org.grails.plugins.web.taglib.FormatTagLib
import spock.lang.Specification

class TimeZoneTagLibSpec extends Specification implements TagLibUnitTest<TimeZoneTagLib> {
    def setup() {
        defineBeans{
            grailsTagDateHelper(DefaultGrailsTagDateHelper)
        }
        mockTagLib(AssetsTagLib)
        mockTagLib(FormTagLib)
        mockTagLib(FormatTagLib)
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    def cleanup() {
    }

    void "test detect when session.timezone is empty"() {
        when:
        tagLib.session.timeZone = null

        String rendered = tagLib.detect().toString()

        then:
        rendered.contains('doTimezoneDetection')
    }

    void "test detect on POST"() {
        when:
        tagLib.session.timeZone = null
        request.method = "POST"

        String rendered = tagLib.detect().toString()

        then:
        !rendered
    }

    void "test detect after detected"() {
        when:
        tagLib.session.timeZone = TimeZone.getTimeZone("America/Chicago")

        String rendered = tagLib.detect().toString()

        then:
        !rendered
    }

    void "test show before detected"() {
        when:
        tagLib.session.timeZone = null
        String rendered = tagLib.show().toString()

        then:
        !rendered
    }

    void "test show after detected"() {
        when:
        tagLib.session.timeZone = TimeZone.getTimeZone("America/Chicago")
        String rendered = tagLib.show().toString()

        then:
        rendered == tagLib.session.timeZone.inDaylightTime(new Date()) ? "CDT" : "CST"
    }

    void "test show attribute detected"() {
        when:
        tagLib.session.timeZone = TimeZone.getTimeZone("America/Chicago")
        String rendered = tagLib.show(attribute: 'displayName').toString()

        then:
        rendered == tagLib.session.timeZone.inDaylightTime(new Date()) ? "Central Daylight Time" : "Central Standard Time"
    }

    void "test offset server to client time - datePicker"() {
        when:
        tagLib.session.timeZone = TimeZone.getTimeZone("America/Chicago")
        Date date = new Date(116, 11, 1, 14, 0)
        Date expectedDate = new Date(116, 11, 1, 8, 0)
        Map attrs = [value: date]


        String rendered = tagLib.datePicker(attrs).toString()

        then:
        Calendar.getInstance().timeZone == TimeZone.getTimeZone("UTC") //Server time is UTC
        attrs.value == expectedDate
        rendered.contains("<option value=\"08\" selected=\"selected\">08</option>")
    }

    void "test offset client to server time - formatDate"() {
        when:
        tagLib.session.timeZone = TimeZone.getTimeZone("America/Chicago")
        Date date = new Date(116, 11, 1, 14, 0)
        Map attrs = [date: date]

        String rendered = tagLib.formatDate(attrs).toString()

        then:
        Calendar.getInstance().timeZone == TimeZone.getTimeZone("UTC") //Server time is UTC
        rendered == "2016-12-01 08:00:00 ${TimeZone.getDefault().inDaylightTime(new Date()) ? "CDT" : "CST"}"
    }
}
