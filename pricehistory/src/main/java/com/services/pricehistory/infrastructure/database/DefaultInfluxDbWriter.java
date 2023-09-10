package com.services.pricehistory.infrastructure.database;

import com.influxdb.client.WriteApi;
import com.influxdb.client.write.Point;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;

/**
 * @author Omar Al-Shalabi
 */
public class DefaultInfluxDbWriter implements InfluxDbWriter {

    private final WriteApi writeApi;

    public DefaultInfluxDbWriter(@NonNull final WriteApi writeApi) {
        this.writeApi = writeApi;
    }

    @Override
    public void writePoint(@NotNull final Point point) {
        writeApi.writePoint(point);
    }
}
