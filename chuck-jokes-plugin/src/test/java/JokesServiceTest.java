import chatops.jokes.service.ChuckJokesService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

/**
 * @author Serhii Solohub
 *         created on: 22.10.16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JokesServiceTest.JokesTestConfiguration.class)
public class JokesServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(JokesServiceTest.class);

    @Autowired
    ChuckJokesService serviceUnderTest;

    @Test
    @Ignore //ignore to not burden remote api
    public void random_joke_test() throws Exception {
        String result = serviceUnderTest.randomJoke();
        assertNotNull("should give random joke", result);
        LOG.info("random joke: \n" + result);
    }

    @Test
    @Ignore //ignore to not burden remote api
    public void random_joke_personalize() {
        String result = serviceUnderTest.randomPersonalJoke("Serhii", "Solohub");
        assertNotNull("should give random personal joke", result);
        LOG.info("random joke: \n" + result);
    }


    @Configuration
    static class JokesTestConfiguration {

        @Bean
        public ChuckJokesService jokesService() {
            return new ChuckJokesService(restTemplate());
        }

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }
}
