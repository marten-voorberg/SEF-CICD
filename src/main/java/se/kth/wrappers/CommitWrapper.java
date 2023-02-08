package se.kth.wrappers;

/**
 *
 */
public interface CommitWrapper {
    /**
     *
     * @return
     */
    String getCommitSHA();

    /**
     *
     * @return
     */
    String getCommitMessage();

    /**
     *
     * @return
     */
    String getCommitDateTimeString();

    /**
     *
     * @return
     */
    String getCommitAuthor();

    /**
     *
     * @return
     */
    String getCommitAuthorEmail();
}
