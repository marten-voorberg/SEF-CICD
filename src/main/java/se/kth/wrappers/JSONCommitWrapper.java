package se.kth.wrappers;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;


    /**
    * Commit Wrapper for use in conjuction with JSONPushWrapper
    * @param commitMap a map of the commit object from the GitHub API
    */
public class JSONCommitWrapper implements CommitWrapper {
    private final Map<String, Object> commitMap;
    private final static DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm").withZone(ZoneId.systemDefault());
  
    public JSONCommitWrapper(Map<String, Object> commitMap) {
        this.commitMap = commitMap;
    }

    @Override
    public String getCommitSHA() {
        return (String) commitMap.get("id");
    }

    @Override
    public String getCommitMessage() {
        return (String) commitMap.get("message");
    }


    @Override
    public String getCommitAuthor() {
        return (String) ((Map<String, Object>) commitMap.get("author")).get("name");
    }


    @Override
    public String getCommitAuthorEmail() {
        return (String) ((Map<String, Object>) commitMap.get("author")).get("email");
    }

    public String getCommitDateTimeString() {
        Instant dateTime = Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse((String) commitMap.get("timestamp")));
        return DATE_TIME_FORMATTER.format(dateTime);
    }
}
