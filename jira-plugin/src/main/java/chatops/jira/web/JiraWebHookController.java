package chatops.jira.web;

import chatops.jira.service.JiraEventReactor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

import java.util.Date;


/**
 * @author Serhii Solohub
 *         created on: 27.09.16
 */
@RestController
public class JiraWebHookController {
    private static final Logger LOG = Logger.getLogger(JiraWebHookController.class);
    private static final String JIRA_ENDPOINT = "/jira";

    @Autowired
    JiraEventReactor eventReactor;
    @Autowired
    ObjectMapper mapper;

    private String lastEvent;
    private Date startTime;
    private boolean processingEnabled;

    // following is optional, just for example
    @PostConstruct
    void init(){
        this.lastEvent = null;
        this.startTime = new Date();
        this.processingEnabled = true; // false by default, but still
    }

    @RequestMapping(path = JIRA_ENDPOINT, method = RequestMethod.POST)
    ResponseEntity<String> handle(@RequestBody String body){
        LOG.info("Got jira event request. \n" + body);
        this.lastEvent = body;
        try {
            if (this.processingEnabled) {
                eventReactor.handleEvent(body);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return ResponseEntity.ok("thanks for event :)");
    }

    @RequestMapping(path = JIRA_ENDPOINT, method = RequestMethod.GET)
    ResponseEntity<String> lastEvent() {
        if (this.lastEvent != null) {
            return ResponseEntity.ok("here your event: \n" + this.lastEvent);
        } else {
            return ResponseEntity.ok("no events since: " + this.startTime);
        }
    }
}
