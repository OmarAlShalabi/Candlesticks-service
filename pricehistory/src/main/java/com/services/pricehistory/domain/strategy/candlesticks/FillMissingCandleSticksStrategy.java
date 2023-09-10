package com.services.pricehistory.domain.strategy.candlesticks;

import com.services.pricehistory.domain.dto.candlesticks.CandleStick;
import com.services.pricehistory.domain.dto.candlesticks.ImmutableCandleStick;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to fill missing data from retrieved candlesticks.
 * Fills empty candles between non-zero ones.
 *
 * @author Omar Al-Shalabi
 */
@Component
public class FillMissingCandleSticksStrategy implements CandleSticksStrategy {


    @Override
    public List<CandleStick> processCandleSticks(@NonNull final List<CandleStick> candleSticks) {
        final List<CandleStick> processedCandleSticks = new ArrayList<>();
        CandleStick lastNonEmptyCandleStick = null;
        for (CandleStick candleStick : candleSticks) {
            CandleStick candleStickToBeAdded = candleStick;
            if (!isCandleStickEmpty(candleStick)) {
                lastNonEmptyCandleStick = candleStick;
            } else if (isCandleStickEmpty(candleStick) && (lastNonEmptyCandleStick != null)) {
                candleStickToBeAdded = ImmutableCandleStick.builder()
                        .openTimestamp(candleStick.openTimestamp())
                        .highPrice(lastNonEmptyCandleStick.highPrice())
                        .lowPrice(lastNonEmptyCandleStick.lowPrice())
                        .openPrice(lastNonEmptyCandleStick.openPrice())
                        .closePrice(lastNonEmptyCandleStick.closePrice())
                        .closeTimestamp(candleStick.closeTimestamp())
                        .build();
            }
            processedCandleSticks.add(candleStickToBeAdded);
        }
        return processedCandleSticks;
    }

    private boolean isCandleStickEmpty(final CandleStick candleStick) {
        return candleStick.highPrice().compareTo(BigDecimal.ZERO) == 0 &&
                candleStick.lowPrice().compareTo(BigDecimal.ZERO) == 0 &&
                candleStick.openPrice().compareTo(BigDecimal.ZERO) == 0 &&
                candleStick.closePrice().compareTo(BigDecimal.ZERO) == 0;
    }
}
