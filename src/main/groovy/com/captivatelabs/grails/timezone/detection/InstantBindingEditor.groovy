package com.captivatelabs.grails.timezone.detection

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.web.databinding.converters.AbstractStructuredBindingEditor

import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId

@CompileStatic
@Slf4j
class InstantBindingEditor extends AbstractStructuredBindingEditor<Instant> implements GrailsConfigurationAware {
    private ZoneId defaultZoneId

    @Override
    Instant getPropertyValue(Map values) {
        Map<String, String> vals = (Map<String, String>) values
        LocalDateTime time = LocalDateTime.of(
                Integer.valueOf(vals.year),
                Month.of(Integer.valueOf(vals.month)),
                Integer.valueOf(vals.day),
                Integer.valueOf(vals.hour),
                Integer.valueOf(vals.minute)
        )
        String zoneIdValue = vals.get(TimeZoneTagLib.TIMEZONE_FIELD)
        ZoneId zoneId
        if (zoneIdValue) {
            zoneId = ZoneId.of(zoneIdValue)
        } else {
            log.warn("Timezone missing in posted values.  Binding using default timezone ${defaultZoneId.id}")
            zoneId = defaultZoneId
        }
        return time.toInstant(zoneId.getRules().getOffset(time))
    }

    @Override
    void setConfiguration(Config co) {
        defaultZoneId = ZoneId.of(co.getProperty('com.captivatelabs.timezone.defaultZoneId', String.class, 'UTC'))
    }
}
