package se.kth.wrappers;

import java.util.List;

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
