package com.services.pricehistory.domain.dto.instrument;

import org.springframework.lang.NonNull;

/**
 * @author Omar Al-Shalabi
 */
public record InstrumentData(String description, String isin) {

    public InstrumentData(@NonNull final String description, @NonNull final String isin) {
        this.description = description;
        this.isin = isin;
    }
}
