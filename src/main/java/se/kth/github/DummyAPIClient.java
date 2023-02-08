package se.kth.github;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

public class DummyAPIClient implements GithubApiClient {
    @Override
    public void createOrUpdateCommitStatus(String commitSHA, StatusState state, String targetUrl, String description, String context) {
        System.out.println("Received dummy request:");
        System.out.printf("\tsha: '%s'\n", commitSHA);
        System.out.printf("\tstate: '%s'\n", state);
        System.out.printf("\tdescription: '%s\n'", description);
    }
}
