package timezone.detection

class FormatTagLib {
    def grailsApplication

    def formatDate = { attrs ->
        def formatTagLib = getDefaultFormatTagLib()
        if (!attrs.timeZone) {
            attrs.timeZone = session.timeZone
        }
        log.error(attrs)

        log.error("Result is: ${formatTagLib.formatDate.call(attrs)}")

        out << formatTagLib.formatDate.call(attrs)
    }

    private def getDefaultFormatTagLib() {
        return grailsApplication.mainContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.FormatTagLib')
    }
}
