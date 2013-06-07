/*
 * Copyright 2013 the original author or authors.
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
package org.gradle.api.publish.maven

import org.gradle.integtests.fixtures.AbstractIntegrationSpec
import org.gradle.test.fixtures.maven.M2Installation
import org.gradle.test.fixtures.maven.MavenFileModule

class AbstractMavenPublishIntegTest extends AbstractIntegrationSpec {

    private M2Installation m2Installation = new M2Installation(testDirectory)

    def setup() {
        executer.beforeExecute m2Installation
    }

    M2Installation getM2Installation() {
        m2Installation
    }

    protected def resolveArtifact(MavenFileModule module, def extension, def classifier) {
        doResolveArtifacts("""
    dependencies {
        resolve group: '${sq(module.groupId)}', name: '${sq(module.artifactId)}', version: '${sq(module.version)}', classifier: '${sq(classifier)}', ext: '${sq(extension)}'
    }
""")
    }

    protected def resolveArtifacts(MavenFileModule module) {
        doResolveArtifacts("""
    dependencies {
        resolve group: '${sq(module.groupId)}', name: '${sq(module.artifactId)}', version: '${sq(module.version)}'
    }
""")
    }

    protected def resolveArtifactsFromMavenLocal(MavenFileModule module) {
        doResolveArtifacts("""
    dependencies {
        resolve group: '${sq(module.groupId)}', name: '${sq(module.artifactId)}', version: '${sq(module.version)}'
    }
""", true)
    }

    protected def resolveArtifacts(MavenFileModule module, Map... additionalArtifacts) {
        def dependencies = """
    dependencies {
        resolve group: '${sq(module.groupId)}', name: '${sq(module.artifactId)}', version: '${sq(module.version)}'
        resolve(group: '${sq(module.groupId)}', name: '${sq(module.artifactId)}', version: '${sq(module.version)}') {
"""
        additionalArtifacts.each {
            // TODO:DAZ Docs say type defaults to 'jar', but seems it must be set explicitly
            def type = it.type == null ? 'jar' : it.type
            dependencies += """
            artifact {
                name = '${sq(module.artifactId)}' // TODO:DAZ Get NPE if name isn't set
                classifier = '${it.classifier}'
                type = '${type}'
            }
"""
        }
        dependencies += """
        }
    }
"""
        doResolveArtifacts(dependencies)
    }

    protected def doResolveArtifacts(def dependencies, boolean useMavenLocal=false) {
        // Replace the existing buildfile with one for resolving the published module
        // TODO:DAZ Use a separate directory for resolving
        settingsFile.text = "rootProject.name = 'resolve'"
        buildFile.text = useMavenLocal ? getBuildFileForMavenLocal(dependencies) : getBuildFile(dependencies)

        // TODO:DAZ Remove this requirement (by always publishing a jar/war/ear in tests?: Maven doesn't really support other file types as main artifact)
        executer.withDeprecationChecksDisabled()
        run "resolveArtifacts"
        def artifactsList = file("artifacts").exists() ? file("artifacts").list() : []
        return artifactsList.sort()
    }

    String getBuildFile(def dependencies) {
        """
            configurations {
                resolve
            }
            repositories {
                maven { url "${mavenRepo.uri}" }
                mavenCentral()
            }
            $dependencies
            task resolveArtifacts(type: Sync) {
                from configurations.resolve
                into "artifacts"
            }

"""
    }

    String getBuildFileForMavenLocal(def dependencies) {
        """
            configurations {
                resolve
            }
            repositories {
                mavenLocal()
                mavenCentral()
            }
            $dependencies
            task resolveArtifacts(type: Sync) {
                from configurations.resolve
                into "artifacts"
            }

"""
    }


    String sq(String input) {
        return escapeForSingleQuoting(input)
    }

    String escapeForSingleQuoting(String input) {
        return input.replace('\\', '\\\\').replace('\'', '\\\'')
    }
}
