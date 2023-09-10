package com.services.pricehistory.domain.writer;

import com.services.pricehistory.domain.dto.quote.ImmutableQuote;
import org.springframework.lang.NonNull;

/**
 * @author Omar Al-Shalabi
 */
public interface QuoteWriter {

    /**
     * Save Quote to database.
     * @param quote quote to be saved.
     */
    void saveQuote(@NonNull final ImmutableQuote quote);
}
