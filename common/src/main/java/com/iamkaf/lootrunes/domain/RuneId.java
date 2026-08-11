package com.iamkaf.lootrunes.domain;

import java.util.Arrays;
import java.util.Optional;

/** Stable identifiers for the prototype rules. The behavior behind an id is deliberately replaceable. */
public enum RuneId {
    PLENTY("plenty"),
    SACRIFICE("sacrifice"),
    ECHOES("echoes"),
    ASCENDANCE("ascendance"),
    MIGRATION("migration"),
    IMPROVISATION("improvisation");

    private final String value;

    RuneId(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Optional<RuneId> byValue(String value) {
        return Arrays.stream(values()).filter(id -> id.value.equals(value)).findFirst();
    }
}
