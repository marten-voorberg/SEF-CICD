package se.kth.github;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

/**
 * An interface of classes that support notification of CI results. The most important is {@link HttpAPIClient} which sends
 * actual requests to the <a href="https://docs.github.com/en/rest/commits/statuses?apiVersion=2022-11-28">GitHub status api</a>.
 * The notification of results happens through {@link GithubApiClient#createOrUpdateCommitStatus(String, StatusState, String, String, String)}
 */
public interface GithubApiClient {
    /**
     * Update the commit status on GitHub.
     * @param commitSHA The ID of the commit to be updated.
     * @param state The state of the commit. For more: {@link StatusState}
     * @param targetUrl The url to which status will link. Can be null.
     * @param description The description of the commit status.
     * @param context The context of the status. Sending statuses to the same commit twice with the same context allows
     *                you to override your previous status.
     */
    void createOrUpdateCommitStatus(String commitSHA, StatusState state, String targetUrl, String description, String context);
}
