package se.kth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import se.kth.github.DummyAPIClient;
import se.kth.github.GithubApiClient;
import se.kth.pipelines.CommitMessageChecker;
import se.kth.pipelines.TestChecker;
import se.kth.wrappers.JSONPushWrapper;
import se.kth.wrappers.PushWrapper;


public class ContinuousIntegrationServer extends AbstractHandler {
    private final GithubApiClient githubApiClient;
    private final CommitMessageChecker commitMessageChecker;
    private final TestChecker testChecker;

    public ContinuousIntegrationServer() {
        githubApiClient = new DummyAPIClient();

        commitMessageChecker = new CommitMessageChecker(this.githubApiClient);
        this.testChecker = new TestChecker(this.githubApiClient);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Starting server!");

        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) {
        System.out.printf("Received request to target '%s'\n", target);

        try {
            PushWrapper pushWrapper = new JSONPushWrapper(request);

            if (target.contains("commit-message")) {
                pushWrapper.getCommitWrappers().forEach(commitMessageChecker::handleCommit);
            } else if (target.contains("test")) {
                pushWrapper.getCommitWrappers().forEach(testChecker::handleCommit);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                System.out.printf("Received invalid target '%s'\n", target);
                return;
            }

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("CI job done");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}