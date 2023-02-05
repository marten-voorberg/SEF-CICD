package se.kth.pipelines;

import se.kth.github.GithubApiClient;
import se.kth.wrappers.CommitWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestChecker extends PipelineHandler {
    public TestChecker(GithubApiClient apiClient) {
        super(apiClient);
    }

    @Override
    public void handleCommit(CommitWrapper commitWrapper) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        try {
            Process p;
            int exitValue;

//            p = runtime.exec(getCloneCommand());
//            exitValue = p.waitFor();
//            System.out.printf("git clone: %d\n", exitValue);
//
//            p = runtime.exec(getCDCommand("SEF-CICD"));
//            exitValue = p.waitFor();
//            System.out.printf("cd: %d\n", exitValue);

//            p = runtime.exec(toCommandArray("cd SEF-CICD; pwd"));
//            new BufferedReader(new InputStreamReader(p.getInputStream())).lines().forEach(System.out::println);
//            exitValue = p.waitFor();
//            System.out.printf("pwd exit value: %d\n", exitValue);

//            p = runtime.exec(getCheckoutCommitCommand("41c9300e7f15cdb56b04f926b1d6c1bcd7dae8d7"));
//            exitValue = p.waitFor();
//            System.out.printf("git checkout: %d\n", exitValue);

            p = runtime.exec(getGradleCommand());
            exitValue = p.waitFor();
            System.out.printf("gradle test: %d\n", exitValue);
            new BufferedReader(new InputStreamReader(p.getInputStream())).lines().forEach(System.out::print);

//            p = runtime.exec(getCDCommand(".."));
//            exitValue = p.waitFor();
//            System.out.printf("cd: %d\n", exitValue);
//
//            p = runtime.exec(getCleanupCommand());
//            exitValue = p.waitFor();
//            System.out.printf("rm: %d\n", exitValue);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static String[] getCloneCommand() {
        return toCommandArray("git clone git@github.com:marten-voorberg/SEF-CICD.git");
    }

    private static String[] getCheckoutCommitCommand(String commitID) {
        return toCommandArray(String.format("cd SEF-CICD; git checkout %s", commitID));
    }

    private static String[] getGradleCommand() {
        return toCommandArray("cd SEF-CICD; /opt/gradle/gradle-7.6/bin/gradle test");
    }

    private static String[] getCleanupCommand() {
        return toCommandArray("rm -rf SEF-CICD");
    }

    private static String[] getCDCommand(String dir){
        return toCommandArray(String.format("cd %s", dir));
    }

    private static String[] toCommandArray(String command) {
        return new String[]{"bash", "-c", command};
    }
}
