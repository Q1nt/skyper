package chatops.skype.service;

/**
 * @author Serhii Solohub created on: 10/22/17
 */
public interface SkypeService {

    void sendMessage(String chatId, String message);

    String getAccessToken();
}
