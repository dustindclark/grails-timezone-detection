package timezone.detection

class FormTagLib {
    def grailsApplication

    //Allow falling back on the default date picker
    def datePicker = { attrs ->
        def formTagLib = getDefaultFormTagLib()
        if (session.timeZone) {
            long serverOffset = Calendar.getInstance().timeZone.getRawOffset()
            long clientOffset = session.timeZone.getRawOffset()
            long adjustment = clientOffset - serverOffset
            attrs.value = new Date(attrs.value.time + adjustment)
        }
        formTagLib.datePicker.call(attrs)
        if (! attrs.suppressTimezone) {
            out << "<span class='tz'>"
            out << tz.show()
            out << "</span>"
        }
    }

    private getDefaultFormTagLib() {
        return grailsApplication.mainContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.FormTagLib')
    }
}
