package com.services.pricehistory.infrastructure.database;

import com.influxdb.client.write.Point;
import org.springframework.lang.NonNull;

/**
 * @author Omar Al-Shalabi
 */
public interface InfluxDbWriter {

    void writePoint(@NonNull final Point point);
}
