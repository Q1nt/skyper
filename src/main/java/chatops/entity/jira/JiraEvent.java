package chatops.entity.jira;

/**
 * @author Serhii Solohub
 *         created on: 21.09.16
 */
public class JiraEvent {

    private long timestamp;
    private String webhookEvent;
    private JiraUser user;
    private JiraIssue issue;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getWebhookEvent() {
        return webhookEvent;
    }

    public void setWebhookEvent(String webhookEvent) {
        this.webhookEvent = webhookEvent;
    }

    public JiraUser getUser() {
        return user;
    }

    public void setUser(JiraUser user) {
        this.user = user;
    }

    public JiraIssue getIssue() {
        return issue;
    }

    public void setIssue(JiraIssue issue) {
        this.issue = issue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JiraEvent{");
        sb.append("timestamp=").append(timestamp);
        sb.append(", webhookEvent='").append(webhookEvent).append('\'');
        sb.append(", user=").append(user);
        sb.append(", issue=").append(issue);
        sb.append('}');
        return sb.toString();
    }
}
