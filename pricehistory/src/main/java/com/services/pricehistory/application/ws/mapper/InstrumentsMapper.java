package com.services.pricehistory.application.ws.mapper;

import com.alibaba.fastjson.JSONObject;
import com.services.pricehistory.domain.dto.instrument.ImmutableInstrument;
import com.services.pricehistory.domain.dto.instrument.InstrumentActionType;
import com.services.pricehistory.domain.dto.instrument.InstrumentData;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author Omar Al-Shalabi
 */
@Component
public class InstrumentsMapper {

    public ImmutableInstrument toImmutableInstrument(@NonNull final String inputStream) {
        final Instant timeNow = Instant.now();
        final JSONObject streamJson = JSONObject.parseObject(inputStream);
        if (streamJson == null) {
            throw new RuntimeException("Insturment Message is null");
        }
        final InstrumentData instrumentData = new InstrumentData(streamJson.getJSONObject("data")
                .getString("description"), streamJson.getJSONObject("data").getString("isin"));
        return new ImmutableInstrument(instrumentData, getActionType(streamJson), timeNow);
    }

    private InstrumentActionType getActionType(final JSONObject streamJson) {
        return JSONObject.parseObject(streamJson.getString("type"), InstrumentActionType.class);
    }
}
