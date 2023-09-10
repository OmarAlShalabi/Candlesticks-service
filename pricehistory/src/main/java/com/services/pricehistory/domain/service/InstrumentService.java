package com.services.pricehistory.domain.service;

import com.services.pricehistory.domain.dto.instrument.ImmutableInstrument;
import com.services.pricehistory.domain.writer.InstrumentWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author Omar Al-Shalabi
 */
@Component
public class InstrumentService {

    private final InstrumentWriter instrumentWriter;

    public InstrumentService(@NonNull final InstrumentWriter instrumentWriter) {
        this.instrumentWriter = instrumentWriter;
    }

    public void saveInstrument(@NonNull final ImmutableInstrument instrument) {
        instrumentWriter.saveInstrument(instrument);
    }
}
