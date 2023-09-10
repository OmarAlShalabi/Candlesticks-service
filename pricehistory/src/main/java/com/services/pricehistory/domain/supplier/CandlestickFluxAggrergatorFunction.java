package com.services.pricehistory.domain.supplier;

import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author Omar Al-Shalabi
 */
@Component
public class CandlestickFluxAggrergatorFunction implements Supplier<String> {

    @Override
    public String get() {
        return """
                candlestick = (tables=<-) => tables
                  |> reduce(
                    fn: (r, accumulator) => ({
                      index: accumulator.index + 1,
                      open: if accumulator.index == 0 then r._value else accumulator.open,
                      close: r._value,
                      high: if accumulator.index == 0 then r._value else if r._value > accumulator.high then r._value else accumulator.high,
                      low: if accumulator.index == 0 then r._value else if r._value < accumulator.low then r._value else accumulator.low
                    }),
                    identity: { index: 0, open: 0.0, close: 0.0, high: 0.0, low: 0.0 }
                  )
                  |> drop(columns: ["index"])
                """;    }
}
