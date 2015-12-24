import com.captivatelabs.grails.timezone.detection.TimeZoneAwareDateEditor

class TimezoneDetectionGrailsPlugin {
    def version = "0.2-SNAPSHOT"
    def grailsVersion = "2.4 > *"
    def loadAfter = ['data-binding']
    def title = "Timezone Detection Plugin"
    def author = "Dustin D. Clark"
    def authorEmail = "dustin@captivatelabs.com"
    def description = "Detects browser timezone and makes the information available in session.  Also modifies the Grails dateFormat tag to use the user's current timezone."
    def documentation = "https://github.com/dustindclark/grails-timezone-detection"
    def license = "MIT"
    def organization = [name: "Captivate Labs, Inc", url: "http://www.captivatelabs.com/"]
    def issueManagement = [url: "https://github.com/dustindclark/grails-timezone-detection/issues"]
    def scm = [url: "https://github.com/dustindclark/grails-timezone-detection"]

    def doWithSpring = {
        timeZoneAwareDateEditor(TimeZoneAwareDateEditor)
    }
}
