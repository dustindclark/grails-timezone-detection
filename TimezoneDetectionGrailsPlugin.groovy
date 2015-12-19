class TimezoneDetectionGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.4 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
    ]

    def dependsOn = [assetPipeline: '* > 2.0']

    def group = "com.captivatelabs.grails.plugins"

    def loadAfter = ['data-binding']

    def title = "Timezone Detection Plugin" // Headline display name of the plugin
    def author = "Dustin D. Clark"
    def authorEmail = "dustin@captivatelabs.com"
    def description = '''\
Detects browser timezone and makes the information available in session.  Also modifies the Grails dateFormat tag to use the user's current timezone.
'''

    // URL to the plugin's documentation
    def documentation = "https://github.com/dustindclark/grails-timezone-detection"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "MIT"

    // Details of company behind the plugin (if there is one)
    def organization = [name: "Captivate Labs, Inc", url: "http://www.captivatelabs.com/"]

    // Location of the plugin's issue tracker.
    def issueManagement = [system: "GITHUB", url: "https://github.com/dustindclark/grails-timezone-detection/issues"]

    // Online location of the plugin's browseable source code.
    def scm = [url: "https://github.com/dustindclark/grails-timezone-detection"]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        timeZoneAwareDateEditor(com.captivatelabs.grails.timezone.detection.TimeZoneAwareDateEditor)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { ctx ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
