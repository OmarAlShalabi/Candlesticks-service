package com.services.pricehistory.domain.strategy.supplier;

import com.services.pricehistory.domain.dto.candlesticks.CandleStick;
import com.services.pricehistory.domain.dto.candlesticks.ImmutableCandleStick;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Omar Al-Shalabi
 */
public class TestOutputCandleSticksSupplier implements Supplier<List<CandleStick>> {

    @Override
    public List<CandleStick> get() {
        final List<CandleStick> candleSticks = new ArrayList<>();
        final Instant testTime = Instant.parse("2023-01-01T00:00:00Z");
        candleSticks.add(buildCandleStick(testTime, new BigDecimal(0), new BigDecimal(0), new BigDecimal(0),
                new BigDecimal(0), testTime.plus(1, ChronoUnit.MINUTES)));
        candleSticks.add(buildCandleStick(testTime.plus(1, ChronoUnit.MINUTES), new BigDecimal(1),
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1),
                testTime.plus(2, ChronoUnit.MINUTES)));
        candleSticks.add(buildCandleStick(testTime.plus(2, ChronoUnit.MINUTES), new BigDecimal(1),
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1),
                testTime.plus(3, ChronoUnit.MINUTES)));
        candleSticks.add(buildCandleStick(testTime.plus(3, ChronoUnit.MINUTES), new BigDecimal(2),
                new BigDecimal(2), new BigDecimal(2), new BigDecimal(2),
                testTime.plus(4, ChronoUnit.MINUTES)));
        candleSticks.add(buildCandleStick(testTime.plus(4, ChronoUnit.MINUTES), new BigDecimal(2),
                new BigDecimal(2), new BigDecimal(2), new BigDecimal(2),
                testTime.plus(5, ChronoUnit.MINUTES)));
        return candleSticks;
    }

    private CandleStick buildCandleStick(final Instant openTimestamp, final BigDecimal openPrice,
                                         final BigDecimal closePrice, final BigDecimal highPrice,
                                         final BigDecimal lowPrice, final Instant closeTimestamp) {
        return ImmutableCandleStick.builder()
                .openTimestamp(openTimestamp)
                .openPrice(openPrice)
                .closePrice(closePrice)
                .highPrice(highPrice)
                .lowPrice(lowPrice)
                .closeTimestamp(closeTimestamp)
                .build();
    }
}
