package com.manish.elwood.quants;

import com.manish.elwood.common.Tick;
import com.manish.elwood.intf.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class manages the calculation and distribution of averages for different types of calculators.
 * It subscribes to Tick events and delegates the calculation to the appropriate Calculator
 * instances. It also notifies registered listeners with the calculated averages.
 */
public class TickManager implements TickListener {

    /**
     * A map to store the different types of calculators.
     * Key: String representing the calculator type (e.g., "EMA", "MovingAvrage").
     * Value: The corresponding Calculator instance.
     */
    private final Map<String, Calculator> calculators;

    /**
     * A map to store listeners for each calculator type.
     * Key: String representing the calculator type.(e.g., "EMA", "MovingAvrage")
     * IMPORTANT : This key needs to be matching to one of the key in the other map which stores calculators.
     * Value: List of AverageListeners registered for that calculator type.
     */
    private final Map<String, List<AverageListener>> listeners;

    /**
     * Constructs a new TickManager instance.
     *
     * @param calculators A map of calculators, where the key is the calculator type and the value is the calculator instance.
     */
    public TickManager(Map<String, Calculator> calculators) {
        this.calculators = calculators;
        this.listeners = new HashMap<>();
        for (String key : calculators.keySet()) {
            listeners.put(key, new ArrayList<>());
        }
    }

    /**
     * Adds an AverageListener to the list of listeners for a specific calculator type.
     *
     * @param calculatorType The type of calculator to register the listener for.
     * @param listener The AverageListener to be added.
     */
    public void addListener(String calculatorType, AverageListener listener) {
        listeners.get(calculatorType).add(listener);
    }

    /**
     * Handles a new Tick event.
     * Calculates the average for each calculator type and notifies the registered listeners.
     *
     * @param tick The received Tick object.
     */
    @Override
    public void onTick(Tick tick) {
        for (Map.Entry<String, Calculator> entry : calculators.entrySet()) {
            String type = entry.getKey();
            Calculator calculator = entry.getValue();
            BigDecimal average = calculator.calculate(tick);
            for (AverageListener listener : listeners.get(type)) {
                listener.onAverage(type, average);
            }
        }
    }

    /**
     * Cancels the calculation for all calculators.
     */
    @Override
    public void onCancel() {
        for (Calculator calculator : calculators.values()) {
            calculator.cancel();
        }
    }

    /**
     * Resumes the calculation for all calculators.
     */
    @Override
    public void onResume() {
        for (Calculator calculator : calculators.values()) {
            calculator.resume();
        }
    }

    /**
     * Resets all calculators.
     */
    @Override
    public void onReset() {
        for (Calculator calculator : calculators.values()) {
            calculator.reset();
        }
    }
}