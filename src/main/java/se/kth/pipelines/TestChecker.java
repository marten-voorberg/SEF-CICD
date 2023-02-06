package se.kth.pipelines;

import se.kth.github.DummyAPIClient;
import se.kth.github.GithubApiClient;
import se.kth.github.StatusState;
import se.kth.wrappers.CommitWrapper;
import se.kth.wrappers.DummyCommitWrapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class TestChecker extends PipelineHandler {
    private final static String CONTEXT_STRING = "GROUP4_TEST_CHECKER";

    public TestChecker(GithubApiClient apiClient) {
        super(apiClient);
    }

    @Override
    public void handleCommit(CommitWrapper commitWrapper) {
        final String commitSHA = commitWrapper.getCommitSHA();

        this.apiClient.createOrUpdateCommitStatus(
                commitSHA,
                StatusState.PENDING,
                null,
                "Gradle test pipelane has been started...",
                CONTEXT_STRING
        );

        final Runtime runtime = Runtime.getRuntime();
        Process p;
        int exitValue;

        try {
            // Clone the repository
            p = runtime.exec(getCloneCommand());
            exitValue = p.waitFor();
            if (exitValue != 0) {
                this.apiClient.createOrUpdateCommitStatus(
                        commitSHA,
                        StatusState.ERROR,
                        null,
                        "Repository could not be cloned!",
                        CONTEXT_STRING
                );
                return;
            }

            // Checkout the right commit
            p = runtime.exec(getCheckoutCommitCommand(commitSHA));
            exitValue = p.waitFor();
            if (exitValue != 0) {
                this.apiClient.createOrUpdateCommitStatus(
                        commitSHA,
                        StatusState.ERROR,
                        null,
                        String.format("Commit '%s' could not be checked out!", commitSHA),
                        CONTEXT_STRING
                );
                return;
            }

            p = runtime.exec(getGradleCommand());
            String gradleOutput = new BufferedReader(new InputStreamReader(p.getInputStream())).lines().collect(Collectors.joining("\n"));
            this.writeToFile(commitSHA, gradleOutput);
            exitValue = p.waitFor();
            if (exitValue == 0) {
                this.apiClient.createOrUpdateCommitStatus(
                        commitSHA,
                        StatusState.SUCCESS,
                        null,
                        "Gradle test succeeded!",
                        CONTEXT_STRING
                );
            } else {
                this.apiClient.createOrUpdateCommitStatus(
                        commitSHA,
                        StatusState.FAILURE,
                        null,
                        "Gradle test failed!",
                        CONTEXT_STRING
                );
            }

        } catch (IOException | InterruptedException e) {
            this.apiClient.createOrUpdateCommitStatus(
                    commitSHA,
                    StatusState.ERROR,
                    null,
                    "An internal error was encountered whilst running the pipeline!",
                    CONTEXT_STRING
            );
            e.printStackTrace();
        } finally {
            try {
                p = runtime.exec(getCleanupCommand());
                p.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                System.err.println("Could not clean up the cloned repository in TestChecker. " +
                        "There might be a lingering folder!");
            }
        }

    }

    private void writeToFile(String commitSHA, String gradleOutput) {
        try {
            Path path = Path.of(String.format("history/tests/commit-%s", commitSHA));
            Files.deleteIfExists(path);
            Files.createFile(path);
            Files.writeString(path, gradleOutput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        TestChecker testChecker = new TestChecker(new DummyAPIClient());
        CommitWrapper commitWrapper = new DummyCommitWrapper("6c369c1af19c54af4485d475debefc1bd69da740", "whatever");

        testChecker.handleCommit(commitWrapper);
    }

    private static String[] getCloneCommand() {
        return toCommandArray("git clone git@github.com:marten-voorberg/SEF-CICD.git");
    }

    private static String[] getCheckoutCommitCommand(String commitID) {
        return toCommandArray(String.format("cd SEF-CICD; git checkout %s", commitID));
    }

    private static String[] getGradleCommand() {
        // Todo: make the gradle path non-fixed
        return toCommandArray("cd SEF-CICD; /opt/gradle/gradle-7.6/bin/gradle -i test");
    }

    private static String[] getCleanupCommand() {
        return toCommandArray("rm -rf SEF-CICD");
    }

    private static String[] getCDCommand(String dir) {
        return toCommandArray(String.format("cd %s", dir));
    }

    private static String[] toCommandArray(String command) {
        return new String[]{"bash", "-c", command};
    }
}
