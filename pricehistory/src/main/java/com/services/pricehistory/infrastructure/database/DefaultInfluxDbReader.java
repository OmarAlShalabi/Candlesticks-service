package com.services.pricehistory.infrastructure.database;

import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxTable;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Omar Al-Shalabi
 */
public class DefaultInfluxDbReader implements InfluxDbReader {

    private final QueryApi queryApi;

    public DefaultInfluxDbReader(@NonNull final QueryApi queryApi) {
        this.queryApi = queryApi;
    }

    @Override
    public List<FluxTable> executeFluxQuery(@NonNull final String fluxQuery) {
        return queryApi.query(fluxQuery);
    }
}
