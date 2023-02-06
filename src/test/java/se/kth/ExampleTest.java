package se.kth;

import org.junit.jupiter.api.Test;
import se.kth.email.EmailClient;
import se.kth.github.StatusState;

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

    // @Test
    public void testSendEmail(){
        var apiClient = new EmailClient();
        apiClient.sendResultEmail("whale0827@icloud.com", StatusState.SUCCESS, "Commit check passed");
        assertTrue(true);
    }
}
