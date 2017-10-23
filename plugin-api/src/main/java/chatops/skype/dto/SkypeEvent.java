package chatops.skype.dto;

import java.util.Collection;

/**
 * @author Serhii Solohub
 *         created on: 22.10.16
 */
public class SkypeEvent {
    private String id;
    // i saw following:
    // conversationUpdate
    // contactRelationUpdate
    // ping
    // message
    private String type;
    private String timestamp; // in format "2016-10-22T14:13:48.104Z"
    private String channelId; // skype, telegram, etc.
    private String serviceUrl;
    private Contact from;
    private Contact recipient;
    private Conversation conversation;
    private Collection<Object> entities;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public Contact getFrom() {
        return from;
    }

    public void setFrom(Contact from) {
        this.from = from;
    }

    public Contact getRecipient() {
        return recipient;
    }

    public void setRecipient(Contact recipient) {
        this.recipient = recipient;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Collection<Object> getEntities() {
        return entities;
    }

    public void setEntities(Collection<Object> entities) {
        this.entities = entities;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SkypeEvent{");
        sb.append("id='").append(id).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", timestamp='").append(timestamp).append('\'');
        sb.append(", from=").append(from);
        sb.append(", recipient=").append(recipient);
        sb.append(", conversation=").append(conversation);
        sb.append(", entities=").append(entities);
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
