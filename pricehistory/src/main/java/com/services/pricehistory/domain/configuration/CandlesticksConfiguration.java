package com.services.pricehistory.domain.configuration;

import com.services.pricehistory.infrastructure.configuration.DatabaseConfiguration;
import com.services.pricehistory.domain.parser.CandleStickParser;
import com.services.pricehistory.domain.repository.CandlesticksRepository;
import com.services.pricehistory.domain.repository.DefaultCandlesticksRepository;
import com.services.pricehistory.domain.strategy.candlesticks.CandleSticksStrategy;
import com.services.pricehistory.domain.strategy.candlesticks.CompositeCandleSticksStrategy;
import com.services.pricehistory.domain.strategy.candlesticks.FillMissingCandleSticksStrategy;
import com.services.pricehistory.domain.supplier.CandlestickFluxAggrergatorFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.lang.NonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Omar Al-Shalabi
 */
@Configuration
@Import({ DatabaseConfiguration.class})
public class CandlesticksConfiguration {

    private final DatabaseConfiguration databaseConfiguration;
    private final CandleStickParser candleStickParser;
    private final CandlestickFluxAggrergatorFunction candlestickFluxAggrergatorFunction;
    private final FillMissingCandleSticksStrategy fillMissingCandleSticksStrategy;

    public CandlesticksConfiguration(@NonNull final DatabaseConfiguration databaseConfiguration,
                                     @NonNull final CandleStickParser candleStickParser,
                                     @NonNull final CandlestickFluxAggrergatorFunction candlestickFluxAggrergatorFunction,
                                     @NonNull final FillMissingCandleSticksStrategy fillMissingCandleSticksStrategy) {
        this.databaseConfiguration = databaseConfiguration;
        this.candleStickParser = candleStickParser;
        this.candlestickFluxAggrergatorFunction = candlestickFluxAggrergatorFunction;
        this.fillMissingCandleSticksStrategy = fillMissingCandleSticksStrategy;
    }

    @Bean
    public CandlesticksRepository candlesticksRepository() {
        return new DefaultCandlesticksRepository(candleStickParser, candlestickFluxAggrergatorFunction,
                databaseConfiguration.defaultInfluxDbReader(), databaseConfiguration.influxDbBucketName,
                databaseConfiguration.quotesMeasurmentName);
    }

    @Bean
    public CompositeCandleSticksStrategy compositeCandleSticksStrategy() {
        final List<CandleSticksStrategy> strategyList = new LinkedList<>();
        strategyList.add(fillMissingCandleSticksStrategy);
        return new CompositeCandleSticksStrategy(strategyList);
    }
}
