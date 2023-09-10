package com.services.pricehistory.application.ws.mapper;

import com.alibaba.fastjson.JSONObject;
import com.services.pricehistory.domain.dto.quote.ImmutableQuote;
import com.services.pricehistory.domain.dto.quote.QuoteData;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author Omar Al-Shalabi
 */
@Component
public class QuotesMapper {

    public ImmutableQuote toImmutableQuote(@NonNull final String inputStream) {
        final Instant timeNow = Instant.now();
        final JSONObject streamData = JSONObject.parseObject(inputStream).getJSONObject("data");
        if (streamData == null) {
            throw new RuntimeException(String.format("Quotes Message has null 'data' parameter. Message: %s",
                    inputStream));
        }
        final QuoteData quoteData = new QuoteData(new BigDecimal(streamData.getBigInteger("price")),
                streamData.getString("isin"));
        return new ImmutableQuote(quoteData, timeNow);
    }
}
