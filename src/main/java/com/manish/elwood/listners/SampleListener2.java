package com.manish.elwood.listners;

import com.manish.elwood.intf.AverageListener;

import java.math.BigDecimal;

public class SampleListener2 implements AverageListener {

    /**
     * Handles the event when an average value is calculated.
     * This method is called to process and display the average value for a specific type.
     *
     * @param type The type or category of the average being reported.
     * @param avg The calculated average value as a BigDecimal.
     */
    @Override
    public void onAverage(String type, BigDecimal avg) {
        System.out.println("SampleListener2: Received " + type + " average: " + avg);
    }
}
