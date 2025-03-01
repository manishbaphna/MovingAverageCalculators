package com.manish.elwood.calculators;

import com.manish.elwood.common.Tick;
import com.manish.elwood.intf.Calculator;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


/**
 * This class calculates the Exponential Moving Average (EMA) of a series of Ticks.
 * For exponential moving average, use the formula:
 * 𝐸𝑀𝐴(𝑡) = α · 𝑃𝑟𝑖𝑐𝑒𝑡(t) + (1 − α) · 𝐸𝑀𝐴(𝑡−1)
 * where:
 * <ul>
 *     <li>EMA(t) is the Exponential Moving Average at time t</li>
 *     <li>α (alpha) is the smoothing factor (0 < α <= 1)</li>
 *     <li>Price(t) is the price of the current Tick</li>
 *     <li>EMA(t-1) is the previous Exponential Moving Average</li>
 * </ul>
 *
 * <p>This implementation maintains a window of the last N Ticks and adjusts the EMA accordingly.</p>
 */
public class ExponentialAverageCalculator implements Calculator {
    BigDecimal alpha;
    private int windowSize;
    private List<Tick> window;
    private BigDecimal ema;

    // Factor to use to remove impact of an old entry , which is getting evicted from the window.
    private BigDecimal removalFactor;
    private boolean reset;

    private boolean cancel;


    /**
     * Constructs an ExponentialAverageCalculator with the given window size and alpha value.
     *
     * @param windowSize The size of the window for the moving average calculation.
     * @param alpha      The smoothing factor (0 < alpha <= 1).
     */
    public ExponentialAverageCalculator(int windowSize, BigDecimal alpha) {
        this.windowSize = windowSize;
        window = new LinkedList<>();
        this.alpha = alpha;
        this.ema = BigDecimal.ZERO;
        this.removalFactor = BigDecimal.ONE.subtract(alpha).pow(windowSize-1);
        this.reset = false;
        this.cancel = false;
    }

    /**
     * Calculates the Exponential Moving Average for the given Tick.
     *
     * @param tick The current Tick.
     * @return The calculated Exponential Moving Average.
     */
    @Override
    public BigDecimal calculate(Tick tick) {

        if (cancel) {
            // Cancel the calculation
            reset();
            return BigDecimal.ZERO;
        }

        if (reset) {
            // Reset the EMA and the window
            ema = BigDecimal.ZERO;
            window.clear();
            reset = false;
            System.out.println("Resetting EMA and window");
        }

        window.add(tick);

        if (window.size() > windowSize) {
            // Remove the oldest tick
            Tick oldestTick = window.remove(0);
            // Calculate the previous EMA without the 'impact of oldest tick'
            BigDecimal prevEmaWithoutOldest = ema.subtract(oldestTick.getPrice().multiply(alpha).multiply(removalFactor));
            System.out.println("removing oldest tick: " + oldestTick.getPrice() + " from EMA calculation. impact " + prevEmaWithoutOldest);
            // Recalculate EMA with the new tick
            ema = prevEmaWithoutOldest.multiply(BigDecimal.ONE.subtract(alpha)).add(tick.getPrice().multiply(alpha));
        } else if (window.size() == 1) {
            // Initialize EMA with the first tick's price
            ema = tick.getPrice().multiply(alpha);
        } else {
            // Calculate EMA for subsequent ticks
            ema = ema.multiply(BigDecimal.ONE.subtract(alpha)).add(tick.getPrice().multiply(alpha));
            System.out.println("EMA latest: " + ema + " with tick's price: " + tick.getPrice());

        }
        return ema;
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
