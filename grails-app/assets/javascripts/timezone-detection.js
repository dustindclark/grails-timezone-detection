//= require jstz

function doTimezoneDetection(timezoneSetUrl, returnUrl) {
    var tz = jstz.determine();
    if (tz && tz.name()) {
        var url = timezoneSetUrl + "?tz=" + encodeURIComponent(tz.name()) + "&r=" + encodeURIComponent(returnUrl);
        window.location = url;
    }
}
