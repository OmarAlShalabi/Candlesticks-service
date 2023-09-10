package com.services.pricehistory.application.ws.client;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.services.pricehistory.application.ws.mapper.QuotesMapper;
import com.services.pricehistory.domain.service.QuoteService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author Omar Al-Shalabi
 */
@Component
public class QuotesAdapter extends WebSocketAdapter {

    private final QuotesMapper quotesMapper;
    private final QuoteService quoteService;

    public QuotesAdapter(@NonNull final QuotesMapper quotesMapper,
                         @NonNull final QuoteService quoteService) {
        this.quotesMapper = quotesMapper;
        this.quoteService = quoteService;
    }

    @Override
    public void onTextMessage(WebSocket websocket, String message) {
        quoteService.saveQuote(quotesMapper.toImmutableQuote(message));
    }
}
