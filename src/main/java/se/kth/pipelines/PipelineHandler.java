package se.kth.pipelines;

import se.kth.github.GithubApiClient;
import se.kth.wrappers.CommitWrapper;

public abstract class PipelineHandler {
    protected GithubApiClient apiClient;

    public PipelineHandler(GithubApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public abstract void handleCommit(CommitWrapper commitWrapper);
}
