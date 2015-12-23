package com.captivatelabs.grails.timezone.detection

class TimeZoneOverridesLib {

    //Override default date picker with timezone aware date picker
    def datePicker = { attrs ->
        out << tz.datePicker(attrs)
    }

    def formatDate = { attrs ->
        out << tz.datePikcer(attrs)
    }
}
