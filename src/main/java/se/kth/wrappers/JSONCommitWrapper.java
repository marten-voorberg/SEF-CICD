package se.kth.wrappers;

import java.util.Map;

public class JSONCommitWrapper implements CommitWrapper {
    private final Map<String, Object> commitMap;

    public JSONCommitWrapper(Map<String, Object> commitMap) {
        this.commitMap = commitMap;
    }

    public String getCommitSHA() {
        return null;
    }

    public String getCommitMessage() {
        return null;
    }
    public String getCommitDate() {
        return null;
    }
    public String getCommitAuthor() {
        return null;
    }
}
