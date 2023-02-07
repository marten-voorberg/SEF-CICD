package se.kth.wrappers;

public class DummyCommitWrapper implements CommitWrapper {
    private final String commitSHA;
    private final String commitMessage;

    public DummyCommitWrapper(String commitSHA, String commitMessage) {
        this.commitSHA = commitSHA;
        this.commitMessage = commitMessage;
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
    public String getCommitDate(){
        return "2302011200";
    }

    @Override
    public String getCommitAuthor(){
        return "Nils Löfberg";
    }
}
