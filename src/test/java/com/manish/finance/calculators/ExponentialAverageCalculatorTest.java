package com.manish.finance.calculators;

import com.manish.finance.common.Tick;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static com.manish.finance.testutils.TestUtilis.assertEqualsBigDecimal;

class ExponentialAverageCalculatorTest {

    ExponentialAverageCalculator calculator;

    @Test
    void shouldCalculateExponentialAverage_N2_Alpha1() {
        int windowSize = 2;
        BigDecimal alpha = BigDecimal.ONE;

        calculator = new ExponentialAverageCalculator(windowSize, alpha);
        // Create a sample Tick object
        Tick tick_10 = new Tick(BigDecimal.valueOf(10.0), Instant.now());
        Tick tick_20 = new Tick(BigDecimal.valueOf(20.0), Instant.now());
        Tick tick_30 = new Tick(BigDecimal.valueOf(30.0), Instant.now());

        // Test with initial value
        assertEqualsBigDecimal(BigDecimal.valueOf(10.0), calculator.calculate(tick_10));
        assertEqualsBigDecimal(BigDecimal.valueOf(20.0), calculator.calculate(tick_20));
        assertEqualsBigDecimal(BigDecimal.valueOf(30.0), calculator.calculate(tick_30));
    }

    @Test
    void shouldCalculateExponentialAverage_N2_Alpha0Point5() {
        int windowSize = 2;
        BigDecimal alpha = BigDecimal.valueOf(0.5);

        calculator = new ExponentialAverageCalculator(windowSize, alpha);
        // Create a sample Tick object
        Tick tick_10 = new Tick(BigDecimal.valueOf(10.0), Instant.now());
        Tick tick_20 = new Tick(BigDecimal.valueOf(20.0), Instant.now());
        Tick tick_30 = new Tick(BigDecimal.valueOf(30.0), Instant.now());

        // Test with initial value
        assertEqualsBigDecimal(BigDecimal.valueOf(5.0), calculator.calculate(tick_10));
        assertEqualsBigDecimal(BigDecimal.valueOf(12.5), calculator.calculate(tick_20));
        assertEqualsBigDecimal(BigDecimal.valueOf(20), calculator.calculate(tick_30));
    }

    @Test
    void shouldCalculateExponentialAverage_N3_Alpha0Point75() {
        int windowSize = 3;
        BigDecimal alpha = BigDecimal.valueOf(0.75);

        calculator = new ExponentialAverageCalculator(windowSize, alpha);

        // Create a sample Tick object
        Tick tick_10 = new Tick(BigDecimal.valueOf(10.0), Instant.now());
        Tick tick_20 = new Tick(BigDecimal.valueOf(20.0), Instant.now());
        Tick tick_30 = new Tick(BigDecimal.valueOf(30.0), Instant.now());
        Tick tick_40 = new Tick(BigDecimal.valueOf(40.0), Instant.now());
        Tick tick_50 = new Tick(BigDecimal.valueOf(50.0), Instant.now());


        // Test with initial value
        assertEqualsBigDecimal(BigDecimal.valueOf(7.5), calculator.calculate(tick_10));
        assertEqualsBigDecimal(BigDecimal.valueOf(16.875), calculator.calculate(tick_20));
        assertEqualsBigDecimal(BigDecimal.valueOf(26.7187500), calculator.calculate(tick_30));
        assertEqualsBigDecimal(BigDecimal.valueOf(36.562500000), calculator.calculate(tick_40));
        assertEqualsBigDecimal(BigDecimal.valueOf(46.40625000), calculator.calculate(tick_50));
    }

    @Test
    void shouldCalculateExponentialAverage_after_reset() {
        int windowSize = 3;
        BigDecimal alpha = BigDecimal.valueOf(0.75);

        calculator = new ExponentialAverageCalculator(windowSize, alpha);

        // Create a sample Tick object
        Tick tick_10 = new Tick(BigDecimal.valueOf(10.0), Instant.now());
        Tick tick_20 = new Tick(BigDecimal.valueOf(20.0), Instant.now());
        Tick tick_30 = new Tick(BigDecimal.valueOf(30.0), Instant.now());

        // Test with initial value
        assertEqualsBigDecimal(BigDecimal.valueOf(7.5), calculator.calculate(tick_10));
        assertEqualsBigDecimal(BigDecimal.valueOf(16.875), calculator.calculate(tick_20));
        calculator.reset();
        assertEqualsBigDecimal(BigDecimal.valueOf(22.5), calculator.calculate(tick_30));
    }

    @Test
    void testCalculateWithMultipleTicksAndCancel() {
        int windowSize = 3;
        BigDecimal alpha = BigDecimal.valueOf(0.75);

        calculator = new ExponentialAverageCalculator(windowSize, alpha);

        // Create a sample Tick object
        Tick tick_10 = new Tick(BigDecimal.valueOf(10.0), Instant.now());
        Tick tick_20 = new Tick(BigDecimal.valueOf(20.0), Instant.now());
        Tick tick_30 = new Tick(BigDecimal.valueOf(30.0), Instant.now());

        // Test with initial value
        assertEqualsBigDecimal(BigDecimal.valueOf(7.5), calculator.calculate(tick_10));
        assertEqualsBigDecimal(BigDecimal.valueOf(16.875), calculator.calculate(tick_20));
        calculator.cancel();
        assertEqualsBigDecimal(BigDecimal.ZERO, calculator.calculate(tick_30));
        assertEqualsBigDecimal(BigDecimal.ZERO, calculator.calculate(tick_20));
        calculator.resume();
        assertEqualsBigDecimal(BigDecimal.valueOf(22.5), calculator.calculate(tick_30));

    }

}