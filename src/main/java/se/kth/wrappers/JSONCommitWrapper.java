package se.kth.wrappers;

import java.util.Map;


public class JSONCommitWrapper implements CommitWrapper {
    private final Map<String, Object> commitMap;

    /**
     * @param commitMap a map of the commit object from the GitHub API
     */
    public JSONCommitWrapper(Map<String, Object> commitMap) {
        this.commitMap = commitMap;
    }

    /**
     * @return the SHA of the commit
     */
    @Override
    public String getCommitSHA() {
        return (String) commitMap.get("id");
    }

    /**
     * @return the message of the commit
     */
    @Override
    public String getCommitMessage() {
        return (String) commitMap.get("message");
    }

    /**
     * @return the author of the commit
     */
    @Override
    public String getCommitAuthor() {
        return (String) ((Map<String, Object>) commitMap.get("author")).get("name");
    }

    /**
     * @return the author email of the commit
     */
    @Override
    public String getCommitAuthorEmail() {
        return (String) ((Map<String, Object>) commitMap.get("author")).get("email");
    }
}
