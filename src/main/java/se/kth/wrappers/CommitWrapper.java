package se.kth.wrappers;

import java.util.Map;

public class CommitWrapper {
    private final Map<String, Object> commitMap;

    public CommitWrapper(Map<String, Object> commitMap) {
        this.commitMap = commitMap;
    }

    public String getCommitSHA() {
        return null;
    }

    public String getCommitMessage() {
        return null;
    }
}
