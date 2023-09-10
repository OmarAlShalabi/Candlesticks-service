package com.services.pricehistory.domain.service;

import com.services.pricehistory.domain.command.GetCandlesticksCommand;
import com.services.pricehistory.domain.command.ImmutableGetCandlesticksCommand;
import com.services.pricehistory.domain.dto.candlesticks.CandleStick;
import com.services.pricehistory.domain.dto.instrument.InstrumentActionType;
import com.services.pricehistory.domain.exception.InstrumentDeletedException;
import com.services.pricehistory.domain.repository.CandlesticksRepository;
import com.services.pricehistory.domain.repository.InstrumentRepository;
import com.services.pricehistory.domain.strategy.candlesticks.CompositeCandleSticksStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * @author Omar Al-Shalabi
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CandlesticksServiceTest {

    private static final String TEST_ISIN = "IS12345678";
    private static final int TEST_RANGE = 30;
    private static final int TEST_TIME_INTERVAL = 1;

    private CandlesticksRepository candlesticksRepository;
    private CompositeCandleSticksStrategy compositeCandleSticksStrategy;
    private InstrumentRepository instrumentRepository;

    private CandlesticksService candlesticksService;

    @BeforeAll
    public void setup() {
        candlesticksRepository = Mockito.mock(CandlesticksRepository.class);
        compositeCandleSticksStrategy = Mockito.mock(CompositeCandleSticksStrategy.class);
        instrumentRepository = Mockito.mock(InstrumentRepository.class);
        candlesticksService = new CandlesticksService(candlesticksRepository, compositeCandleSticksStrategy,
                instrumentRepository);
    }

    @Test
    public void testGetCandlesticksByIsinAndInterval_WhenIsinIsDeleted_ThenThrowInstrumentDeletedException() {
        Mockito.when(instrumentRepository.getLastInstrumentActionByIsin(TEST_ISIN))
                .thenReturn(InstrumentActionType.DELETE);
        final GetCandlesticksCommand getCandlesticksCommand = getTestCommand();
        Assertions.assertThrows(InstrumentDeletedException.class,
                () -> candlesticksService.getCandlesticksByIsinAndInterval(getCandlesticksCommand));
    }

    @Test
    public void testGetCandlesticksByIsinAndInterval_WhenIsinIsActive_ThenReturnProcessedCandleSticks()
            throws InstrumentDeletedException {
        final TestCandleSticksSupplier testCandleSticksSupplier = new TestCandleSticksSupplier();
        final OutputCandleSticksSupplier outputCandleSticksSupplier = new OutputCandleSticksSupplier();
        final GetCandlesticksCommand getCandlesticksCommand = getTestCommand();
        Mockito.when(instrumentRepository.getLastInstrumentActionByIsin(TEST_ISIN))
                .thenReturn(InstrumentActionType.ADD);
        Mockito.when(candlesticksRepository.getCandlesticksByCommand(getCandlesticksCommand))
                .thenReturn(testCandleSticksSupplier.get());
        Mockito.when(compositeCandleSticksStrategy.applyStrategies(testCandleSticksSupplier.get()))
                .thenReturn(outputCandleSticksSupplier.get());
        final List<CandleStick> actualCandleStick = candlesticksService
                .getCandlesticksByIsinAndInterval(getCandlesticksCommand);
        Assertions.assertEquals(outputCandleSticksSupplier.get(), actualCandleStick);
        Mockito.verify(candlesticksRepository, Mockito.times(1))
                .getCandlesticksByCommand(getCandlesticksCommand);
        Mockito.verify(instrumentRepository, Mockito.times(1))
                .getLastInstrumentActionByIsin(TEST_ISIN);
        Mockito.verify(compositeCandleSticksStrategy, Mockito.times(1))
                .applyStrategies(testCandleSticksSupplier.get());
    }

    private GetCandlesticksCommand getTestCommand() {
        return ImmutableGetCandlesticksCommand.builder()
                .isin(TEST_ISIN)
                .priceHistoryRangeInMins(TEST_RANGE)
                .timeIntervalInMins(TEST_TIME_INTERVAL)
                .build();
    }
}
