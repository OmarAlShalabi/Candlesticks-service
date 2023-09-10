package com.services.pricehistory.domain.command;

import org.immutables.value.Value;

/**
 * @author Omar Al-Shalabi
 */
@Value.Immutable
public interface GetCandlesticksCommand {

    String isin();
    int priceHistoryRangeInMins();
    int timeIntervalInMins();

}
