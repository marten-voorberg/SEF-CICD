package se.kth.wrappers;

public interface CommitWrapper {
    String getCommitSHA();
    String getCommitMessage();
    String getCommitAuthor();
    String getCommitAuthorEmail();
}
