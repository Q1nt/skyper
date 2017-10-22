package chatops.entity.command;

/**
 * @author Maksym_Zinkevych
 *         created on 10/4/2016.
 */
public class SkyperCommand {
    private SkyperCommand from;
    private SkyperCommand conversation;

    private String fromId;
    private String id;
    private boolean isGroup;
    private String text;

    public SkyperCommand getFrom() {
        return from;
    }

    public SkyperCommand getConversation() {
        return conversation;
    }

    public String getFromId() {
        return fromId;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean getIsGroup() {
        return isGroup;
    }

}
