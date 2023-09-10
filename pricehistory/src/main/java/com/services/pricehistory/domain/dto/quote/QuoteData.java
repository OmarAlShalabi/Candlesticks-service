package com.services.pricehistory.domain.dto.quote;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;

/**
 * @author Omar Al-Shalabi
 */
public record QuoteData(BigDecimal price, String isin) {

    public QuoteData(@NonNull final BigDecimal price, @NonNull final String isin) {
        this.price = price;
        this.isin = isin;
    }
}
