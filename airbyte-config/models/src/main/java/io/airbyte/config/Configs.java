/*
 * MIT License
 *
 * Copyright (c) 2020 Airbyte
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.airbyte.config;

import java.nio.file.Path;
import java.util.Set;

public interface Configs {

  String getAirbyteRole();

  String getAirbyteVersion();

  String getAirbyteVersionOrWarning();

  Path getConfigRoot();

  Path getWorkspaceRoot();

  Path getLocalRoot();

  String getDatabaseUser();

  String getDatabasePassword();

  String getDatabaseUrl();

  String getWebappUrl();

  String getWorkspaceDockerMount();

  String getLocalDockerMount();

  String getDockerNetwork();

  TrackingStrategy getTrackingStrategy();

  WorkerEnvironment getWorkerEnvironment();

  WorkspaceRetentionConfig getWorkspaceRetentionConfig();

  String getTemporalHost();

  Set<Integer> getTemporalWorkerPorts();

  String getKubeNamespace();

  String getCpuRequest();

  String getCpuLimit();

  String getMemoryRequest();

  String getMemoryLimit();

  // The following methods retrieve logging related information.
  String getS3LogBucket();

  String getS3LogBucketRegion();

  String getAwsAccessKey();

  String getAwsSecretAccessKey();

  String getS3MinioEndpoint();

  enum TrackingStrategy {
    SEGMENT,
    LOGGING
  }

  enum WorkerEnvironment {
    DOCKER,
    KUBERNETES
  }

}
