package chatops.healthcheck.dto;

/**
 * @author Serhii Solohub
 *         created on: 15.03.17
 */
public class OrchestratorVersion {

    private String component;
    private long date;
    private String version;

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
