package com.services.pricehistory.domain.strategy;

import com.services.pricehistory.domain.strategy.supplier.TestInputCandleSticksSupplier;
import com.services.pricehistory.domain.strategy.supplier.TestOutputCandleSticksSupplier;
import com.services.pricehistory.domain.dto.candlesticks.CandleStick;
import com.services.pricehistory.domain.strategy.candlesticks.FillMissingCandleSticksStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Omar Al-Shalabi
 */
public class FillMissingCandleSticksStrategyTest {

    @Test
    public void testFillMissingCandleSticksStrategy_WhenProvidedCandleData_ThenFillMissingSegments() {
        final FillMissingCandleSticksStrategy strategy = new FillMissingCandleSticksStrategy();
        final TestInputCandleSticksSupplier inputSupplier = new TestInputCandleSticksSupplier();
        final TestOutputCandleSticksSupplier outSupplier = new TestOutputCandleSticksSupplier();
        final List<CandleStick> inputCandleStick = inputSupplier.get();
        final List<CandleStick> expectedOutputCandleStick = outSupplier.get();
        final List<CandleStick> actualProceessedCandleSticks = strategy.processCandleSticks(inputCandleStick);
        Assertions.assertEquals(expectedOutputCandleStick, actualProceessedCandleSticks);
    }
}
