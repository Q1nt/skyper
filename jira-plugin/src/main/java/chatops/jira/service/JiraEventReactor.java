package chatops.jira.service;

import chatops.jira.dto.JiraEvent;
import chatops.jira.dto.JiraIssueFields;
import chatops.jira.dto.JiraResolution;
import chatops.skype.service.SkypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Serhii Solohub
 *         created on: 27.09.16
 */
@Service
public class JiraEventReactor {
    private static final String ISSUE_CREATED_TYPE = "jira:issue_created";
    private static final String ISSUE_UPDATED_TYPE = "jira:issue_updated";
    private static final String WORKLOG_UPDATED_TYPE = "jira:worklog_updated";
    private static final String ISSUE_TYPE_BUG = "Bug";
    private static final String ISSUE_TYPE_TASK = "Task";
    private static final String ISSUE_TYPE_IMPROVEMENT = "Improvement";
    private static final String ISSUE_TYPE_EPIC = "Epic";
    private static final Logger LOG = Logger.getLogger(JiraEventReactor.class);

    @Autowired
    ObjectMapper mapper;
    @Autowired
    SkypeService skypeService;
    @Autowired
    ProjectConfigurationService projectConfigurationService;
    @Value("${client.id}")
    private String clientId;
    @Value("${client.token}")
    private String clientSecret;

    public void handleEvent(JiraEvent event) {

        //System.out.println(event.toString());
        String message = composeMessage(event);

        if (!message.equals("ignore")) {
            String projectKey = extractProjectKey(event);
            String chatId = projectConfigurationService.getProjectChatId(projectKey);
            skypeService.sendMessage(chatId, message);
        }
    }

    public void handleEvent(String eventBody) {
        JiraEvent event = deserialize(eventBody);
        handleEvent(event);
    }

    private String extractProjectKey(JiraEvent event) {
        if (event.getIssue() != null && event.getIssue().getFields() != null && event.getIssue().getFields().getProject() != null && StringUtils.isNotBlank(event.getIssue().getFields().getProject().getKey())) {
            return event.getIssue().getFields().getProject().getKey();
        } else {
            throw new IllegalArgumentException("got bad jira event without project key, " + event.toString());
        }
    }

    private JiraEvent deserialize(String body){
        try {
            return mapper.readValue(body, JiraEvent.class);
        } catch (IOException e) {
            LOG.error("failed to deserialize event, reason " + e.getMessage());
            throw new IllegalStateException("deserialization failed", e);
        }
    }

    private String composeMessage(JiraEvent event) {
        StringBuilder builder = new StringBuilder();

        JiraIssueFields fields = event.getIssue().getFields();
        String issueType = fields.getIssueType().getName();
        switch (issueType) {
            case ISSUE_TYPE_BUG:
                builder.append("(bug) ");
                break;
            case ISSUE_TYPE_IMPROVEMENT:
                builder.append("(computerrage)  ");
                break;
            case ISSUE_TYPE_EPIC:
                builder.append("(island) ");
                break;
            case ISSUE_TYPE_TASK:
                builder.append("(monkey) ");
                break;
            default:
                builder.append("[").append(issueType).append("] ");
        }
        builder.append(event.getIssue().getKey()).append(" ");
        String eventType = event.getWebhookEvent();
        switch (eventType) {
            case ISSUE_CREATED_TYPE:
                builder.append("was created ");
                break;
//            case ISSUE_UPDATED_TYPE:
//                builder.append("was updated ");
//                break;
            case ISSUE_UPDATED_TYPE:
                JiraResolution resolution = fields.getResolution();
                if (resolution == null) {
                    return "ignore";
                }
                String taskStatus = resolution.getResName();
                if (taskStatus != null && !taskStatus.isEmpty() && !taskStatus.equals("null")) {
                    if (taskStatus.equals("Closed")) {
                        builder.append("was closed ");
                        break;
                    }
                }
                return "ignore";
            case WORKLOG_UPDATED_TYPE:
                return "ignore";
            default:
                throw new RuntimeException("unknown event type " + eventType);
        }
        builder.append("by ").append(event.getUser().getDisplayName());
        builder.append("\r\n");
        if (fields.getAssignee() != null ) {
            String email = fields.getAssignee().getEmailAddress();
            if (email != null) {
                builder.append("Assigned to ");
                builder.append(email);
                builder.append("\r\n");
            }
        }
        String apiIssueUrl = event.getIssue().getSelf();
        URI uri = null;
        try {
            uri = new URI(apiIssueUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String issueUrl = uri.getScheme() + "://" + uri.getHost() + "/jira/browse/" + event.getIssue().getKey();

        if (fields != null && fields.getSummary() != null) {
            builder.append(fields.getSummary()).append("\r\n");
        }
        builder.append(issueUrl);

        return builder.toString();
    }
}
