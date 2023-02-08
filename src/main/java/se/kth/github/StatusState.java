package se.kth.github;

public enum StatusState {
    ERROR("error"), FAILURE("failure"), PENDING("pending"), SUCCESS("success");

    private String apiRepresentation;

    StatusState(String apiRepresentation) {
        this.apiRepresentation = apiRepresentation;
    }

    public String getApiRepresentation() {
        return apiRepresentation;
    }
}
