package chatops.jira.dto;

/**
 * @author Serhii Solohub
 *         created on: 13.04.17
 */
public class ProjectConfiguration {
    private final String projectKey;
    private final String skypeChatId;

    public ProjectConfiguration(String projectKey, String skypeChatId) {
        this.projectKey = projectKey;
        this.skypeChatId = skypeChatId;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public String getSkypeChatId() {
        return skypeChatId;
    }
}
