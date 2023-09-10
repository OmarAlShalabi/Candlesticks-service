package com.services.pricehistory.domain.writer;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.services.pricehistory.infrastructure.database.InfluxDbWriter;
import com.services.pricehistory.domain.dto.quote.ImmutableQuote;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;

/**
 * @author Omar Al-Shalabi
 */
public class DefaultQuoteWriter implements QuoteWriter {

    private final InfluxDbWriter influxDbWriter;
    private final String quotesMeasurment;

    public DefaultQuoteWriter(@NonNull final InfluxDbWriter influxDbWriter, @NonNull final String quotesMeasurment) {
        this.influxDbWriter = influxDbWriter;
        this.quotesMeasurment = quotesMeasurment;
    }

    @Override
    public void saveQuote(@NotNull final ImmutableQuote quote) {
        final Point point = Point
                .measurement(quotesMeasurment)
                .addTag("isin", quote.quoteData().isin())
                .addField("price", quote.quoteData().price())
                .time(quote.timestamp(), WritePrecision.S);
        influxDbWriter.writePoint(point);
    }
}
