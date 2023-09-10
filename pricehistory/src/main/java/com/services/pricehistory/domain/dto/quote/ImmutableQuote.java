package com.services.pricehistory.domain.dto.quote;

import org.springframework.lang.NonNull;

import java.time.Instant;

/**
 * @author Omar Al-Shalabi
 */
public record ImmutableQuote(QuoteData quoteData, Instant timestamp) {

    public ImmutableQuote(@NonNull final QuoteData quoteData, @NonNull final Instant timestamp) {
        this.quoteData = quoteData;
        this.timestamp = timestamp;
    }
}
