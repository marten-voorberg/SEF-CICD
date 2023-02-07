package se.kth.pipelines;

import se.kth.github.GithubApiClient;
import se.kth.github.StatusState;
import se.kth.wrappers.CommitWrapper;

/**
 * Pipeline that checks whether commit messages adhere to the conventions.
 */
public class CommitMessageChecker extends PipelineHandler {
    public static final String CONTEXT = "CommitMessageChecker";

    /**
     * @see PipelineHandler#PipelineHandler(GithubApiClient)
     */
    public CommitMessageChecker(GithubApiClient apiClient) {
        super(apiClient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommit(CommitWrapper commitWrapper) {
        apiClient.createOrUpdateCommitStatus(
                commitWrapper.getCommitSHA(),
                StatusState.SUCCESS,
                null,
                "Commit message looks good!",
                CONTEXT
        );

        // Todo: Implement
    }
}
