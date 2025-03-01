package com.manish.elwood.calculators;

import com.manish.elwood.common.Tick;
import com.manish.elwood.intf.Calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

/**
 * This class calculates the Simple Moving Average (SMA) of a series of Ticks.
 *
 * <p>The SMA calculates the average price of the last 'windowSize' Ticks.</p>
 *
 * <p>This implementation maintains a window of the last N Ticks and calculates the average of their prices.</p>
 */
public class MovingAverageCalculator implements Calculator {
    private int windowSize;
    private List<Tick> window;
    private BigDecimal sum;
    boolean reset;
    boolean cancel;

    /**
     * Constructs a MovingAverageCalculator with the specified window size.
     *
     * @param windowSize The size of the window for the moving average calculation.
     */
    public MovingAverageCalculator(int windowSize) {
        this.windowSize = windowSize;
        this.window = new LinkedList<>();
        this.sum = BigDecimal.ZERO;
        this.reset = false;
        this.cancel = false;
    }

    /**
     * Calculates the Simple Moving Average for the given Tick.
     *
     * @param tick The current Tick.
     * @return The calculated Simple Moving Average.
     */
    @Override
    public BigDecimal calculate(Tick tick) {
        // If calculation is cancelled, return 0 immediately i.e. ignore any tick updates
        if (cancel) {
            reset(); // Cancelled calculation, reset the window when you resume
            return BigDecimal.ZERO;  // Cancelled calculation, return 0
        }

        if (reset) {
            reset = false;
            window.clear();
            sum = BigDecimal.ZERO;
        }
        // Add the new tick to the window
        window.add(tick);
        sum = sum.add(tick.getPrice());

        // If the window is full, remove the oldest tick
        if (window.size() > windowSize) {
            Tick oldestTick = window.remove(0);
            sum = sum.subtract(oldestTick.getPrice());
        }

        // Calculate the average
        return sum.divide(BigDecimal.valueOf(window.size()), 4, RoundingMode.HALF_UP);
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
