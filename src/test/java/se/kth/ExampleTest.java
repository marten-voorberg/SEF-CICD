package se.kth;

import org.junit.jupiter.api.Test;
import se.kth.email.EmailClient;
import se.kth.github.StatusState;

import static org.junit.jupiter.api.Assertions.*;

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
