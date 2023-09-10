package com.services.pricehistory.domain.writer;

import com.services.pricehistory.domain.dto.instrument.ImmutableInstrument;
import org.springframework.lang.NonNull;

/**
 * @author Omar Al-Shalabi
 */
public interface InstrumentWriter {

    /**
     * Saves Instrument to database.
     * @param instrument Instrument to be saved.
     */
    void saveInstrument(@NonNull final ImmutableInstrument instrument);
}
