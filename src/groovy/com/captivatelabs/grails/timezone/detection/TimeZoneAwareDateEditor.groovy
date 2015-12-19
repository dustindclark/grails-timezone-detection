package com.captivatelabs.grails.timezone.detection

import org.codehaus.groovy.grails.web.util.WebUtils
import org.grails.databinding.TypedStructuredBindingEditor
import org.grails.databinding.converters.AbstractStructuredDateBindingEditor

/**
 * Created by dustin.clark on 12/19/15.
 */
class TimeZoneAwareDateEditor extends AbstractStructuredDateBindingEditor<Date> implements TypedStructuredBindingEditor {

    Date getDate(Calendar c) {
        TimeZone timeZone = WebUtils.retrieveGrailsWebRequest()?.getSession()?.timeZone
        if (timeZone) {
            long serverOffset = Calendar.getInstance().timeZone.getRawOffset()
            long clientOffset = timeZone.getRawOffset()
            long adjustment = serverOffset - clientOffset
            return new Date(c.time.time + adjustment)
        } else {
            return c.time
        }
    }

    @Override
    Class getTargetType() {
        return Date
    }
}