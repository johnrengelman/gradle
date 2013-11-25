package org.gradle.api.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.AsyncProcessOperations

import javax.inject.Inject

/**
 * Adds an extension with methods to create and handle forked processes
 */
class AsyncProcessPlugin implements Plugin<Project> {

    private final AsyncProcessOperations asyncProcessOperations

    @Inject
    AsyncProcessPlugin(AsyncProcessOperations asyncProcessOperations) {
        assert(asyncProcessOperations)
        this.asyncProcessOperations = asyncProcessOperations
    }

    @Override
    void apply(Project project) {
        project.extensions.create('async', AsyncProcessExtension, asyncProcessOperations)
    }
}
