package se.kth.github;

import com.google.gson.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class CommitStatus implements GithubApiClient {

    private String owner;
    private String repo;
    private String sha;
    private String url;


    public CommitStatus(String owner, String repo, String sha, StatusState state) {
        this.owner = owner;
        this.repo = repo;
        this.sha = sha;
        this.url = "https://api.github.com/repos/" + owner + "/" + repo + "/statuses/" + sha;
    }

    @Override
    public void createOrUpdateCommitStatus(String commitSHA, StatusState state, String targetUrl, String description, String context) {

    }

    @Override
    public CloseableHttpResponse postStatus(StatusState state) throws IOException {

        JsonObject jo = new JsonObject(); // create json object

        switch (state) {
            case SUCCESS -> jo.addProperty("state", "success");

            case ERROR -> jo.addProperty("state", "error");

            case PENDING -> jo.addProperty("state", "pending");

            case FAILURE -> jo.addProperty("state", "failure");
        }

        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost hp = new HttpPost(url);

            hp.addHeader("Accept", "application/vnd.github+json");
            hp.addHeader("Authorization", "token " + "mockmock");

            StringEntity se = new StringEntity(jo.toString());

            hp.setEntity(se);
            CloseableHttpResponse res = httpClient.execute(hp);

            return res;
        }
    }


}
