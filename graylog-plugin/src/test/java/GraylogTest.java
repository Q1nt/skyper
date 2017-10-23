/**
 * @author Serhii Solohub
 * created on: 03.11.16
 */

import chatops.graylog.dto.Dashboard;
import chatops.graylog.dto.SearchResultCountWidgetValue;
import chatops.graylog.dto.WidgetConfiguration;
import chatops.graylog.service.GraylogService;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {GraylogTest.GraylogTestConfiguration.class})
@TestPropertySource()
public class GraylogTest {

    @Value("${dashboard.allprod.id}")
    private String allprodDashboardId;
    @Value("${dashboard.prod.id}")
    private String prodDashboardId;
    @Value("${dashboard.qa.id}")
    private String qaDashboardId;
    @Value("${dashboard.dev.id}")
    private String devDashboardId;

    @Value("${graylog.host}")
    private String graylogHost;

    @Autowired
    GraylogService graylogService;

    @Autowired
    RestTemplate http;

    @Test
    @Ignore
    public void test_list_dashboards() throws Exception {
        Collection<Dashboard> dashboards = graylogService.listDashboards();
        for (Dashboard dashboard : dashboards) {
            System.out.println(dashboard.getTitle() + " :: " + dashboard.getDescription());
        }
    }

    @Test
    @Ignore
    public void get_dashboard_by_id() throws Exception {
        Dashboard allProdDashboard = graylogService.getDashboard(allprodDashboardId);
        Dashboard prodDashboard = graylogService.getDashboard(prodDashboardId);
        Dashboard qaDashboard = graylogService.getDashboard(qaDashboardId);
        Dashboard devDashboard = graylogService.getDashboard(devDashboardId);

        printInfoAboutDashboard(allProdDashboard);
        printInfoAboutDashboard(prodDashboard);
        printInfoAboutDashboard(qaDashboard);
        printInfoAboutDashboard(devDashboard);
    }

    @Test
    @Ignore
    public void get_widget_value() throws Exception {
        WidgetConfiguration config = graylogService.getWidgetConfiguration(allprodDashboardId, "21fcfa35-aa9b-4786-8899-b7808a1af825");
        SearchResultCountWidgetValue countWidgetValue = graylogService.getCountWidgetValue(allprodDashboardId, "21fcfa35-aa9b-4786-8899-b7808a1af825");

        System.err.println(config);
        System.err.println(countWidgetValue);

    }

    void printInfoAboutDashboard(Dashboard dashboard) {

        System.err.println("dashboard: " + dashboard.getTitle());
        System.err.println("description: " + dashboard.getDescription());
        for (WidgetConfiguration widget : dashboard.getWidgets()) {
            System.err.println(widget);
        }
    }

    @Configuration
    static class GraylogTestConfiguration {

        @Value("${graylog.host}")
        private String graylogHost;
        @Value("${graylog.user}")
        private String graylogUser;
        @Value("${graylog.password}")
        private String graylogPassword;

        @Bean
        public GraylogService graylogService() {
            return new GraylogService(restTemplate(), graylogHost);
        }

        @Bean
        public RestTemplate restTemplate() {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(httpClientFactory());
            return restTemplate;
        }

        @Bean
        public ClientHttpRequestFactory httpClientFactory() {
            return new HttpComponentsClientHttpRequestFactory(httpClient());
        }

        @Bean
        public HttpClient httpClient() {
            return HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider()).build();
        }

        @Bean
        public CredentialsProvider credentialsProvider(){
            BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
            basicCredentialsProvider.setCredentials(
//                    new AuthScope(new HttpHost(AppConfiguration.extractHost(graylogHost))),
                    new AuthScope(new HttpHost(graylogHost)),
                    new UsernamePasswordCredentials(graylogUser, graylogPassword)
            );
            return basicCredentialsProvider;
        }

    }

}
