package se.kth.pipelines;

import se.kth.github.GithubApiClient;
import se.kth.github.StatusState;
import se.kth.wrappers.CommitWrapper;

public class CommitMessageChecker extends PipelineHandler {
    public static final String CONTEXT = "CommitMessageChecker";
    public CommitMessageChecker(GithubApiClient apiClient) {
        super(apiClient);
    }

    @Override
    public void handleCommit(CommitWrapper commitWrapper) {
        apiClient.createOrUpdateCommitStatus(
                commitWrapper.getCommitSHA(),
                StatusState.SUCCESS,
                null,
                "Commit message looks good!",
                CONTEXT
        );
    }
}
