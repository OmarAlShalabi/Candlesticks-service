package com.services.pricehistory.infrastructure.database;

import com.influxdb.query.FluxTable;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Omar Al-Shalabi
 */
public interface InfluxDbReader {

    List<FluxTable> executeFluxQuery(@NonNull final String fluxQuery);
}
