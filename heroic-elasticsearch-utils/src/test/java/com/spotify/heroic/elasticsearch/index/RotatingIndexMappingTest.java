package com.spotify.heroic.elasticsearch.index;

import static org.junit.Assert.assertArrayEquals;

import com.spotify.heroic.common.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;

public class RotatingIndexMappingTest {
    private RotatingIndexMapping rotating;

    private Duration interval = new Duration(1000, TimeUnit.MILLISECONDS);
    private String pattern = "index-%s";
    private int maxReadIndices = 2;
    private int maxWriteIndices = 1;

    @Before
    public void setup() {
        rotating = new RotatingIndexMapping(interval, maxReadIndices, maxWriteIndices, pattern,
            new HashMap<>());
    }

    @Test
    public void testReadIndex() throws NoIndexSelectedException {
        final String[] indices = rotating.readIndices(8000, "typeA");
        assertArrayEquals(new String[]{"index-typeA-8000", "index-typeA-7000"}, indices);
    }

    @Test
    public void testEmptyReadIndex() throws NoIndexSelectedException {
        final String[] indices = rotating.readIndices(0, "typeA");
        assertArrayEquals(new String[]{"index-typeA-0"}, indices);
    }

    @Test
    public void testWriteIndex() {
        final String[] indices = rotating.writeIndices(8000, "typeA");
        assertArrayEquals(new String[]{"index-typeA-8000"}, indices);
    }
}
