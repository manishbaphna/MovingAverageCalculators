package com.manish.finance.quants;

import com.manish.finance.calculators.ExponentialAverageCalculator;
import com.manish.finance.calculators.MovingAverageCalculator;
import com.manish.finance.calculators.WindowedAverageCalculator;
import com.manish.finance.common.Tick;
import com.manish.finance.intf.Calculator;
import com.manish.finance.listners.SampleListener1;
import com.manish.finance.listners.SampleListener2;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

class TickManagerTest {

    @Test
    void sampleAppUsage() {

        // This is how a system can be created with few calculators and listeners. It's not an assertion test, rather
        // a sample application usage for demonstration purposes.
        final String MOVING_AVERAGE = "MovingAverage";
        final String EXPONENTIAL_AVERAGE = "ExponentialMovingAverage";
        final String WINDOWED_AVERAGE = "WindowedAverage";

        Map<String, Calculator> calculators = new HashMap<>();
        calculators.put(MOVING_AVERAGE, new MovingAverageCalculator(5));
        calculators.put(EXPONENTIAL_AVERAGE, new ExponentialAverageCalculator(5, BigDecimal.valueOf(0.75)));
        calculators.put(WINDOWED_AVERAGE, new WindowedAverageCalculator(Duration.ofMinutes(5)));

        // Test creation of a new TickManager object
        TickManager tickManager = new TickManager(calculators);

        // create SampleListeners
        SampleListener1 listener1 = new SampleListener1();
        SampleListener2 listener2 = new SampleListener2();

        // add listeners to the TickManager
        tickManager.addListener(MOVING_AVERAGE, listener1);
        tickManager.addListener(EXPONENTIAL_AVERAGE, listener2);
        tickManager.addListener(WINDOWED_AVERAGE, listener1);
        tickManager.addListener(WINDOWED_AVERAGE, listener2);


        tickManager.onTick(new Tick(BigDecimal.valueOf(100), Instant.now()));
        tickManager.onTick(new Tick(BigDecimal.valueOf(110), Instant.now().plusSeconds(60)));
        tickManager.onTick(new Tick(BigDecimal.valueOf(120), Instant.now().minus(Duration.ofMinutes(4))));
        tickManager.onTick(new Tick(BigDecimal.valueOf(130), Instant.now().plusSeconds(120)));
        tickManager.onTick(new Tick(BigDecimal.valueOf(90), Instant.now().minusSeconds(120)));
        tickManager.onTick(new Tick(BigDecimal.valueOf(80), Instant.now().minusSeconds(600)));

        tickManager.onReset();
        tickManager.onTick(new Tick(BigDecimal.valueOf(112), Instant.now().plusSeconds(60)));

        Tick[] ticks = new Tick[10];
        // add ticks in above array
        for (int i = 0; i < 10; ++i){
            ticks[i] = new Tick(BigDecimal.valueOf(100 + i), Instant.now().plusSeconds(i * 60));
        }
        tickManager.onTicks(ticks);

    }



}