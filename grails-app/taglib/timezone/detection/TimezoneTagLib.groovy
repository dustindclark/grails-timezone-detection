package timezone.detection

class TimezoneTagLib {
    static namespace = 'tz'

    def detect = {
        //Don't do this on a POST because posted data will be lost.
        if (!session.timeZone && request.method == 'GET') {
            out << asset.javascript(src: 'timezone-detection.js')
            out << "<script type='text/javascript'>"
            out << "doTimezoneDetection('${createLink(controller: 'timezone', action: 'set')}/', '${request.forwardURI}');"
            out << "</script>"
        }
    }

    def show = { final attrs ->
        TimeZone timeZone = session.timeZone
        if (! timeZone) {
            return
        }
        if (attrs.attribute) {
            out << timeZone."${attribute}"
        } else {
            out << timeZone.getDisplayName(timeZone.inDaylightTime(new Date()), TimeZone.SHORT, request.getLocale())
        }

    }
}
