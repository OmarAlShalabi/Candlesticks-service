package com.services.pricehistory.domain.dto.candlesticks;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.services.pricehistory.domain.dto.candlesticks.ImmutableCandleStick;
import org.immutables.value.Value;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author Omar Al-Shalabi
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableCandleStick.class)
public interface CandleStick {
    Instant openTimestamp();
    BigDecimal openPrice();
    BigDecimal closePrice();
    BigDecimal highPrice();
    BigDecimal lowPrice();
    Instant closeTimestamp();
}
