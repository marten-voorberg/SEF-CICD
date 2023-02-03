package se.kth.pipelines;

import se.kth.github.GithubApiClient;
import se.kth.wrappers.CommitWrapper;

public class TestChecker extends PipelineHandler {
    public TestChecker(GithubApiClient apiClient) {
        super(apiClient);
    }

    @Override
    public void handleCommit(CommitWrapper commitWrapper) {
        throw new UnsupportedOperationException();
    }
}
