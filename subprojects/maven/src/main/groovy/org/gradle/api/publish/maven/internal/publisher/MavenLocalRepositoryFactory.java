/*
 * Copyright 2012 the original author or authors.
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

package org.gradle.api.publish.maven.internal.publisher;

import org.apache.maven.artifact.ant.LocalRepository;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.internal.Factory;

import java.io.File;

class MavenLocalRepositoryFactory implements Factory<LocalRepository> {

    private final MavenArtifactRepository artifactRepository;

    public MavenLocalRepositoryFactory(MavenArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

    public LocalRepository create() {
        LocalRepository localRepository = new LocalRepository();
        localRepository.setPath(new File(artifactRepository.getUrl()));

        return localRepository;
    }
}
