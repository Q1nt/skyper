package chatops.graylog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Serhii Solohub
 *         created on: 03.11.16
 */
public class GraylogInfo {
    @JsonProperty("cluster_id")
    private String clusterId;
    @JsonProperty("node_id")
    private String nodeId;
    private String tagline;
    private String version;

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GraylogInfo{");
        sb.append("clusterId='").append(clusterId).append('\'');
        sb.append(", nodeId='").append(nodeId).append('\'');
        sb.append(", tagline='").append(tagline).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
