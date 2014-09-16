package com.spotify.heroic.cluster.discovery;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotify.heroic.async.Callback;
import com.spotify.heroic.async.ResolvedCallback;
import com.spotify.heroic.cluster.ClusterDiscovery;

@RequiredArgsConstructor
public class StaticListDiscovery implements ClusterDiscovery {
    private static final int DEFAULT_THREAD_POOL_SIZE = 100;

    @JsonCreator
    public static StaticListDiscovery create(
            @JsonProperty("threadPoolSize") Integer threadPoolSize,
            @JsonProperty("nodes") List<URI> nodes) {
        if (threadPoolSize == null)
            threadPoolSize = DEFAULT_THREAD_POOL_SIZE;

        if (nodes == null)
            nodes = new ArrayList<>();

        return new StaticListDiscovery(nodes);
    }

    private final List<URI> nodes;

    @Override
    public Callback<Collection<URI>> find() {
        return new ResolvedCallback<Collection<URI>>(nodes);
    }
}
