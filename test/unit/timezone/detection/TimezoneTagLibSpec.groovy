package timezone.detection

import asset.pipeline.grails.AssetsTagLib
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.web.GroovyPageUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GroovyPageUnitTestMixin)
@TestFor(TimezoneTagLib)
class TimezoneTagLibSpec extends Specification {

    def setup() {
        mockTagLib(AssetsTagLib)
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
}
