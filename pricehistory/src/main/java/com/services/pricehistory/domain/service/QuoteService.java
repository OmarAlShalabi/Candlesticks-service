package com.services.pricehistory.domain.service;

import com.services.pricehistory.domain.dto.quote.ImmutableQuote;
import com.services.pricehistory.domain.writer.QuoteWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author Omar Al-Shalabi
 */
@Component
public class QuoteService {

    private final QuoteWriter quoteWriter;

    public QuoteService(@NonNull final QuoteWriter quoteWriter) {
        this.quoteWriter = quoteWriter;
    }

    /**
     * Method that saves Quote into database.
     * @param quote Quote Object
     */
    public void saveQuote(@NonNull final ImmutableQuote quote) {
        quoteWriter.saveQuote(quote);
    }
}
