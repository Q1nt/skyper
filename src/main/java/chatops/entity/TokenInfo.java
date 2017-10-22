package chatops.entity;

/**
 * just an entity for caching
 * @author Serhii Solohub
 *         created on: 17.09.16
 */
public class TokenInfo {
    private final String clientId;
    private final String accessToken;
    private final long expiresOn;

    public TokenInfo(String clientId, String accessToken, long expiresOn) {
        this.clientId = clientId;
        this.accessToken = accessToken;
        this.expiresOn = System.currentTimeMillis() + expiresOn * 1000;;

    }

    public String getClientId() {
        return clientId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiresOn() {
        return expiresOn;
    }
}
