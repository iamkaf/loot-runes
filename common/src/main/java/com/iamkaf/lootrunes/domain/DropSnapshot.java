package com.iamkaf.lootrunes.domain;

/** Loader-free representation of one drop, used by the rules and persistence layer. */
public record DropSnapshot(String itemId, int count) {
    public DropSnapshot {
        itemId = itemId == null ? "" : itemId;
        count = Math.max(0, count);
    }

    public boolean isEmpty() {
        return itemId.isBlank() || count == 0;
    }
}
