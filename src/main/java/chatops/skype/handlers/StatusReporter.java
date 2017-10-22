package chatops.skype.handlers;

import chatops.entity.command.SkypeEvent;
import chatops.service.SkypeService;
import chatops.skype.CommandHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Serhii Solohub
 *         created on: 03.11.16
 */
@Component
public class StatusReporter implements CommandHandler {
    private static final Logger LOG = Logger.getLogger(StatusReporter.class);
    private final SkypeService skype;
    private final Date startTime;

    public StatusReporter(SkypeService skype) {
        this.skype = skype;
        this.startTime = new Date();
    }

    @Override
    public boolean shouldHandle(String text) {
        return StringUtils.containsIgnoreCase(text, "status");
    }

    @Override
    public void handle(SkypeEvent event) {
        String conversationId = event.getConversation().getId();
        if (org.springframework.util.StringUtils.isEmpty(conversationId)) {
            LOG.error("oh shit, unexpected condition met, conversation id is blank");
            return;
        }
        skype.sendMessage(conversationId, "I am alive! ALIVE!");
        skype.sendMessage(conversationId, "Living since " + startTime);
    }
}
