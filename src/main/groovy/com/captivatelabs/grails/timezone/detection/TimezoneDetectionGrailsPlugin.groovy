package com.captivatelabs.grails.timezone.detection

import grails.plugins.Plugin

class TimeZoneDetectionGrailsPlugin extends Plugin {
    def version = "0.4-SNAPSHOT"
    def grailsVersion = "4.0 > *"
    def loadAfter = ['data-binding', 'groovyPages']
    def title = "Timezone Detection Plugin"
    def author = "Dustin D. Clark"
    def authorEmail = "dustin@captivatelabs.com"
    def description = "Detects browser timezone and makes the information available in session.  Also modifies the Grails dateFormat tag to use the user's current timezone."
    def documentation = "https://github.com/dustindclark/grails-timezone-detection"
    def license = "MIT"
    def organization = [name: "Captivate Labs, Inc", url: "https://www.captivatelabs.com/"]
    def issueManagement = [url: "https://github.com/dustindclark/grails-timezone-detection/issues"]
    def scm = [url: "https://github.com/dustindclark/grails-timezone-detection"]

    def providedArtefacts = [
            FormatTagLib,
            FormTagLib
    ]

    Closure doWithSpring() {
        { ->
            instantBindingEditor(InstantBindingEditor)
            timeZoneAwareDateEditor(TimeZoneAwareDateEditor)
            formTagLib(FormTagLib)
            formatTagLib(FormatTagLib)
        }
    }
}
