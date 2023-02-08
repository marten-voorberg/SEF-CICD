package se.kth.github;

import com.google.gson.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpAPIClient implements GithubApiClient {
    private final String owner;
    private final String repo;
    private final String url;
    private final String token;


    /**
     * Constructor for a commit status
     *
     * @param owner The owner of the repo
     * @param repo  The name of the repo
     */
    public HttpAPIClient(String owner, String repo) {
        this.owner = owner;
        this.repo = repo;
        this.url = "https://api.github.com/repos/%s/%s/statuses/".formatted(owner, repo);

        try {
            this.token = Files.readString(Path.of("secrets/github_token"));
        } catch (IOException e) {
            System.err.println("No token file was found! Make sure you have a /secrets/github_token file");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createOrUpdateCommitStatus(String commitSHA, StatusState state, String targetUrl, String description, String context) {
        JsonObject jo = new JsonObject(); // create json object

        jo.addProperty("state", state.getApiRepresentation());
        jo.addProperty("target_url", targetUrl);
        jo.addProperty("description", description);
        jo.addProperty("context", context);

        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) { //create http client and add headers
            HttpPost hp = new HttpPost(url + commitSHA);

            hp.addHeader("Accept", "application/vnd.github+json");
            hp.addHeader("Authorization", "Bearer %s".formatted(token));
            hp.addHeader("X-GitHub-Api-Version", "2022-11-28");

            StringEntity se = new StringEntity(jo.toString());

            hp.setEntity(se);
            httpClient.execute(hp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        var client = new HttpAPIClient("marten-voorberg", "SEF-CICD");
        client.createOrUpdateCommitStatus("948fa5bd433caed2f2ff81a3975aeaadc97678ae", StatusState.SUCCESS, null, "Set through java", "TEMP");
    }
}
