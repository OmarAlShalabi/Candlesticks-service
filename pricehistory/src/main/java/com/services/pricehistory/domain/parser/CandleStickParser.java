package com.services.pricehistory.domain.parser;

import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.services.pricehistory.domain.dto.candlesticks.CandleStick;
import com.services.pricehistory.domain.dto.candlesticks.ImmutableCandleStick;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Omar Al-Shalabi
 */
@Component
public class CandleStickParser {

    public List<CandleStick> getCandlesticksFromFluxTable(@NonNull final List<FluxTable> fluxTables) {
        final List<CandleStick> candleSticks = new ArrayList<>();
        if (fluxTables.isEmpty()) {
            return List.of();
        }
        final FluxTable isinCandlesticks = fluxTables.get(0);
        final List<FluxRecord> fluxRecords = isinCandlesticks.getRecords();
        dropLastFluxRecord(fluxRecords);
        for (FluxRecord record : isinCandlesticks.getRecords()) {
            final Instant openTimestamp = (Instant) Objects.requireNonNull(record.getValueByKey("_time"));
            final Instant closeTimestamp = openTimestamp.plus(1, ChronoUnit.MINUTES);
            final CandleStick candleStick = ImmutableCandleStick.builder()
                    .openTimestamp(openTimestamp)
                    .lowPrice(new BigDecimal(Objects.requireNonNull(record.getValueByKey("low")).toString()))
                    .highPrice(new BigDecimal(Objects.requireNonNull(record.getValueByKey("high")).toString()))
                    .openPrice(new BigDecimal(Objects.requireNonNull(record.getValueByKey("open")).toString()))
                    .closePrice(new BigDecimal(Objects.requireNonNull(record.getValueByKey("close")).toString()))
                    .closeTimestamp(closeTimestamp)
                    .build();
            candleSticks.add(candleStick);
        }
        return candleSticks;
    }

    private void dropLastFluxRecord(final List<FluxRecord> fluxRecords) {
        fluxRecords.remove(fluxRecords.size() - 1);
    }
}
