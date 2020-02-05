package com.captivatelabs.grails.timezone.detection

class FormTagLib {
    //Override default implementation of datePicker to consider timezone
    Closure datePicker = { attrs ->
        out << tz.datePicker(attrs)
    }
}
