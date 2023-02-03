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

    public JSONPushWrapper(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String jsonString = reader.lines().collect(Collectors.joining());
        ObjectMapper mapper = new ObjectMapper();
        this.pushMap = mapper.readValue(jsonString, Map.class);

        this.commitWrappers = new ArrayList<>();
        ArrayList commits = (ArrayList) pushMap.get("commits");
        for (int i = 0; i < commits.size(); i++) {
            commitWrappers.add(new JSONCommitWrapper((Map<String, Object>) commits.get(i)));
        }

    }

    public List<CommitWrapper> getCommitWrappers() {
        return this.commitWrappers;
    }
}
