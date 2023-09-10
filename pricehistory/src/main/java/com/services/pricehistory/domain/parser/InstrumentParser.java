package com.services.pricehistory.domain.parser;

import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.services.pricehistory.domain.dto.instrument.InstrumentActionType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author Omar Al-Shalabi
 */
@Component
public class InstrumentParser {

    public InstrumentActionType getActionTypeFromFluxTable(@NonNull final List<FluxTable> fluxTables) {
        if (fluxTables.isEmpty()) {
            return InstrumentActionType.DELETE;
        }
        final FluxTable fluxTable = fluxTables.get(0);
        if (fluxTable.getRecords().isEmpty()) {
            return InstrumentActionType.DELETE;
        }
        final FluxRecord fluxRecord = fluxTable.getRecords().get(fluxTable.getRecords().size() - 1);
        return Objects.requireNonNull(fluxRecord.getValueByKey("_value")).toString().equals("DELETE") ?
                InstrumentActionType.DELETE : InstrumentActionType.ADD;
    }
}
