package chatops.dto;

/**
 * @author Serhii Solohub
 *         created on: 03.11.16
 */
public class TextMessage {
    private final String text;
    private final String type = "message/text";

    public TextMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }
}
