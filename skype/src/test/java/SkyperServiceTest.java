import chatops.skype.service.SkypeService;
import chatops.skype.service.SkypeServiceImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @author Serhii Solohub
 *         created on: 03.11.16
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SkyperServiceTest.SkyperServiceTestConfiguration.class})
@TestPropertySource()
public class SkyperServiceTest {

    private static final String TEST_CHAT_ID = "todo";

    @Autowired
    SkypeService service;

    @Test
    @Ignore
    public void test_send_message() throws Exception {
        service.sendMessage(TEST_CHAT_ID, "¯\\_(ツ)_/¯");
    }

    @Test
    @Ignore
    public void test_get_token() throws Exception {
        String accessToken = service.getAccessToken();
        Assert.assertNotNull(accessToken);
    }

    static class SkyperServiceTestConfiguration {
        @Bean
        SkypeService skypeService() {
            return new SkypeServiceImpl(restTemplate());
        }

        @Bean
        RestTemplate restTemplate() {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(formHttpMessageConverter());
            return restTemplate;
        }

        @Bean
        FormHttpMessageConverter formHttpMessageConverter() {
            return new FormHttpMessageConverter();
        }
    }

}
