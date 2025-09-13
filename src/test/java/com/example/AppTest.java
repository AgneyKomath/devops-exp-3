package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void testIsPrime() {
        App app = new App();
        assertTrue(app.isPrime(5));
        assertFalse(app.isPrime(4));
    }
}