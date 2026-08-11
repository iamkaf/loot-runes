package com.iamkaf.lootrunes.domain;

import java.util.Objects;
import java.util.Optional;

/** Combined, deterministic instructions produced by the active rune pipeline. */
public record RunePlan(boolean keepNaturalDrops, int bonusRolls, Optional<DropSnapshot> echoedDrop) {
    public RunePlan {
        bonusRolls = Math.max(0, bonusRolls);
        echoedDrop = echoedDrop == null ? Optional.empty() : echoedDrop.filter(drop -> !drop.isEmpty());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private boolean keepNaturalDrops = true;
        private int bonusRolls;
        private DropSnapshot echoedDrop;

        public Builder discardNaturalDrops() {
            keepNaturalDrops = false;
            return this;
        }

        public Builder addBonusRolls(int count) {
            bonusRolls += Math.max(0, count);
            return this;
        }

        public Builder echo(DropSnapshot drop) {
            if (drop != null && !drop.isEmpty()) {
                echoedDrop = drop;
            }
            return this;
        }

        public Builder echo(Optional<DropSnapshot> drop) {
            Objects.requireNonNull(drop, "drop").ifPresent(this::echo);
            return this;
        }

        public RunePlan build() {
            return new RunePlan(keepNaturalDrops, bonusRolls, Optional.ofNullable(echoedDrop));
        }
    }
}
