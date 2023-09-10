package com.services.pricehistory.domain.repository;

import com.services.pricehistory.infrastructure.database.InfluxDbReader;
import com.services.pricehistory.domain.command.GetCandlesticksCommand;
import com.services.pricehistory.domain.dto.candlesticks.CandleStick;
import com.services.pricehistory.domain.parser.CandleStickParser;
import com.services.pricehistory.domain.supplier.CandlestickFluxAggrergatorFunction;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Omar Al-Shalabi
 */
public class DefaultCandlesticksRepository implements CandlesticksRepository {

    private final CandleStickParser candleStickParser;
    private final CandlestickFluxAggrergatorFunction candlestickFluxAggrergatorFunction;
    private final InfluxDbReader influxDbReader;
    private final String bucketName;
    private final String quotesMeasurmentName;

    public DefaultCandlesticksRepository(@NonNull final CandleStickParser candleStickParser,
                                         @NonNull final CandlestickFluxAggrergatorFunction candlestickFluxAggrergatorFunction,
                                         @NonNull final InfluxDbReader influxDbReader, @NonNull final String bucketName,
                                         @NonNull final String quotesMeasurmentName) {
        this.candleStickParser = candleStickParser;
        this.candlestickFluxAggrergatorFunction = candlestickFluxAggrergatorFunction;
        this.influxDbReader = influxDbReader;
        this.bucketName = bucketName;
        this.quotesMeasurmentName = quotesMeasurmentName;
    }

    @Override
    public List<CandleStick> getCandlesticksByCommand(@NonNull final GetCandlesticksCommand command) {
        final String aggregatorFunction = candlestickFluxAggrergatorFunction.get();
        final String candlestickQuery = getCandlestickQuery(command);
        return candleStickParser.getCandlesticksFromFluxTable(influxDbReader.executeFluxQuery(aggregatorFunction +
                System.lineSeparator() + candlestickQuery));
    }

    private String getCandlestickQuery(final GetCandlesticksCommand command) {
        final String baseQuery = """
                from(bucket: "%s")
                  |> range(start: -%dm)
                  |> filter(fn: (r) => r._measurement == "%s" and r._field == "price" and r.isin == "%s")
                  |> aggregateWindow(
                    every: %dm,
                    fn: (tables=<-, column) => tables |> candlestick()
                  )""";
        return String.format(baseQuery, bucketName, command.priceHistoryRangeInMins(), quotesMeasurmentName,
                command.isin(), command.timeIntervalInMins());
    }
}
