package se.kth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExampleTest {
    @Test
    public void testTrivialThing() {
        assertEquals(5, 3 + 2);
    }

    @Test
    public void testThatFails() {
        assertTrue(false);
    }
}
