package chatops.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * @author Serhii Solohub
 *         created on: 16.03.17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HowDoIServiceTest.TestConfiguration.class)
public class HowDoIServiceTest {

    @Autowired
    HowDoIService service;

    @Test
    @Ignore
    public void ask() throws Exception {
        String response = service.ask("make c source");
        Assert.notNull(response);
        System.err.println(response);
    }

    static class TestConfiguration {

        String host = "https://how-doi.herokuapp.com";

        @Bean
        RestTemplate restTemplate() {
            return new RestTemplate();
        }

        @Bean
        HowDoIService howDoIService() {
            return new HowDoIService(restTemplate(), host);
        }
    }

}