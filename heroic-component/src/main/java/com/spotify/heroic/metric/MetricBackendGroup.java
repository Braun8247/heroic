/*
 * Copyright (c) 2015 Spotify AB.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.spotify.heroic.metric;

import eu.toolchain.async.AsyncFuture;

public interface MetricBackendGroup extends MetricBackend {
    /**
     * Perform a local query for metrics.
     */
    AsyncFuture<FullQuery> query(FullQuery.Request request);

    /**
     * Fetch metrics with a default (no-op) quota watcher. This method allows for the fetching of an
     * indefinite amount of metrics.
     *
     * @see MetricBackend#fetch
     */
    AsyncFuture<FetchData> fetch(FetchData.Request request);
}
