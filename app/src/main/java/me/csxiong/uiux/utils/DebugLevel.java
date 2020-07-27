package me.csxiong.uiux.utils;

/**
 * Debug等级,默认的Debug等级为{@link #ALL}
 */
public enum DebugLevel implements Comparable<DebugLevel> {
    NONE, ERROR, WARNING, INFO, DEBUG, VERBOSE;

    private static DebugLevel ALL = DebugLevel.VERBOSE;

    public boolean isSameOrLessThan(final DebugLevel pDebugLevel) {
        return this.compareTo(pDebugLevel) >= 0;
    }
}