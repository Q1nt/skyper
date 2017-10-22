package chatops.entity.command;

/**
 * @author Serhii Solohub
 *         created on: 22.10.16
 */
public class Conversation {
    private boolean isGroup;
    private String id;

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Conversation{");
        sb.append("isGroup=").append(isGroup);
        sb.append(", id='").append(id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

