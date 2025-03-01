package com.manish.elwood.calculators;

import com.manish.elwood.common.Tick;
import com.manish.elwood.intf.Calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;

/**
 * This class calculates the Windowed Average of a series of Ticks.
 *
 * <p>The Windowed Average calculates the average price of all Ticks that occurred within a
 * specified time window. </p>
 *
 * <p>This implementation maintains a window of Ticks and removes older Ticks that fall
 * outside the specified time window.</p>
 */
public class WindowedAverageCalculator implements Calculator {
    private final Duration windowDuration;
    private final LinkedList<Tick> window;
    private BigDecimal sum;
    private boolean reset;
    private boolean cancel;

    /**
     * Constructs a WindowedAverageCalculator with the given window duration.
     *
     * @param windowDuration The duration of the time window for the average calculation.
     */
    public WindowedAverageCalculator(Duration windowDuration) {
        this.windowDuration = windowDuration;
        this.window = new LinkedList<>();
        this.sum  = BigDecimal.ZERO;
        this.reset = false;
        this.cancel = false;
    }

    /**
     * Calculates the Windowed Average for the given Tick.
     *
     * @param tick The current Tick.
     * @return The calculated Windowed Average.
     */
    @Override
    public BigDecimal calculate(Tick tick) {

        if (cancel) {
            reset();
            return BigDecimal.ZERO; // Cancelled, so return zero average
        }

        if (reset) {
            reset = false;
            sum = BigDecimal.ZERO;
            window.clear();
        }

        // Add the new tick to the window & running sum
        window.addLast(tick);
        sum = sum.add(tick.getPrice());

        // Remove ticks that are outside the time window
        Instant cutoffTime = tick.getTimestamp().minus(windowDuration);
        while (!window.isEmpty() && window.getFirst().getTimestamp().isBefore(cutoffTime)) {
            sum = sum.subtract(window.removeFirst().getPrice());
        }

        // Calculate the average if we have ticks in the window
        return sum.divide(BigDecimal.valueOf(window.size()), 2, RoundingMode.HALF_UP);
    }

    /**
     * Resets the EMA calculation.
     */
    @Override
    public void reset() {
        reset = true;
    }

    /**
     * Cancels the EMA calculation.
     */
    @Override
    public void cancel() {
        cancel = true;
    }

    /**
     * Resumes the EMA calculation.
     */
    @Override
    public void resume() {
        cancel = false;
    }
}
