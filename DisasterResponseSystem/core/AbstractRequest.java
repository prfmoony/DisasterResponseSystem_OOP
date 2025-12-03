package src.core;

public abstract class AbstractRequest {
    private final int id;
    private String requester;
    private String location;

    public AbstractRequest(int id, String requester, String location) {
        this.id = id;
        this.requester = requester;
        this.location = location;
    }

    public int getId() { return id; }
    public String getRequester() { return requester; }
    public void setRequester(String r) { requester = r; }
    public String getLocation() { return location; }
    public void setLocation(String l) { location = l; }

    public abstract String displaySummary();
}
