package se.kth.github;

/**
 * Status of a commit because of a CI check.
 * These statuses here correspond to the statuses send by the <a href="https://docs.github.com/en/rest/commits/statuses?apiVersion=2022-11-28">GitHub status api</a>.
 * Each enum value has an associated string which returns the representation of this status expected by the API. This
 * field is accessed by calling {@link StatusState#getApiRepresentation()}
 */
public enum StatusState {
    ERROR("error"), FAILURE("failure"), PENDING("pending"), SUCCESS("success");

    private final String apiRepresentation;

    StatusState(String apiRepresentation) {
        this.apiRepresentation = apiRepresentation;
    }

    public String getApiRepresentation() {
        return apiRepresentation;
    }
}
