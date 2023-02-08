//package se.kth;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.jupiter.api.Test;
//import se.kth.wrappers.JSONCommitWrapper;
//
//class JSONCommitWrapperTest {
//
//    //Test the getting sha method
//    @Test
//    void testGetCommitSHA() {
//        Map<String, Object> commitMap = new HashMap<>();
//        commitMap.put("sha", "abc123");
//        JSONCommitWrapper wrapper = new JSONCommitWrapper(commitMap);
//        assertEquals("abc123", wrapper.getCommitSHA());
//    }
//
//    //Test whether we can get the commit message method
//    @Test
//    void testGetCommitMessage() {
//        Map<String, Object> commitMap = new HashMap<>();
//        Map<String, Object> commit = new HashMap<>();
//        commit.put("message", "This is a commit message.");
//        commitMap.put("commit", commit);
//        JSONCommitWrapper wrapper = new JSONCommitWrapper(commitMap);
//        assertEquals("This is a commit message.", wrapper.getCommitMessage());
//    }
//
//    //test the extracting author method
//    @Test
//    void testGetCommitAuthor() {
//        Map<String, Object> commitMap = new HashMap<>();
//        Map<String, Object> commit = new HashMap<>();
//        commit.put("author", "Karlis");
//        commitMap.put("commit", commit);
//        JSONCommitWrapper wrapper = new JSONCommitWrapper(commitMap);
//        assertEquals("Karlis", wrapper.getCommitAuthor());
//    }
//
//    //test the author-email method
//    @Test
//    void testGetCommitAuthorEmail() {
//        Map<String, Object> commitMap = new HashMap<>();
//        Map<String, Object> commit = new HashMap<>();
//        commit.put("author_email", "karlis@kth.se");
//        commitMap.put("commit", commit);
//        JSONCommitWrapper wrapper = new JSONCommitWrapper(commitMap);
//        assertEquals("karlis@kth.se", wrapper.getCommitAuthorEmail());
//    }
//}
