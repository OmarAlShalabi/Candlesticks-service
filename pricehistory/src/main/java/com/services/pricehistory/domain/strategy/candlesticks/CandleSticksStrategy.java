package com.services.pricehistory.domain.strategy.candlesticks;

import com.services.pricehistory.domain.dto.candlesticks.CandleStick;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Omar Al-Shalabi
 */
public interface CandleSticksStrategy {

    List<CandleStick> processCandleSticks(@NonNull final List<CandleStick> candleSticks);
}
