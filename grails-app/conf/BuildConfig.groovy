grails.project.work.dir = 'target'

grails.project.dependency.resolver = 'maven'
grails.project.dependency.resolution = {
    inherits 'global'
    log 'warn'
    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    plugins {

        runtime 'org.grails.plugins:asset-pipeline:2.6.10'

        build(':release:3.1.2', ':rest-client-builder:2.1.1') {
            export = false
        }
    }
}
