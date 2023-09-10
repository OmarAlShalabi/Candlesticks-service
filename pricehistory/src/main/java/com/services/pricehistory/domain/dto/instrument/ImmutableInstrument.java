package com.services.pricehistory.domain.dto.instrument;

import org.springframework.lang.NonNull;

import java.time.Instant;

/**
 * @author Omar Al-Shalabi
 */
public record ImmutableInstrument(InstrumentData instrumentData, InstrumentActionType type, Instant timestamp) {

    public ImmutableInstrument(@NonNull final InstrumentData instrumentData, @NonNull final InstrumentActionType type,
                               @NonNull final Instant timestamp) {
        this.instrumentData = instrumentData;
        this.type = type;
        this.timestamp = timestamp;
    }
}
