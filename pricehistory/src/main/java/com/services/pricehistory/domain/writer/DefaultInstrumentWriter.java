package com.services.pricehistory.domain.writer;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.services.pricehistory.infrastructure.database.InfluxDbWriter;
import com.services.pricehistory.domain.dto.instrument.ImmutableInstrument;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;

/**
 * @author Omar Al-Shalabi
 */
public class DefaultInstrumentWriter implements InstrumentWriter {

    private final InfluxDbWriter influxDbWriter;
    private final String instrumentsMeasurement;

    public DefaultInstrumentWriter(@NonNull final InfluxDbWriter influxDbWriter,
                                   @NonNull final String instrumentsMeasurement) {
        this.influxDbWriter = influxDbWriter;
        this.instrumentsMeasurement = instrumentsMeasurement;
    }

    @Override
    public void saveInstrument(@NotNull final ImmutableInstrument instrument) {
        final Point point = Point
                .measurement(instrumentsMeasurement)
                .addTag("isin", instrument.instrumentData().isin())
                .addField("action", instrument.type().name())
                .time(instrument.timestamp(), WritePrecision.S);
        influxDbWriter.writePoint(point);
    }
}
