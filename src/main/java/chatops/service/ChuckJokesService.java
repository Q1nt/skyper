package chatops.service;

import chatops.entity.jokes.Joke;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author Serhii Solohub
 *         created on: 22.10.16
 */
@Service
public class ChuckJokesService {
    private static final Logger LOG = LoggerFactory.getLogger(ChuckJokesService.class);
    private static final String DEFAULT_RESPONSE = "no jokes for you :(";
    private static final String RANDOM_JOKE_URI_TEMPLATE_PERSONAL = "https://api.icndb.com/jokes/random?firstName={firstName}&lastName={lastName}";
    private static final String RANDOM_JOKE_URI_TEMPLATE = "https://api.icndb.com/jokes/random";

    private final RestTemplate http;

    @Autowired
    public ChuckJokesService(RestTemplate http) {
        this.http = http;
    }

    public String randomJoke() {
        ResponseEntity<Joke> response = http.getForEntity(RANDOM_JOKE_URI_TEMPLATE, Joke.class);
        return getJokeText(response.getBody());
    }

    public String randomPersonalJoke(String firstName, String lastName) {
        if (StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
            LOG.error("got blank first {} or last {} name. please, don't do that stuff. fallback to default response", firstName, lastName);
            return DEFAULT_RESPONSE;
        }

        ResponseEntity<Joke> response = http.getForEntity(RANDOM_JOKE_URI_TEMPLATE_PERSONAL, Joke.class, firstName, lastName);
        return getJokeText(response.getBody());
    }

    private String getJokeText(Joke joke) {
        LOG.info("got joke: " + joke);
        if (joke.getType().equalsIgnoreCase("success")) {
            return joke.getValue().getJoke();
        } else {
            return DEFAULT_RESPONSE;
        }
    }
}