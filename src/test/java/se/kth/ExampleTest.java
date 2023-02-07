package se.kth;

import org.junit.jupiter.api.Test;
import se.kth.github.CommitStatus;
import se.kth.github.StatusState;

import java.io.IOException;
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

    // should be a temporary test function
    // @Test
    public void testGithubClient() throws IOException {
        var commitStatus = new CommitStatus("marten-voorberg", "SEF-CICD", "ff389bebac839116391c797f097e7d79e2c1837f", StatusState.ERROR);
        var res = commitStatus.postStatus(StatusState.ERROR);
        assertNotNull(res);
        assertTrue(true);
    }
}
