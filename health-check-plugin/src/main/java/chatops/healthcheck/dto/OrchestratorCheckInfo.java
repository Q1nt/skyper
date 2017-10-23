package chatops.healthcheck.dto;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Serhii Solohub
 *         created on: 15.03.17
 */
public class OrchestratorCheckInfo {

    private CheckStatus status = CheckStatus.RED;
    private String host;
    private Collection<OrchestratorVersion> versions = new ArrayList<>();
    private Collection<String> problems = new ArrayList<>();

    public CheckStatus getStatus() {
        return status;
    }

    public void setStatus(CheckStatus status) {
        this.status = status;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Collection<OrchestratorVersion> getVersions() {
        return versions;
    }

    public void setVersions(Collection<OrchestratorVersion> versions) {
        this.versions = versions;
    }

    public Collection<String> getProblems() {
        return problems;
    }

    public void setProblems(Collection<String> problems) {
        this.problems = problems;
    }
}
