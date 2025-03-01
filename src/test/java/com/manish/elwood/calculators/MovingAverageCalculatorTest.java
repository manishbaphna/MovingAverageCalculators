package com.manish.elwood.calculators;

import com.manish.elwood.common.Tick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static com.manish.elwood.testutils.TestUtilis.assertEqualsBigDecimal;
import static org.junit.jupiter.api.Assertions.assertNull;

class MovingAverageCalculatorTest {

    private MovingAverageCalculator calculator;
    private static final int WINDOW_SIZE = 3;

    @BeforeEach
    void setUp() {
        calculator = new MovingAverageCalculator(WINDOW_SIZE);
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
        calculator.calculate(new Tick(BigDecimal.valueOf(20.0), now.plusSeconds(1)));
        BigDecimal result = calculator.calculate(new Tick(BigDecimal.valueOf(30.0), now.plusSeconds(2)));
        assertEqualsBigDecimal(BigDecimal.valueOf(20.00), result);
    }

    @Test
    void testCalculateWithMoreTicksThanWindowSize() {
        Instant now = Instant.now();
        calculator.calculate(new Tick(BigDecimal.valueOf(10.0), now));
        calculator.calculate(new Tick(BigDecimal.valueOf(20.0), now.plusSeconds(1)));
        calculator.calculate(new Tick(BigDecimal.valueOf(30.0), now.plusSeconds(2)));
        BigDecimal result = calculator.calculate(new Tick(BigDecimal.valueOf(40.0), now.plusSeconds(3)));
        assertEqualsBigDecimal(BigDecimal.valueOf(30.00), result);
    }

    @Test
    void testCalculateWithPreciseValues() {
        Instant now = Instant.now();
        calculator.calculate(new Tick(new BigDecimal("10.123"), now));
        calculator.calculate(new Tick(new BigDecimal("20.456"), now.plusSeconds(1)));
        BigDecimal result = calculator.calculate(new Tick(new BigDecimal("30.789"), now.plusSeconds(2)));
        assertEqualsBigDecimal(new BigDecimal("20.456"), result);
    }

    @Test
    void testCalculateWithZeroValues() {
        Instant now = Instant.now();
        calculator.calculate(new Tick(BigDecimal.ZERO, now));
        calculator.calculate(new Tick(BigDecimal.ZERO, now.plusSeconds(1)));
        BigDecimal result = calculator.calculate(new Tick(BigDecimal.ZERO, now.plusSeconds(2)));
        assertEqualsBigDecimal(BigDecimal.ZERO, result);
    }

    @Test
    void testCalculateWithMultipleTicksAndReset() {
        Instant now = Instant.now();
        calculator.calculate(new Tick(BigDecimal.valueOf(10.0), now));
        calculator.calculate(new Tick(BigDecimal.valueOf(20.0), now.plusSeconds(1)));
        calculator.reset();
        BigDecimal result = calculator.calculate(new Tick(BigDecimal.valueOf(30.0), now.plusSeconds(2)));
        assertEqualsBigDecimal(BigDecimal.valueOf(30.00), result);
    }

    @Test
    void testCalculateWithMultipleTicksAndCancel() {
        Instant now = Instant.now();
        calculator.calculate(new Tick(BigDecimal.valueOf(10.0), now));
        calculator.calculate(new Tick(BigDecimal.valueOf(20.0), now.plusSeconds(1)));
        calculator.cancel();
        assertEqualsBigDecimal(BigDecimal.ZERO, calculator.calculate(new Tick(BigDecimal.valueOf(30.0), now.plusSeconds(2))));
        assertEqualsBigDecimal(BigDecimal.ZERO, calculator.calculate(new Tick(BigDecimal.valueOf(40.0), now.plusSeconds(2))));
        calculator.reset();
        assertEqualsBigDecimal(BigDecimal.ZERO, calculator.calculate(new Tick(BigDecimal.valueOf(50.0), now.plusSeconds(2))));
        calculator.resume();
        assertEqualsBigDecimal(BigDecimal.valueOf(30.00), calculator.calculate(new Tick(BigDecimal.valueOf(30.0), now.plusSeconds(2))));
    }

}