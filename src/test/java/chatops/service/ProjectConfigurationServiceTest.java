package chatops.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Serhii Solohub
 *         created on: 13.04.17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProjectConfigurationServiceTest.Config.class)
@TestPropertySource
public class ProjectConfigurationServiceTest {
    @Autowired
    private ProjectConfigurationService service;

    @Test
    public void getProjectChatId() throws Exception {
        Assert.notNull(service);
        assertThat(service.getProjectChatId("maestro2"), is("maestro2-chat"));
        assertThat(service.getProjectChatId("maestro3"), is("maestro3-chat"));
    }

    @Test
    public void getProjectChatIdErrorPath() throws Exception {
        try {
            service.getProjectChatId(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("got blank project key"));
        }

        try {
            service.getProjectChatId("");
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("got blank project key"));
        }


        try {
            service.getProjectChatId("maestro1");
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is("no project configuration for key maestro1"));
        }

    }


    @Configuration
    static class Config {

        @Bean
        ProjectConfigurationService projectConfigurationService() {
            return new ProjectConfigurationService();
        }

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
            return new PropertySourcesPlaceholderConfigurer();
        }

    }
}