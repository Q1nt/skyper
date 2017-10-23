package chatops.jira.dto;

/**
 * @author Serhii Solohub
 *         created on: 13.04.17
 */
public class JiraProject {
    private String id;
    private String key;
    private String name;
    private String self;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JiraProject{");
        sb.append("id='").append(id).append('\'');
        sb.append(", key='").append(key).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", self='").append(self).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
