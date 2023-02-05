package se.kth.wrappers;

import java.util.List;
import java.util.Map;

public interface PushWrapper {
    List<CommitWrapper> getCommitWrappers();
    Map<String, Object> getPushMap();
}
