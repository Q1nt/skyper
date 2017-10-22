package chatops.entity.jira;

/**
 * @author Serhii Solohub
 *         created on: 21.09.16
 */
public class JiraIssueFields {
    private String summary;
    private JiraIssueType issuetype;
    private JiraResolution resolution;
    private JiraProject project;
    private JiraUser assignee;

    public JiraResolution getResolution() {
        return resolution;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public JiraIssueType getIssueType() { return issuetype; }

    public void setIssuetype(JiraIssueType issuetype) {
        this.issuetype = issuetype;
    }

    public void setResolution(JiraResolution resolution) {
        this.resolution = resolution;
    }

    public JiraProject getProject() {
        return project;
    }

    public void setProject(JiraProject project) {
        this.project = project;
    }

    public JiraUser getAssignee() {
        return assignee;
    }

    public void setAssignee(JiraUser assignee) {
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JiraIssueFields{");
        sb.append("summary='").append(summary).append('\'');
        sb.append(", issuetype=").append(issuetype);
        sb.append(", resolution=").append(resolution);
        sb.append(", project=").append(project);
        sb.append('}');
        return sb.toString();
    }
}
