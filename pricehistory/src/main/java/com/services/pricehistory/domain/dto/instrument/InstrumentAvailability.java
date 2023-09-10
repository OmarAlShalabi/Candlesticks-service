package com.services.pricehistory.domain.dto.instrument;

import java.time.Instant;

/**
 * @author Omar Al-Shalabi
 */
public record InstrumentAvailability(boolean isAvailable, Instant lastAddDate, Instant lastDeleteDate) {

    public InstrumentAvailability(boolean isAvailable, Instant lastAddDate, Instant lastDeleteDate) {
        this.isAvailable = isAvailable;
        this.lastAddDate = lastAddDate;
        this.lastDeleteDate = lastDeleteDate;
    }
}
