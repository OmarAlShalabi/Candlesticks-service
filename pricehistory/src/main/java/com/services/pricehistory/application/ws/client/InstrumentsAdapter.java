package com.services.pricehistory.application.ws.client;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.services.pricehistory.application.ws.mapper.InstrumentsMapper;
import com.services.pricehistory.domain.service.InstrumentService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author Omar Al-Shalabi
 */
@Component
public class InstrumentsAdapter extends WebSocketAdapter {

    private final InstrumentsMapper instrumentsMapper;
    private final InstrumentService instrumentService;

    public InstrumentsAdapter(@NonNull final InstrumentsMapper instrumentsMapper
            , @NonNull final  InstrumentService instrumentService) {
        this.instrumentsMapper = instrumentsMapper;
        this.instrumentService = instrumentService;
    }

    @Override
    public void onTextMessage(WebSocket websocket, String message) {
        instrumentService.saveInstrument(instrumentsMapper.toImmutableInstrument(message));
    }
}
