package com.services.pricehistory.infrastructure.configuration;

import com.influxdb.client.*;
import com.services.pricehistory.infrastructure.database.DefaultInfluxDbReader;
import com.services.pricehistory.infrastructure.database.DefaultInfluxDbWriter;
import com.services.pricehistory.infrastructure.models.ImmutableInfluxDbWriteOptions;
import com.services.pricehistory.infrastructure.models.InfluxDbWriteOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Omar Al-Shalabi
 */
@Configuration
public class DatabaseConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfiguration.class);

    @Value("${influx-db-config.host}")
    public String influxDbHost;
    @Value("${influx-db-config.port}")
    public int influxDbPort;

    @Value("${influx-db-config.bucket-name}")
    public String influxDbBucketName;

    @Value("${influx-db-config.bucket-token}")
    public String influxDbBucketToken;

    @Value("${influx-db-write-options.batch-size}")
    public int writeBatchSize;

    @Value("${influx-db-write-options.flush-Interval}")
    public int writeFlushInterval;

    @Value("${influx-db-write-options.buffer-limit}")
    public int writeBufferLimit;

    @Value("${influx-db-write-options.jitterInterval}")
    public int writeJitterInterval;

    @Value("${influx-db-measurements.quotes.measurment}")
    public String quotesMeasurmentName;

    @Value("${influx-db-measurements.instruments.measurment}")
    public String instrumentsMeasurmentName;

    @Bean
    public InfluxDBClient influxDBClient() {
        logger.info(String.format("Establishing database connection to bucket %s", influxDbBucketName));
        final String influxDbURL = "http://" + influxDbHost + ":" + influxDbPort;
        return InfluxDBClientFactory.create(influxDbURL, influxDbBucketToken.toCharArray(),
                influxDbBucketName, influxDbBucketName);
    }

    @Bean
    public InfluxDbWriteOptions influxDbWriteOptions() {
        return ImmutableInfluxDbWriteOptions.builder()
                .writeBatchSize(writeBatchSize)
                .writeFlushInterval(writeFlushInterval)
                .writeBufferLimit(writeBufferLimit)
                .writeJitterInterval(writeJitterInterval)
                .build();
    }

    @Bean
    public DefaultInfluxDbWriter defaultInfluxDbWriter() {
        return new DefaultInfluxDbWriter(writeApi());
    }

    @Bean
    public WriteApi writeApi() {
        logger.info("Initializing Async InfluxDb Write API with batching.");
        return influxDBClient().makeWriteApi(WriteOptions.builder()
                .batchSize(influxDbWriteOptions().writeBatchSize())
                .flushInterval(influxDbWriteOptions().writeFlushInterval())
                .bufferLimit(influxDbWriteOptions().writeBufferLimit())
                .jitterInterval(influxDbWriteOptions().writeJitterInterval())
                .build());
    }

    @Bean
    public DefaultInfluxDbReader defaultInfluxDbReader() {
        return new DefaultInfluxDbReader(queryApi());
    }

    @Bean
    public QueryApi queryApi() {
        logger.info("Initializing default InfluxDb Query API.");
        return influxDBClient().getQueryApi();
    }
}
