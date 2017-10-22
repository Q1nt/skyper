package chatops.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author Serhii Solohub
 *         created on: 16.03.17
 */
@Service
public class HowDoIService {

    private static final Logger LOG = Logger.getLogger(HowDoIService.class);
    private static final String HOWDOI_PATH = "/howdoi";
    private static final String REQUEST_PARAM = "text";
    private final RestTemplate http;
    private final String host;
    private final String url;

    public HowDoIService(RestTemplate http, @Value("${howdoi.host}") String host) {
        this.http = http;
        this.host = host;
        this.url = host + HOWDOI_PATH;
    }

    @PostConstruct
    private void init() {
        LOG.info("initialized 'how-do-i' service with host: " + host);
    }

    public String ask(String question){
        ResponseEntity<String> entity = null;
        try {
            entity = http.postForEntity(url, createRequestObject(question), String.class);
        } catch (Exception e) {
            LOG.error("failed to ask how do i with question: " + question + ", reason: " + e.getMessage(), e);
        }

        if (entity != null && entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return "sorry, service temporary unavailable";
        }

    }

    HttpHeaders makeAppFormHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    HttpEntity createRequestObject(String text) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(REQUEST_PARAM, text);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, makeAppFormHeaders());
        return request;
    }
}
