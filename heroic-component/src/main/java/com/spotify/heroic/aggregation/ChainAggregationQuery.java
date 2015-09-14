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

package com.spotify.heroic.aggregation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChainAggregationQuery implements AggregationQuery {
    private final List<AggregationQuery> chain;

    @JsonCreator
    public ChainAggregationQuery(@JsonProperty("chain") List<AggregationQuery> chain) {
        if (chain == null || chain.isEmpty())
            throw new IllegalArgumentException("chain must be specified and non-empty");

        this.chain = chain;
    }

    @Override
    public ChainAggregation build(final AggregationContext context) {
        return new ChainAggregation(ChainAggregation.convertQueriesAsList(context, chain));
    }
}