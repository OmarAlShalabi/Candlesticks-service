package com.services.pricehistory.domain.repository;

import com.services.pricehistory.domain.command.GetCandlesticksCommand;
import com.services.pricehistory.domain.dto.candlesticks.CandleStick;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Omar Al-Shalabi
 */
public interface CandlesticksRepository {

    List<CandleStick> getCandlesticksByCommand(@NonNull final GetCandlesticksCommand command);
}
