package chatops.skype;

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

    String SKYPE_ENDPOINT = "/bot";
    String MESSAGE_TYPE_EVENT = "message";
}
