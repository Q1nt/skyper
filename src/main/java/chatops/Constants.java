package chatops;

/**
 * just ad hoc constants.
 * @author Serhii Solohub
 *         created on: 17.09.16
 */
public interface Constants {
    String AUTH_URL = "https://login.microsoftonline.com/common/oauth2/v2.0/token";
    String SKYPE_API_URL = "https://apis.skype.com/v3/conversations/"; // specific for conversations, change to generic one

    String REQUEST_TOKEN_SCOPE = "https://graph.microsoft.com/.default";
    String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";

    String TEST_CHAT_ID = "";

    String ISSUE_CREATED_TYPE = "jira:issue_created";
    String ISSUE_UPDATED_TYPE = "jira:issue_updated";
    String WORKLOG_UPDATED_TYPE = "jira:worklog_updated";
    String ISSUE_TYPE_BUG = "Bug";
    String ISSUE_TYPE_TASK = "Task";
    String ISSUE_TYPE_IMPROVEMENT = "Improvement";
    String ISSUE_TYPE_EPIC = "Epic";

    String JIRA_ENDPOINT = "/jira";
    String SKYPE_ENDPOINT = "/bot";
    String STATUS_ENDPOINT = "/status";


    String MESSAGE_TYPE_EVENT = "message";
}
