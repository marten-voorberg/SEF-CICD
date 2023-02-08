package se.kth.wrappers;

import java.util.List;
import java.util.Map;

public class DummyPushWrapper implements PushWrapper {
    private final List<CommitWrapper> commitWrappers;

    public DummyPushWrapper(List<CommitWrapper> commitWrappers) {
        this.commitWrappers = commitWrappers;
    }


    @Override
    public List<CommitWrapper> getCommitWrappers() {
        return commitWrappers;
    }

}
