package org.gradle.api.plugins

import org.gradle.api.Project
import org.gradle.api.internal.AsyncProcessOperations
import org.gradle.util.TestUtil
import spock.lang.Specification

class AsyncProcessPluginTest extends Specification {

    private final Project project = TestUtil.createRootProject()

    def "creates extension"() {
        when:
        project.plugins.apply(AsyncProcessPlugin)

        then:
        assert project.plugins.hasPlugin(AsyncProcessPlugin)
        assert project.extensions.getByType(AsyncProcessExtension)
        assert project.extensions.findByName('async')
    }

    def 'extension implements async operations interface and has delegate'() {
        when:
        project.plugins.apply(AsyncProcessPlugin)

        then:
        def ext = project.extensions.getByType(AsyncProcessExtension)
        assert ext instanceof AsyncProcessOperations
    }
}
