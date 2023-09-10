package com.services.pricehistory.infrastructure.models;

import org.immutables.value.Value;

/**
 * @author Omar Al-Shalabi
 */
@Value.Immutable
public interface InfluxDbWriteOptions {
    int writeBatchSize();

    int writeFlushInterval();

    int writeBufferLimit();

    int writeJitterInterval();
}
