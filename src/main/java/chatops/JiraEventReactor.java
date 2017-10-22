package chatops;

import chatops.entity.jira.JiraEvent;
import chatops.entity.jira.JiraIssueFields;
import chatops.entity.jira.JiraResolution;
import chatops.service.ProjectConfigurationService;
import chatops.service.SkypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * @author Serhii Solohub
 *         created on: 27.09.16
 */
@Service
public class JiraEventReactor {

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
            case Constants.ISSUE_TYPE_BUG:
                builder.append("(bug) ");
                break;
            case Constants.ISSUE_TYPE_IMPROVEMENT:
                builder.append("(computerrage)  ");
                break;
            case Constants.ISSUE_TYPE_EPIC:
                builder.append("(island) ");
                break;
            case Constants.ISSUE_TYPE_TASK:
                builder.append("(monkey) ");
                break;
            default:
                builder.append("[").append(issueType).append("] ");
        }
        builder.append(event.getIssue().getKey()).append(" ");
        String eventType = event.getWebhookEvent();
        switch (eventType) {
            case Constants.ISSUE_CREATED_TYPE:
                builder.append("was created ");
                break;
//            case Constants.ISSUE_UPDATED_TYPE:
//                builder.append("was updated ");
//                break;
            case Constants.ISSUE_UPDATED_TYPE:
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
            case Constants.WORKLOG_UPDATED_TYPE:
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
