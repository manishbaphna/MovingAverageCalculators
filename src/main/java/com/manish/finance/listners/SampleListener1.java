package com.manish.finance.listners;

import com.manish.finance.intf.AverageListener;

import java.math.BigDecimal;

public class SampleListener1 implements AverageListener {

    /**
     * Handles the event when an average value is calculated.
     * This method is called to process and display the average value for a specific type.
     *
     * @param type The type or category of the average being reported.
     * @param avg The calculated average value as a BigDecimal.
     */
    @Override
    public void onAverage(String type, BigDecimal avg) {
        System.out.println("SampleListener1: Received " + type + " average: " + avg);
    }
}
