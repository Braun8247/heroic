package com.spotify.heroic.model;

import java.sql.Date;

import lombok.Data;

import org.apache.commons.lang.time.FastDateFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
public class DateRange implements Comparable<DateRange> {
    private final long start;
    private final long end;

    public DateRange(long start, long end) {
        if (start > end)
            start = end;

        this.start = start;
        this.end = end;
    }

    public long start() {
        return start;
    }

    public long end() {
        return end;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return diff() == 0;
    }

    public long diff() {
        return end - start;
    }

    /**
     * Creates a range that is rounded to the specified interval.
     * 
     * @param interval
     *            Interval to round to. Return same range if 0.
     * @return Rounded date range.
     */
    public DateRange rounded(long interval) {
        if (interval <= 0)
            return this;

        return new DateRange(start - start % interval, end - end % interval);
    }

    public boolean overlap(DateRange other) {
        if (end < other.start) {
            return false;
        }

        if (start > other.end) {
            return false;
        }

        return true;
    }

    @Override
    public int compareTo(DateRange other) {
        return Long.compare(start, other.start);
    }

    public DateRange join(DateRange other) {
        long start = Math.min(this.start, other.start);
        long end = Math.max(this.end, other.end);
        return new DateRange(start, end);
    }

    public boolean contains(long t) {
        return t >= start && t <= end;
    }

    /**
     * Modify this range with another range.
     * 
     * A modification asserts that the new range is a subset of the current
     * range. Any span which would cause the new range to become out of bounds
     * will be cropped.
     * 
     * @param range
     *            The constraints to modify this range against.
     * @return A new range representing the modified range.
     */
    public DateRange modify(DateRange range) {
        return modify(range.getStart(), range.getEnd());
    }

    public DateRange modify(long start, long end) {
        return new DateRange(Math.max(this.start, start), Math.min(this.end,
                end));
    }

    public DateRange start(long start) {
        return new DateRange(start, this.end);
    }

    public DateRange end(long end) {
        return new DateRange(this.start, end);
    }

    public DateRange shiftStart(long extent) {
        return new DateRange(Math.max(start + extent, 0), end);
    }

    public DateRange shiftEnd(long extent) {
        return new DateRange(start, Math.max(end + extent, 0));
    }

    public DateRange shift(long extent) {
        return new DateRange(Math.max(start + extent, 0), Math.max(end + extent, 0));
    }

    private static final FastDateFormat format = FastDateFormat
            .getInstance("yyyy-MM-dd HH:mm");

    @Override
    public String toString() {
        final Date start = new Date(this.start);
        final Date end = new Date(this.end);
        return "DateRange(start=" + format.format(start) + ", end="
        + format.format(end) + ")";
    }
}
