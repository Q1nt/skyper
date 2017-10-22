package chatops.skype.handlers;

import chatops.entity.command.SkypeEvent;
import chatops.service.ChuckJokesService;
import chatops.service.SkypeService;
import chatops.skype.CommandHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author Serhii Solohub
 *         created on: 03.11.16
 */
@Component
public class Joker implements CommandHandler {
    private static final Logger LOG = Logger.getLogger(Joker.class);
    private final SkypeService skype;
    private final ChuckJokesService jokes;

    public Joker(SkypeService skype, ChuckJokesService jokes) {
        this.skype = skype;
        this.jokes = jokes;
    }

    @Override
    public boolean shouldHandle(String text) {
        return StringUtils.containsIgnoreCase(text, "joke") || StringUtils.containsIgnoreCase(text, "fun");
    }

    @Override
    public void handle(SkypeEvent event) {
        String conversationId = event.getConversation().getId();
        if (org.springframework.util.StringUtils.isEmpty(conversationId)) {
            LOG.error("oh shit, unexpected condition met, conversation id is blank");
            return;
        }

        String joke = jokes.randomJoke();
        skype.sendMessage(conversationId, joke);
    }
}
