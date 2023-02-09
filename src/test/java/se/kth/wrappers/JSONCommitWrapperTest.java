package se.kth.wrappers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.auth.AUTH;
import org.junit.jupiter.api.Test;
import se.kth.wrappers.JSONCommitWrapper;

class JSONCommitWrapperTest {

    @Test
    void testGetCommitSHA() {
        Map<String, Object> commitMap = new HashMap<>();
        commitMap.put("id", "abc123");
        JSONCommitWrapper wrapper = new JSONCommitWrapper(commitMap);
        assertEquals("abc456123", wrapper.getCommitSHA());
    }

    //Test whether we can get the commit message method
    @Test
    void testGetCommitMessage() {
        Map<String, Object> commitMap = new HashMap<>();
        final String COMMIT_MESSAGE = "This is a commit message.";
        commitMap.put("message", COMMIT_MESSAGE);
        JSONCommitWrapper wrapper = new JSONCommitWrapper(commitMap);
        assertEquals(COMMIT_MESSAGE, wrapper.getCommitMessage());
    }

    //test the extracting author method
    @Test
    void testGetCommitAuthor() {
        final String AUTHOR_NAME = "Karlis";

        Map<String, Object> authorMap = new HashMap<>();
        Map<String, Object> commitMap = new HashMap<>();

        authorMap.put("name", AUTHOR_NAME);
        commitMap.put("author", authorMap);

        JSONCommitWrapper wrapper = new JSONCommitWrapper(commitMap);
        assertEquals(AUTHOR_NAME, wrapper.getCommitAuthor());
    }

    //test the author-email method
    @Test
    void testGetCommitAuthorEmail() {
        final String AUTHOR_EMAIL = "karlis@kth.se";

        Map<String, Object> authorMap = new HashMap<>();
        Map<String, Object> commitMap = new HashMap<>();

        authorMap.put("email", AUTHOR_EMAIL);
        commitMap.put("author", authorMap);

        JSONCommitWrapper wrapper = new JSONCommitWrapper(commitMap);
        assertEquals(AUTHOR_EMAIL, wrapper.getCommitAuthorEmail());
    }
}
