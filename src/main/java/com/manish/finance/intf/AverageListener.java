package com.manish.finance.intf;

import java.math.BigDecimal;

/**
 * Listener interface for receiving average calculations. Any comsumer of avrage values should implement this interface.
 * It will then need to register themselves with an appropriate calculation instance, via TickManager, to receive these updates.
 */
public interface AverageListener {
    /**
     * Called when an average calculation is completed.
     *
     * @param type The type or category of the average calculation.
     * @param avg The calculated average value.
     */
    void onAverage(String type, BigDecimal avg);
}