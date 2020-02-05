# About
The Grails Timezone Detection Plugin makes use of the
[jsTimezoneDetect Library](http://pellepim.bitbucket.org/jstz/) in order to make the
client/browser's timezone available on the server/JVM.  Additionally, this plugin overrides
the default Grails formatDate tag to make use of the user's current timezone.

# Installation

Add the following to your BuildConfig.groovy in the plugins section:

```groovy
compile "org.grails.plugins:timezone-detection:latest.integration"

```

# Usage

Simply include the tz:detect tag inside of your head tag (preferably in your layout):

```rhtml
<head>
    ...
    <tz:detect />
</head>
```

If the timezone has not already been detected, the plugin will do a quick redirect to set the timezone,
and will then redirect back to the originally requested page.  This guarantees that the formatDate tag
will make use of the end user's timezone, even on the first page.

You can also use the 'show' tag to render other timezone information:

```rhtml
<tz:show /> <!-- results in TimeZone ID (i.e. UTC, CST) -->
<tz:show attribute="displayName" /> <!-- results in TimeZone display name (i.e. Universal Time Coordinated, America/Chicago, etc) -->
```

See the [java.util.TimeZone class](http://docs.oracle.com/javase/7/docs/api/java/util/TimeZone.html) for all available attributes.

This plugin also overrides the default Grails implementations for `g:formatDate` and `g:datePicker` to make them timezone aware.
Additionally, you can make use of Java 8 by binding to an `Instant` instead of a date.  For example:

```
<g:datePicker date="${Instant.now()}" />
``` 

**NOTE&mdash;** If you leverage Spring Security with rejectIfNoRule set to true *and* you intend to detect the user's timezone on unprotected pages
or in your template/layout, you *MUST* unprotect /timezone/**.  Otherwise, an infinite redirect to your login page may occur.
Thank you, @harbdog for pointing this out.