package com.services.pricehistory.domain.repository;

import com.services.pricehistory.domain.dto.instrument.InstrumentActionType;
import org.springframework.lang.NonNull;

/**
 * @author Omar Al-Shalabi
 */
public interface InstrumentRepository {

    InstrumentActionType getLastInstrumentActionByIsin(@NonNull final String isin);
}
