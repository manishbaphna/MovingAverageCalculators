package com.manish.finance.calculators;

import com.manish.finance.common.Tick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

import static com.manish.finance.testutils.TestUtilis.assertEqualsBigDecimal;

class WindowedAverageCalculatorTest {

    private WindowedAverageCalculator calculator;
    private static final Duration WINDOW_DURATION = Duration.ofMinutes(5);

    @BeforeEach
    void setUp() {
        calculator = new WindowedAverageCalculator(WINDOW_DURATION);
    }

    @Test
    void testCalculateWithSingleTick() {
        Tick tick = new Tick(BigDecimal.valueOf(10.0), Instant.now());
        BigDecimal result = calculator.calculate(tick);
        assertEqualsBigDecimal(BigDecimal.valueOf(10.00), result);
    }

    @Test
    void testCalculateWithMultipleTicks() {
        Instant now = Instant.now();
        calculator.calculate(new Tick(BigDecimal.valueOf(10.0), now));
        calculator.calculate(new Tick(BigDecimal.valueOf(20.0), now.plusSeconds(60)));
        BigDecimal result = calculator.calculate(new Tick(BigDecimal.valueOf(30.0), now.plusSeconds(120)));
        assertEqualsBigDecimal(BigDecimal.valueOf(20.00), result);
    }

    @Test
    void testCalculateWithTicksOutsideWindow() {
        Instant now = Instant.now();
        calculator.calculate(new Tick(BigDecimal.valueOf(10.0), now.minus(Duration.ofMinutes(10))));
        calculator.calculate(new Tick(BigDecimal.valueOf(20.0), now.minus(Duration.ofMinutes(6))));
        BigDecimal result = calculator.calculate(new Tick(BigDecimal.valueOf(30.0), now));
        assertEqualsBigDecimal(BigDecimal.valueOf(30.00), result);
    }

    @Test
    void testCalculateWithMixedTicks() {
        Instant now = Instant.now();
        calculator.calculate(new Tick(BigDecimal.valueOf(10.0), now.minus(Duration.ofMinutes(6))));
        calculator.calculate(new Tick(BigDecimal.valueOf(20.0), now.minus(Duration.ofMinutes(4))));
        calculator.calculate(new Tick(BigDecimal.valueOf(30.0), now.minus(Duration.ofMinutes(2))));
        BigDecimal result = calculator.calculate(new Tick(BigDecimal.valueOf(40.0), now));
        assertEqualsBigDecimal(BigDecimal.valueOf(30.00), result);
    }

    @Test
    void testCalculateWithEmptyWindow() {
        Instant now = Instant.now();
        calculator.calculate(new Tick(BigDecimal.valueOf(10.0), now.minus(Duration.ofMinutes(10))));
        BigDecimal result = calculator.calculate(new Tick(BigDecimal.valueOf(20.0), now));
        assertEqualsBigDecimal(BigDecimal.valueOf(20.00), result);
    }

    @Test
    void testCalculateWithPreciseValues() {
        Instant now = Instant.now();
        calculator.calculate(new Tick(new BigDecimal("10.123"), now));
        calculator.calculate(new Tick(new BigDecimal("20.456"), now.plusSeconds(60)));
        BigDecimal result = calculator.calculate(new Tick(new BigDecimal("30.789"), now.plusSeconds(120)));
        assertEqualsBigDecimal(new BigDecimal("20.46"), result);
    }

    @Test
    void testCalculateWithMultipleTicksAndReset() {
        Instant now = Instant.now();
        calculator.calculate(new Tick(BigDecimal.valueOf(10.0), now));
        calculator.calculate(new Tick(BigDecimal.valueOf(20.0), now.plusSeconds(60)));
        calculator.reset();
        BigDecimal result = calculator.calculate(new Tick(BigDecimal.valueOf(30.0), now.plusSeconds(120)));
        assertEqualsBigDecimal(BigDecimal.valueOf(30.00), result);
    }

}
