package org.gradle.api.plugins

import org.gradle.api.internal.AsyncProcessOperations
import org.gradle.process.ExecResult
import org.gradle.process.ProcessHandle

class AsyncProcessExtension implements AsyncProcessOperations {

    private final AsyncProcessOperations asyncProcessOperations

    AsyncProcessExtension(AsyncProcessOperations asyncProcessOperations) {
        this.asyncProcessOperations = asyncProcessOperations
    }

    @Override
    ProcessHandle javafork(Closure cl) {
        return asyncProcessOperations.javafork(cl)
    }

    @Override
    ProcessHandle fork(Closure cl) {
        return asyncProcessOperations.fork(cl)
    }

    @Override
    ExecResult waitForFinish(ProcessHandle fork) {
        return asyncProcessOperations.waitForFinish(fork)
    }

    @Override
    List<ExecResult> waitForFinish(List<ProcessHandle> forks) {
        return asyncProcessOperations.waitForFinish(forks)
    }
}
