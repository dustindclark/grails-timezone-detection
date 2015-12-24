package com.captivatelabs.grails.timezone.detection

class FormTagLib {
    //Override default implementation of datePicker to consider timezone
    def datePicker = { attrs ->
        out << tz.datePicker(attrs)
    }
}
