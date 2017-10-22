package chatops.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Serhii Solohub
 *         created on: 17.09.16
 */
public class TokenResponse {
    @JsonProperty("access_token")
    private String accesToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("ext_expires_in")
    private String extExpiresIn;

    public String getAccesToken() {
        return accesToken;
    }

    public void setAccesToken(String accesToken) {
        this.accesToken = accesToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getExtExpiresIn() {
        return extExpiresIn;
    }

    public void setExtExpiresIn(String extExpiresIn) {
        this.extExpiresIn = extExpiresIn;
    }
}
