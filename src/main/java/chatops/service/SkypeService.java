package chatops.service;

import chatops.Constants;
import chatops.entity.TokenInfo;
import chatops.entity.TokenResponse;
import chatops.entity.skype.TextMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static chatops.Constants.GRANT_TYPE_CLIENT_CREDENTIALS;
import static chatops.Constants.REQUEST_TOKEN_SCOPE;

/**
 * @author Serhii Solohub
 *         created on: 03.11.16
 */
@Component
public class SkypeService {

    private static final Logger LOG = Logger.getLogger(SkypeService.class);
    private static final String CHAT_ACTIVITIES_URI = Constants.SKYPE_API_URL + "{chatId}/activities";

    private final RestTemplate http;
    private final Map<String, TokenInfo> tokenCache;

    @Value("${client.id}")
    private String clientId;
    @Value("${client.token}")
    private String clientSecret;

    public SkypeService(RestTemplate http) {
        this.http = http;
        this.tokenCache = new ConcurrentHashMap<>();
    }

    public void sendMessage(String chatId, String message) {
        Assert.hasText(chatId,  "got blank chat id, please don't do that");
        Assert.hasText(message, "got blank message, please don't do that");
        String accessToken = getAccessToken();

        HttpEntity<TextMessage> requestEntity = new HttpEntity<>(new TextMessage(message), createAuthHeaders(accessToken));

        String result = http.postForObject(CHAT_ACTIVITIES_URI, requestEntity, String.class, chatId);
        LOG.info("response for send message: " + result);
    }

    public HttpHeaders createAuthHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }


    public String getAccessToken() {
        TokenInfo tokenInfo = tokenCache.get(clientId);
        if (tokenInfo != null && isValid(tokenInfo)) {
            LOG.info("returning token for " + clientId + " from cache");
            return tokenInfo.getAccessToken();
        }

        TokenResponse response = http.postForObject(Constants.AUTH_URL, createTokenRequest(), TokenResponse.class);
        tokenInfo = new TokenInfo(clientId, response.getAccesToken(), Long.valueOf(response.getExpiresIn()));
        tokenCache.put(clientId, tokenInfo);
        LOG.info("saving token for " + clientId + " to cache");
        return tokenInfo.getAccessToken();
    }

    private MultiValueMap<String, String> createTokenRequest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("scope", REQUEST_TOKEN_SCOPE);
        map.add("grant_type", GRANT_TYPE_CLIENT_CREDENTIALS);
        return map;
    }

    boolean isValid(TokenInfo tokenInfo) {
        return System.currentTimeMillis() < tokenInfo.getExpiresOn();
    }

}
