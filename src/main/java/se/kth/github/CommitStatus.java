package se.kth.github;

import com.google.gson.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CommitStatus implements GithubApiClient {

    private final String owner;
    private final String repo;
    private final String sha;
    private final String url;
    private final String token;


    /**
     * Constructor for a commit status
     *
     * @param owner The owner of the repo
     * @param repo The name of the repo
     * @param sha The commit id
     */
    public CommitStatus(String owner, String repo, String sha) {
        this.owner = owner;
        this.repo = repo;
        this.sha = sha;
        this.url = "https://api.github.com/repos/" + owner + "/" + repo + "/statuses/" + sha;

        try{
            this.token = Files.readString(Path.of("secrets/github_token"));
        } catch (IOException e) {
            System.err.println("No token file was found! Make sure you have a /secrets/github_token file");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createOrUpdateCommitStatus(String commitSHA, StatusState state, String targetUrl, String description, String context) {

    }
    /**
     * Creates a POST request with the Github Access Token as header.
     *
     * @param state the status one wants to update or create for the commit
     */
    @Override
    public CloseableHttpResponse postStatus(StatusState state) throws IOException {

        JsonObject jo = new JsonObject(); // create json object

        switch (state) { //add the appropriate state as a property
            case SUCCESS -> jo.addProperty("state", "success");

            case ERROR -> jo.addProperty("state", "error");

            case PENDING -> jo.addProperty("state", "pending");

            case FAILURE -> jo.addProperty("state", "failure");
        }

        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) { //create http client and add headers
            HttpPost hp = new HttpPost(url);

            hp.addHeader("Accept", "application/vnd.github+json");
            hp.addHeader("Authorization", "Bearer " + token);
            hp.addHeader("X-GitHub-Api-Version","2022-11-28");

            StringEntity se = new StringEntity(jo.toString());

            hp.setEntity(se);
            CloseableHttpResponse res = httpClient.execute(hp);

            return res;
        }
    }


}
