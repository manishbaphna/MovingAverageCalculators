package com.manish.elwood.common;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Represents a financial tick with price and timestamp information.
 */
public class Tick {

    private final BigDecimal price;
    private final Instant timestamp;

    /**
     * Constructs a new Tick object with the specified price and timestamp.
     *
     * @param price     The price of the tick as a BigDecimal.
     * @param timestamp The timestamp of the tick as an Instant.
     */
    public Tick(BigDecimal price, Instant timestamp) {
        this.price = price;
        this.timestamp = timestamp;
    }

    /**
     * Retrieves the price of the tick.
     *
     * @return The price as a BigDecimal.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Retrieves the timestamp of the tick.
     *
     * @return The timestamp as an Instant.
     */
    public Instant getTimestamp() {
        return timestamp;
    }
}