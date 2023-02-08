package se.kth.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import se.kth.github.GithubApiClient;
import se.kth.github.StatusState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This API client stores all the calls to {@link StoringApiClient#createOrUpdateCommitStatus}
 */
public class StoringApiClient implements GithubApiClient {
    private final List<APICall> storedCalls;

    public StoringApiClient() {
        this.storedCalls = new ArrayList<>();
    }

    @Override
    public void createOrUpdateCommitStatus(String commitSHA, StatusState state, String targetUrl, String description, String context) {
        this.storedCalls.add(new APICall(commitSHA, state, targetUrl, description, context));
    }

    public List<APICall> getStoredCalls() {
        return this.storedCalls;
    }

    public record APICall(String commitSHA, StatusState state, String targetUrl, String description, String context) {

    }
}
