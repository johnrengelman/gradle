/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.process;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Represents a process that was started asynchronously.
 */
public interface ProcessHandle {

    /**
     * The working directory of the process.
     * @return
     */
    File getDirectory();

    /**
     * The command to that is being executed by the process
     * @return
     */
    String getCommand();

    /**
     * The arguments passed to the process
     * @return
     */
    List<String> getArguments();

    /**
     * The configured environment for the process.
     * @return
     */
    Map<String, String> getEnvironment();

    /**
     * The current execution state of the process.
     * @return
     */
    ProcessState getState();

    /**
     * Waits for the process to finish.
     *
     * @return result
     */
    ExecResult waitForFinish();

    /**
     * Returns if the exit value of the process should be ignored or not.
     * @return true if the exit value should be ignored. False otherwise.
     */
    boolean isIgnoreExitValue();
}
