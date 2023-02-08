package se.kth.pipelines;

import se.kth.github.DummyAPIClient;
import se.kth.github.GithubApiClient;
import se.kth.github.StatusState;
import se.kth.wrappers.CommitWrapper;
import se.kth.wrappers.DummyCommitWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * Pipeline that builds and tests the state of the repository after a specific commit through gradle.
 * The results are send are set as a GitHub status and the build and test logs are stored locally as a file.
 */
public class TestChecker extends PipelineHandler {
    private final static String CONTEXT_STRING = "GROUP4_TEST_CHECKER";

    /**
     * @see PipelineHandler#PipelineHandler(GithubApiClient)
     */
    public TestChecker(GithubApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Build and test the repository after a specific commit through gradle.
     * <br>
     * This check happens in the following steps:
     * <ol>
     *     <li>Clone the repository</li>
     *     <li>Checkout the commit using the commit id or sha (See: {@link CommitWrapper#getCommitSHA()})</li>
     *     <li>Perform <code>gradle test</code></li>. See the
     *     <a href="https://docs.gradle.org/current/userguide/java_testing.html">Gradle documentation</a> for details.
     *     <li>Clean up the cloned repository</li>
     * </ol>
     * The results of the pipeline are sent to GitHub through a {@link GithubApiClient} and the gradle build and test log
     * are stored in <code>history/tests/commit-'commit-id'</code>.
     * @param commitWrapper A wrapper containing all relevant information about the commit to be put through the pipeline.
     */
    @Override
    public void handleCommit(CommitWrapper commitWrapper) {
        final String commitSHA = commitWrapper.getCommitSHA();

        this.apiClient.createOrUpdateCommitStatus(
                commitSHA,
                StatusState.PENDING,
                null,
                "Gradle test pipeline has been started...",
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
            // At the end of the pipeline, we want to clean up the cloned folder. This should happen even if any
            // of the steps above have thrown an Exception. This is why clean up in a 'finally' clause.
            try {
                p = runtime.exec(getCleanupCommand());
                p.waitFor();
            } catch (IOException | InterruptedException e) {
                // If we could not clean up the folder, we report the error.
                e.printStackTrace();
                System.err.println("Could not clean up the cloned repository in TestChecker. " +
                        "There might be a lingering folder!");
            }
        }

    }

    private void writeToFile(String commitSHA, String gradleOutput) {
        try {
            Files.createDirectories(Path.of("history/tests"));
//            Path historyPath = Path.of("history");
//            if (!Files.exists(historyPath)) {
//                Files.createDirectory(historyPath);
//            }
//
//            Path testsPath = Path.of("history/tests");
//            if (!Files.exists(testsPath)) {
//                Files.createDirectory(testsPath);
//            }

            Path path = Path.of(String.format("history/tests/commit-%s", commitSHA));
            Files.deleteIfExists(path);
            Files.createFile(path);
            Files.writeString(path, gradleOutput);
        } catch (IOException e) {
            System.err.println("Could not create the build log history file!");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        TestChecker testChecker = new TestChecker(new DummyAPIClient());
        CommitWrapper commitWrapper = new DummyCommitWrapper("6c369c1af19c54af4485d475debefc1bd69da740", "whatever", "", "");

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

    private static String[] toCommandArray(String command) {
        return new String[]{"bash", "-c", command};
    }
}
