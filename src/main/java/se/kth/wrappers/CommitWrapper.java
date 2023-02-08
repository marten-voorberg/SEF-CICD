package se.kth.wrappers;

public interface CommitWrapper {
    String getCommitSHA();
    String getCommitMessage();
    String getCommitDateTimeString();
    String getCommitAuthor();
    String getCommitAuthorEmail();
}
