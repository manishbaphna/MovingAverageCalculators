package com.manish.elwood.intf;
import com.manish.elwood.common.Tick;

/**
 * An interface for listening to tick-related events.
 */
public interface TickListener {
    /**
     * Called when a new tick is received.
     *
     * @param tick The Tick object containing the tick data.
     */
    void onTick(Tick tick);

    /**
     * Called when the tick processing is cancelled. Any ticks after this action
     * will be ignored ie. no average calculations will be performed, till onResume is called.
     *
     */
    void onCancel();

    /**
     * Called when the tick processing needs to be resumed. Calculations will start from the first tick received
     * after the resume and impact of any ticks received before the resume is ignored.
     */
    void onResume();

    /**
     * Processes an array of ticks by calling onTick for each tick.
     * This is a default method that can be overridden if needed.
     * This is an additional functionality to allow processing of multiple ticks in a single call.
     * @param ticks An array of Tick objects to be processed.
     */
    default void onTicks(Tick[] ticks) {
        for (Tick tick : ticks) {
            onTick(tick);
        }
    }

    /**
     * Called when the 'moving' sum of the calcualtions is reset i.e. to start calclualtion afresh from the given tick.
     */
    void onReset();
}
