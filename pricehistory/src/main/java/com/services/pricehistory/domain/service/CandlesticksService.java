package com.services.pricehistory.domain.service;

import com.services.pricehistory.domain.exception.InstrumentDeletedException;
import com.services.pricehistory.domain.repository.CandlesticksRepository;
import com.services.pricehistory.domain.repository.InstrumentRepository;
import com.services.pricehistory.domain.strategy.candlesticks.CompositeCandleSticksStrategy;
import com.services.pricehistory.domain.command.GetCandlesticksCommand;
import com.services.pricehistory.domain.dto.candlesticks.CandleStick;
import com.services.pricehistory.domain.dto.instrument.InstrumentActionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Omar Al-Shalabi
 */
@Component
public class CandlesticksService {

    private static Logger logger = LoggerFactory.getLogger(CandlesticksService.class);

    private final CandlesticksRepository candlesticksRepository;
    private final CompositeCandleSticksStrategy compositeCandleSticksStrategy;
    private final InstrumentRepository instrumentRepository;
    public CandlesticksService(@NonNull final CandlesticksRepository candlesticksRepository,
                               @NonNull final CompositeCandleSticksStrategy compositeCandleSticksStrategy,
                               @NonNull final InstrumentRepository instrumentRepository) {
        this.candlesticksRepository = candlesticksRepository;
        this.compositeCandleSticksStrategy = compositeCandleSticksStrategy;
        this.instrumentRepository = instrumentRepository;
    }

    public List<CandleStick> getCandlesticksByIsinAndInterval(@NonNull final GetCandlesticksCommand candlesticksCommand)
            throws InstrumentDeletedException {
        if (instrumentRepository.getLastInstrumentActionByIsin(candlesticksCommand.isin())
                .equals(InstrumentActionType.DELETE)) {
            logger.error(String.format("Instrument with Isin %s is deleted or does not exist.",
                    candlesticksCommand.isin()));
            throw new InstrumentDeletedException();
        }
        final List<CandleStick> candleSticks = candlesticksRepository.getCandlesticksByCommand(candlesticksCommand);
        return compositeCandleSticksStrategy.applyStrategies(candleSticks);
    }
}
