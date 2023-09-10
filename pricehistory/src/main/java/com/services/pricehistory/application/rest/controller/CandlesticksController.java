package com.services.pricehistory.application.rest.controller;

import com.services.pricehistory.application.rest.validator.InstrumentValidator;
import com.services.pricehistory.domain.command.GetCandlesticksCommand;
import com.services.pricehistory.domain.command.ImmutableGetCandlesticksCommand;
import com.services.pricehistory.domain.dto.candlesticks.CandleStick;
import com.services.pricehistory.domain.exception.InstrumentDeletedException;
import com.services.pricehistory.domain.service.CandlesticksService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Omar Al-Shalabi
 */
@RestController
@RequestMapping("v1/candlesticks")
public class CandlesticksController {

    @Value("${candlesticks.intervalInMins}")
    public int intervalInMins;

    @Value("${candlesticks.priceHistoryInMins}")
    public int priceHistoryInMins;

    private final InstrumentValidator instrumentValidator;
    private final CandlesticksService candlesticksService;

    public CandlesticksController(@NonNull final InstrumentValidator instrumentValidator,
                                  @NonNull final CandlesticksService candlesticksService) {
        this.instrumentValidator = instrumentValidator;
        this.candlesticksService = candlesticksService;
    }

    @GetMapping("/{isin}")
    public ResponseEntity<List<CandleStick>> getCandlesticks(@PathVariable("isin") final String isin) {
        if (!instrumentValidator.isIsinValid(isin)) {
            return ResponseEntity.notFound().build();
        }
        final GetCandlesticksCommand candlesticksCommand = buildCommand(isin);
        try {
            return ResponseEntity.ok(candlesticksService.getCandlesticksByIsinAndInterval(candlesticksCommand));
        } catch (InstrumentDeletedException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private GetCandlesticksCommand buildCommand(final String isin) {
        return ImmutableGetCandlesticksCommand.builder()
                .isin(isin)
                .priceHistoryRangeInMins(priceHistoryInMins)
                .timeIntervalInMins(intervalInMins)
                .build();
    }
}
