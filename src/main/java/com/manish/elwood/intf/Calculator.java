package com.manish.elwood.intf;

import com.manish.elwood.common.Tick;

import java.math.BigDecimal;

/**
 * This interface provides a common contract for calculating results based on tick data.
 */
public interface Calculator {
    /**
     * Calculates a result based on the provided tick data.
     *
     * @param tick The tick data to be processed.
     * @return A BigDecimal representing the calculated result.
     */
    BigDecimal calculate(Tick tick);

    /**
     * Resets the calculator to its initial state , primarily it resets the 'moving' sum to calculations start fresh.
     */
    void reset();

    /**
     * Cancels the calculation process and return zero value till it's resumed.
     */
    void cancel();

    /**
     * Resumes the calculation process after it has been cancelled.
     */
    void resume();
}
