package com.services.pricehistory.infrastructure.configuration;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.services.pricehistory.application.ws.client.InstrumentsAdapter;
import com.services.pricehistory.application.ws.client.QuotesAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import java.io.IOException;

/**
 * @author Omar Al-Shalabi
 */
@Configuration
public class WebSocketConfiguration {

    @Value("${partner-service.port}")
    public int partnerServicePort;

    @Value("${partner-service.instruments-endpoint}")
    public String InstrumentsEndpoint;

    @Value("${partner-service.quotes-endpoint}")
    public String quotesEndpooint;

    @Value("${partner-service.connection-timeout-millis}")
    public int connectionTimeoutMillis;

    private final InstrumentsAdapter instrumentsAdapter;
    private final QuotesAdapter quotesAdapter;

    public WebSocketConfiguration(@NonNull final InstrumentsAdapter instrumentsAdapter,
                                  @NonNull final QuotesAdapter quotesAdapter) {
        this.instrumentsAdapter = instrumentsAdapter;
        this.quotesAdapter = quotesAdapter;
    }

    @Bean
    public WebSocket instrumentsWebSocket() throws IOException, WebSocketException {
        final String instrumentsWebSocketURL = "ws://localhost:" + partnerServicePort + InstrumentsEndpoint;
        return new WebSocketFactory()
                .setConnectionTimeout(connectionTimeoutMillis)
                .createSocket(instrumentsWebSocketURL)
                .addListener(instrumentsAdapter)
                .connect();
    }

    @Bean
    public WebSocket quotesWebSocket() throws IOException, WebSocketException {
        final String instrumentsWebSocketURL = "ws://localhost:" + partnerServicePort + quotesEndpooint;
        return new WebSocketFactory()
                .setConnectionTimeout(connectionTimeoutMillis)
                .createSocket(instrumentsWebSocketURL)
                .addListener(quotesAdapter)
                .connect();
    }
}
