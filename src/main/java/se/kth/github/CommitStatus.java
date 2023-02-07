package se.kth.github;

import com.google.gson.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileReader;
import java.io.IOException;

import java.util.Properties;


public class CommitStatus implements GithubApiClient {

    private final String owner;
    private final String repo;
    private final String sha;
    private final String url;
    private String token;

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

        try (FileReader reader = new FileReader("src\\main\\java\\se\\kth\\secrets\\github-token")) {
            Properties properties = new Properties();
            properties.load(reader);
            token = properties.getProperty("token");
        } catch (Exception e) {
            e.printStackTrace();
        }

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

            StringEntity se = new StringEntity(jo.toString());

            hp.setEntity(se);
            CloseableHttpResponse res = httpClient.execute(hp);

            return res;
        }
    }


}
