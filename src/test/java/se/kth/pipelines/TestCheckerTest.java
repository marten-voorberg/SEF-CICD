//package se.kth.pipelines;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import se.kth.github.GithubApiClient;
//import se.kth.github.StatusState;
//import se.kth.utils.StoringApiClient;
//import se.kth.wrappers.CommitWrapper;
//import se.kth.wrappers.DummyCommitWrapper;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TestCheckerTest {
//    private final CommitWrapper COMMIT_WITH_PASSING_TESTS = new DummyCommitWrapper("948fa5bd433caed2f2ff81a3975aeaadc97678ae", "", "", "");
//    private final CommitWrapper COMMIT_WITH_FAILING_TESTS = new DummyCommitWrapper("5a37324c4056c6a07ed68b2824c7c0219e5bd0bd", "", "", "");
//
//    private StoringApiClient storingApiClient;
//    private TestChecker testChecker;
//
//    @BeforeEach
//    public void setUp() {
//        this.storingApiClient = new StoringApiClient();
//        this.testChecker = new TestChecker(storingApiClient);
//    }
//
//    @Test
//    public void testCommitWithPassingTestsSendsSuccessOverAPI() {
//        this.testChecker.handleCommit(COMMIT_WITH_PASSING_TESTS);
//
//        // There should be two api calls
//        // One pending and one success in that order
//        assertEquals(2, storingApiClient.getStoredCalls().size());
//
//        assertEquals(StatusState.PENDING, storingApiClient.getStoredCalls().get(0).state());
//        assertEquals(StatusState.SUCCESS, storingApiClient.getStoredCalls().get(1).state());
//    }
//
//    @Test
//    public void testCommitWithFailingTestsSendsFailureOverAPI() {
//        this.testChecker.handleCommit(COMMIT_WITH_FAILING_TESTS);
//
//        // There should be two api calls
//        // One pending and one failure in that order
//        assertEquals(2, storingApiClient.getStoredCalls().size());
//
//        assertEquals(StatusState.PENDING, storingApiClient.getStoredCalls().get(0).state());
//        assertEquals(StatusState.FAILURE, storingApiClient.getStoredCalls().get(1).state());
//    }
//}