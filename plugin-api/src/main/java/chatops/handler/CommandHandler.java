package chatops.handler;

import chatops.skype.dto.SkypeEvent;

/**
 * @author Serhii Solohub
 *         created on: 03.11.16
 */
public interface CommandHandler {

    boolean shouldHandle(String text);
    void handle(SkypeEvent event);
}
