package se.kth.wrappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JSONPushWrapper implements PushWrapper {
    private final Map<String, Object> pushMap;
    private final List<CommitWrapper> commitWrappers;

    /**
     * @param request the request from the GitHub API
     * @throws IOException if the request cannot be read
     */
    public JSONPushWrapper(HttpServletRequest request) throws IOException {

        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper mapper = new ObjectMapper();
        this.pushMap = mapper.readValue(body, Map.class);
        this.commitWrappers = new ArrayList<>();
        List<Map<String, Object>> commits = (List<Map<String, Object>>) pushMap.get("commits");
        for (Map<String, Object> commit : commits) {
            commitWrappers.add(new JSONCommitWrapper(commit));
        }

    }

    /**
     * @return the list of commit wrappers
     */
    @Override
    public List<CommitWrapper> getCommitWrappers() {
        return this.commitWrappers;
    }

    /**
     * @return the map of the push object from the GitHub API
     */
    public Map<String, Object> getPushMap() {
        return this.pushMap;
    }

}
