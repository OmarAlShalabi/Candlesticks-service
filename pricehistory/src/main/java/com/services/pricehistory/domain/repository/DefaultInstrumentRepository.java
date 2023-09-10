package com.services.pricehistory.domain.repository;

import com.services.pricehistory.infrastructure.database.InfluxDbReader;
import com.services.pricehistory.domain.dto.instrument.InstrumentActionType;
import com.services.pricehistory.domain.parser.InstrumentParser;
import org.springframework.lang.NonNull;

/**
 * @author Omar Al-Shalabi
 */
public class DefaultInstrumentRepository implements InstrumentRepository {

    private static final String EPOCH_ZERO = "1970-01-01T00:00:00Z";
    private final InstrumentParser instrumentParser;
    private final String bucketName;
    private final String instrumentsMeasurmentName;
    private final InfluxDbReader influxDbReader;

    public DefaultInstrumentRepository(@NonNull final InstrumentParser instrumentParser,
                                       @NonNull final String bucketName, @NonNull final String instrumentsMeasurmentName,
                                       @NonNull final InfluxDbReader influxDbReader) {
        this.instrumentParser = instrumentParser;
        this.bucketName = bucketName;
        this.instrumentsMeasurmentName = instrumentsMeasurmentName;
        this.influxDbReader = influxDbReader;
    }

    @Override
    public InstrumentActionType getLastInstrumentActionByIsin(@NonNull final String isin) {
        return instrumentParser.getActionTypeFromFluxTable(
                influxDbReader.executeFluxQuery(getLastInstrumentActionQuery(isin)));
    }

    private String getLastInstrumentActionQuery(final String isin) {
        final String query = """
                from(bucket: "%s")
                  |> range(start: %s)
                  |> filter(fn: (r) => r["_measurement"] == "%s" and r.isin == "%s")
                  |> aggregateWindow(every: 1d, fn: last, createEmpty: false)
                  |> yield(name: "last")""";
        return String.format(query, bucketName, EPOCH_ZERO, instrumentsMeasurmentName, isin);
    }
}
