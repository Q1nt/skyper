package chatops.core.handler;

import chatops.handler.CommandHandler;
import chatops.skype.dto.Contact;
import chatops.skype.dto.SkypeEvent;
import chatops.skype.service.SkypeService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Serhii Solohub
 *         created on: 03.11.16
 */
@Component
public class Greeter implements CommandHandler {

    private static final Pattern PATTERN = Pattern.compile("(?i)^hi|hey|hello|yo|<.*> hi|<.*> hey|<.*> hello|<.*> yo");
    private static final Logger LOG = Logger.getLogger(Greeter.class);
    public static final String UNKNOWN_FROM_MESSAGE = "Who is here? o_O";

    private final SkypeService skype;
    private final List<String> greetings = Arrays.asList(
            "Hello %s!", "Glad to see you %s!", "Konishua %s-san", "Hey cutie :3"
    );

    public Greeter(SkypeService skype) {
        this.skype = skype;
    }

    @Override
    public boolean shouldHandle(String text) {
        return PATTERN.matcher(text).matches();
    }

    @Override
    public void handle(SkypeEvent event) {
        String conversationId = event.getConversation().getId();
        if (StringUtils.isEmpty(conversationId)) {
            LOG.error("oh shit, unexpected condition met, conversation id is blank");
            return;
        }

        Contact from = event.getFrom();
        String message;
        if (from == null || StringUtils.isEmpty(from.getName())) {
            message = UNKNOWN_FROM_MESSAGE;
        } else {
            message = getRandomGreeting(from.getName());
        }
        skype.sendMessage(conversationId, message);
    }

    public String getRandomGreeting(String name) {
        int i = RandomUtils.nextInt(0, greetings.size());
        String message = greetings.get(i);
        return String.format(message, name);
    }
}
