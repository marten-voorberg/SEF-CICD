package se.kth.pipelines;

import se.kth.github.GithubApiClient;
import se.kth.wrappers.CommitWrapper;

/**
 * Abstract class for executing some CI pipeline for a specific commit.
 * <br>
 * The exact actions taken by a <code>PipelineHandler</code> depend on the specifics of the handler. The most important
 * is by far the {@link TestChecker} which builds and tests the project for each commit through gradle.
 */
public abstract class PipelineHandler {
    protected GithubApiClient apiClient;

    /**
     * Create a new PipelineHandler.
     * @param apiClient The {@link GithubApiClient} to which the results of the CI pipeline will ought to be sent.
     *                  In a production/deployment scenario, this will be a concrete API client that sends actual API request.
     *                  For testing and debugging purposes you can pass in clients that do not actually perform calls such as
     *                  {@link se.kth.github.DummyAPIClient}
     */
    public PipelineHandler(GithubApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Perform the CI checks for a specific commit.
     * @param commitWrapper A wrapper containing all relevant information about the commit to be put through the pipleline.
     */
    public abstract void handleCommit(CommitWrapper commitWrapper);
}
