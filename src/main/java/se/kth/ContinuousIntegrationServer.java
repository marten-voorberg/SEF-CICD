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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
        System.out.println("================");
        System.out.println("Starting server!");
        System.out.println("================");

        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) {
        System.out.printf("Received request to target '%s'\n", target);

        try {

            if (target.contains("commit-message")) {
                PushWrapper pushWrapper = new JSONPushWrapper(request);
                pushWrapper.getCommitWrappers().forEach(commitMessageChecker::handleCommit);
                response.setContentType("text/plain;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("commit-message CI Job done");
            } else if (target.contains("test")) {
                PushWrapper pushWrapper = new JSONPushWrapper(request);
                pushWrapper.getCommitWrappers().forEach(testChecker::handleCommit);
                response.setContentType("text/plain;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("test CI Job done");
            } else if (target.contains("commits/")) {
                String[] URI = target.split("commits/");

                if (URI.length == 1){
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter().write(getTestSummaryFromFolder());
                    response.getWriter().flush();
                    response.setStatus(HttpServletResponse.SC_OK);
                }
                else {
                    String commitId = URI[1];
                
                    if (commitId.charAt(commitId.length() - 1) == '/') {
                        commitId = commitId.substring(0, commitId.length() - 1);
                    }
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter().write(getTestSummaryFromFile(commitId));
                    response.getWriter().flush();
                    response.setStatus(HttpServletResponse.SC_OK);
                }
               
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                System.out.printf("Received invalid target '%s'\n", target);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    private String getTestSummaryFromFile(String commitId) {
        try (Stream<String> linesStream = Files.lines(Path.of(String.format("history/tests/commit-%s", commitId)))) {
            return "<div style='font-family: monospace'>" + linesStream.collect(Collectors.joining("<br>")) + "</div>";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTestSummaryFromFolder() {
        try {
            Set<String> commits = Stream.of(new File("history/tests").listFiles())
            .filter(file -> !file.isDirectory())
            .map(File::getName)
            .collect(Collectors.toSet());

            String commitList = "<div style='font-family: monospace'>";

            for (String commit : commits){
                String commitId = commit.split("commit-")[1];
                
                commitList += "<a  target= '_self' href='http://localhost:8080/commits/" + commitId + "'>" + commit + "</a> <br>";
            }

            commitList += "</div>";

            return commitList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}