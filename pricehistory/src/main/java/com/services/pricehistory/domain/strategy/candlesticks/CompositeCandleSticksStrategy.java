package com.services.pricehistory.domain.strategy.candlesticks;

import com.services.pricehistory.domain.dto.candlesticks.CandleStick;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omar Al-Shalabi
 */
public class CompositeCandleSticksStrategy {

    private final List<CandleSticksStrategy> strategies;

    public CompositeCandleSticksStrategy(List<CandleSticksStrategy> strategies) {
        this.strategies = strategies;
    }

    public List<CandleStick> applyStrategies(@NonNull final List<CandleStick> candleSticks) {
        List<CandleStick> processedCandleSticks = new ArrayList<>(candleSticks);
        for (CandleSticksStrategy strategy : strategies) {
            processedCandleSticks = strategy.processCandleSticks(processedCandleSticks);
        }
        return processedCandleSticks;
    }
}
