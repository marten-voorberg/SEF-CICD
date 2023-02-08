package se.kth.wrappers;

public class DummyCommitWrapper implements CommitWrapper {
    private final String commitSHA;
    private final String commitMessage;
    private final String commitAuthor;
    private final String commitAuthorEmail;

    public DummyCommitWrapper(String commitSHA, String commitMessage, String commitAuthor, String commitAuthorEmail) {
        this.commitSHA = commitSHA;
        this.commitMessage = commitMessage;
        this.commitAuthor = commitAuthor;
        this.commitAuthorEmail = commitAuthorEmail;
    }

    @Override
    public String getCommitSHA() {
        return commitSHA;
    }

    @Override
    public String getCommitMessage() {
        return commitMessage;
    }

    @Override
    public String getCommitAuthor() {
        return commitAuthor;
    }

    @Override
    public String getCommitAuthorEmail() {
        return commitAuthorEmail;
    }

    @Override
    public String getCommitDateTimeString(){
        return "2302011200";
    }


}
