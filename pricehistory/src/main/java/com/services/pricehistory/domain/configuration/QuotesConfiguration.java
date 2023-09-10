package com.services.pricehistory.domain.configuration;

import com.services.pricehistory.infrastructure.configuration.DatabaseConfiguration;
import com.services.pricehistory.domain.writer.DefaultQuoteWriter;
import com.services.pricehistory.domain.writer.QuoteWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.lang.NonNull;

/**
 * @author Omar Al-Shalabi
 */
@Configuration
@Import({ DatabaseConfiguration.class})
public class QuotesConfiguration {

    private final DatabaseConfiguration databaseConfiguration;

    public QuotesConfiguration(@NonNull final DatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    @Bean
    public QuoteWriter quoteWriter() {
        return new DefaultQuoteWriter(databaseConfiguration.defaultInfluxDbWriter(),
                databaseConfiguration.quotesMeasurmentName);
    }
}
