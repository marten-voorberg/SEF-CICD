package se.kth.github;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

public interface GithubApiClient {
    void createOrUpdateCommitStatus(String commitSHA, StatusState state, String targetUrl, String description, String context);
}
