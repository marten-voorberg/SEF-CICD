package se.kth.github;

public interface GithubApiClient {
    void createOrUpdateCommitStatus(String commitSHA, StatusState state, String targetUrl, String description, String context);
}
