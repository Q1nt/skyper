package chatops.web;

import chatops.handler.CommandHandler;
import chatops.skype.Constants;
import chatops.skype.dto.Contact;
import chatops.skype.dto.SkypeEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;

/**
 * @author Serhii Solohub
 *         created on: 27.09.16
 */
@RestController
public class SkypeWebHookController {

    private static final Logger LOG = Logger.getLogger(SkypeWebHookController.class);
    private final ObjectMapper mapper;
    private final Collection<CommandHandler> handlers;

    @Autowired
    public SkypeWebHookController(ObjectMapper mapper, Collection<CommandHandler> handlers) {
        this.mapper = mapper;
        this.handlers = handlers;
    }

    @RequestMapping(path = Constants.SKYPE_ENDPOINT, method = RequestMethod.POST)
    ResponseEntity<?> hook(@RequestBody String body) {
        LOG.info("Got skype event request. \n\n "+ body);
        try {
            SkypeEvent event = deserialize(body);

            handle(event);

        } catch (Exception e) {
            LOG.error("something bad happened, " + e.getMessage(), e);
        }
        // better always respond with ok)
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    void handle(SkypeEvent event) {
        if (!Constants.MESSAGE_TYPE_EVENT.equalsIgnoreCase(event.getType())) {
            LOG.info("not message event, don't know what to do, " + event);
            return;
        }

        String text = event.getText();
        LOG.info("received message with text: " + text);

        if(StringUtils.isBlank(text)) {
            LOG.info("blank message :(");
        }

        Contact from = event.getFrom();
        LOG.info("from: " + from.getName());
        Contact recipient = event.getRecipient();
        LOG.info("recipient: " + recipient.getName());

        CommandHandler handler = findHandler(text);
        if (handler != null) {
            handler.handle(event);
        } else {
            LOG.info("doesn't know such command: " + text +  ", teach me!");
        }
    }

    CommandHandler findHandler(String text) {
        for (CommandHandler handler : handlers) {
            if (handler.shouldHandle(text)) {
                return handler;
            }
        }
        return null;
    }

    private SkypeEvent deserialize(String body) {
        try {
            return mapper.readValue(body, SkypeEvent.class);
        } catch (IOException e) {
            LOG.error("failed to deserialize event, reason " + e.getMessage());
            throw new IllegalStateException("deserialization failed", e);
        }
    }

}
