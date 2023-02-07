package se.kth;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import se.kth.wrappers.JSONCommitWrapper;

class JSONCommitWrapperTest {

    @Test
    void testGetCommitSHA() {
        Map<String, Object> commitMap = new HashMap<>();
        commitMap.put("sha", "abc123");
        JSONCommitWrapper wrapper = new JSONCommitWrapper(commitMap);
        assertEquals("abc123", wrapper.getCommitSHA());
    }

    @Test
    void testGetCommitMessage() {
        Map<String, Object> commitMap = new HashMap<>();
        Map<String, Object> commit = new HashMap<>();
        commit.put("message", "This is a commit message.");
        commitMap.put("commit", commit);
        JSONCommitWrapper wrapper = new JSONCommitWrapper(commitMap);
        assertEquals("This is a commit message.", wrapper.getCommitMessage());
    }

    @Test
    void testGetCommitAuthor() {
        Map<String, Object> commitMap = new HashMap<>();
        Map<String, Object> commit = new HashMap<>();
        commit.put("author", "John Doe");
        commitMap.put("commit", commit);
        JSONCommitWrapper wrapper = new JSONCommitWrapper(commitMap);
        assertEquals("John Doe", wrapper.getCommitAuthor());
    }

    @Test
    void testGetCommitAuthorEmail() {
        Map<String, Object> commitMap = new HashMap<>();
        Map<String, Object> commit = new HashMap<>();
        commit.put("author_email", "johndoe@example.com");
        commitMap.put("commit", commit);
        JSONCommitWrapper wrapper = new JSONCommitWrapper(commitMap);
        assertEquals("johndoe@example.com", wrapper.getCommitAuthorEmail());
    }
}
