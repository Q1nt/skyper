package chatops.skype.handlers;

import chatops.entity.command.SkypeEvent;
import chatops.service.HowDoIService;
import chatops.service.SkypeService;
import chatops.skype.CommandHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Serhii Solohub
 *         created on: 16.03.17
 */
@Component
public class HowDoI implements CommandHandler{
    private static final Logger LOG = Logger.getLogger(HowDoI.class);
    private static final String RESPONSE_PATTERN = "(ninja) so, how to %s: \n\n%s\n";
    private static final Pattern PATTERN = Pattern.compile("^.*howdoi(?<question>.*)$");

    private final SkypeService skype;
    private final HowDoIService service;


    public HowDoI(SkypeService skype, HowDoIService service) {
        this.skype = skype;
        this.service = service;
    }

    @Override
    public boolean shouldHandle(String text) {
        return StringUtils.isNotBlank(text) && text.toLowerCase().contains("howdoi");
    }

    @Override
    public void handle(SkypeEvent event) {
        String conversationId = event.getConversation().getId();
        if (org.springframework.util.StringUtils.isEmpty(conversationId)) {
            LOG.error("oh shit, unexpected condition met, conversation id is blank");
            return;
        }

        String text = event.getText().toLowerCase();

        Matcher matcher = PATTERN.matcher(text);

        String response;
        String question = null;
        if (matcher.matches()) {
            String value = matcher.group("question");
            question = StringUtils.normalizeSpace(value);
        }

        if (StringUtils.isNotBlank(question)) {
            String rawAnswer = service.ask(question);
            response = String.format(RESPONSE_PATTERN, question, rawAnswer);
        } else {
            response = "sorry, could not parse your question :|";
        }

        skype.sendMessage(conversationId, response);
    }
}
