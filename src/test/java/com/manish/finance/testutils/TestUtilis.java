package com.manish.finance.testutils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtilis {

    private static final double EPSILON = 0.00001;

    // An equality utility for BigDecimals that are almost equal
    public static boolean almostEquals(BigDecimal n1, BigDecimal n2) {
        return n1.subtract(n2).abs().compareTo(BigDecimal.valueOf(EPSILON)) < 1;
    }

    // JUnit doesn't have a built-in assertEquals for BigDecimals, so we define our own
    public static void assertEqualsBigDecimal(BigDecimal n1, BigDecimal n2){
        assertTrue(almostEquals(n1, n2),
                "Expected: " + n1 + ", but got: " + n2);
    }
}
