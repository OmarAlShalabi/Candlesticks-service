package com.services.pricehistory.domain.configuration;

import com.services.pricehistory.infrastructure.configuration.DatabaseConfiguration;
import com.services.pricehistory.domain.parser.InstrumentParser;
import com.services.pricehistory.domain.repository.DefaultInstrumentRepository;
import com.services.pricehistory.domain.writer.DefaultInstrumentWriter;
import com.services.pricehistory.domain.writer.InstrumentWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.lang.NonNull;

/**
 * @author Omar Al-Shalabi
 */
@Configuration
@Import({ DatabaseConfiguration.class})
public class InstrumentsConfiguration {

    private final DatabaseConfiguration databaseConfiguration;
    private final InstrumentParser instrumentParser;

    public InstrumentsConfiguration(@NonNull final DatabaseConfiguration databaseConfiguration,
                                    @NonNull final InstrumentParser instrumentParser) {
        this.databaseConfiguration = databaseConfiguration;
        this.instrumentParser = instrumentParser;
    }

    @Bean
    public InstrumentWriter instrumentWriter() {
        return new DefaultInstrumentWriter(databaseConfiguration.defaultInfluxDbWriter(),
                databaseConfiguration.instrumentsMeasurmentName);
    }

    @Bean
    public DefaultInstrumentRepository defaultInstrumentRepository() {
        return new DefaultInstrumentRepository(instrumentParser, databaseConfiguration.influxDbBucketName,
                databaseConfiguration.instrumentsMeasurmentName, databaseConfiguration.defaultInfluxDbReader());
    }
}
