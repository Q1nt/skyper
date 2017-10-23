package chatops.jira.dto;

/**
 * @author Serhii Solohub
 *         created on: 21.09.16
 */
public class JiraUser {
    private String name;
    private String key;
    private String emailAddress;
    private String displayName;
    private String self;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JiraUser{");
        sb.append("name='").append(name).append('\'');
        sb.append(", key='").append(key).append('\'');
        sb.append(", emailAddress='").append(emailAddress).append('\'');
        sb.append(", displayName='").append(displayName).append('\'');
        sb.append(", self='").append(self).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
