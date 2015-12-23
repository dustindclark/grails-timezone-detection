package com.captivatelabs.grails.timezone.detection

import org.codehaus.groovy.grails.web.util.WebUtils
import org.grails.databinding.TypedStructuredBindingEditor
import org.grails.databinding.converters.AbstractStructuredDateBindingEditor

class TimeZoneAwareDateEditor extends AbstractStructuredDateBindingEditor<Date> implements TypedStructuredBindingEditor {

    Date getDate(Calendar c) {
        TimeZone timeZone = WebUtils.retrieveGrailsWebRequest()?.session?.timeZone
        if (timeZone) {
            return TimeZoneUtil.offsetDate(timeZone, Calendar.getInstance().timeZone, c.time)
        } else {
            return c.time
        }
    }

    @Override
    Class getTargetType() {
        return Date
    }
}