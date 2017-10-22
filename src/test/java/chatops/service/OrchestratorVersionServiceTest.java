package chatops.service;

import chatops.entity.orchcestrator.OrchestratorCheckInfo;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.GeneralSecurityException;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author Serhii Solohub
 *         created on: 15.03.17
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OrchestratorVersionServiceTest.OrchestratorVersionServiceConfiguration.class})
public class OrchestratorVersionServiceTest {

    @Autowired
    private OrchestratorVersionService service;

    @Test
    @Ignore
    public void checkOrchestratorTest() throws Exception {
        OrchestratorCheckInfo orchestratorCheckInfo = service.checkOrchestrator("https://host");
        assertNotNull(orchestratorCheckInfo);
    }

    @Test
    @Ignore
    public void checkAll() throws Exception {
        Collection<OrchestratorCheckInfo> result = service.checkAllOrchestrators();
        assertNotNull(result);
    }

    @Configuration
    static class OrchestratorVersionServiceConfiguration {

        @Bean
        public OrchestratorVersionService service() {
            return new OrchestratorVersionService(restTemplate(), "https://node1,https://node1,");
        }
        @Bean
        public RestTemplate restTemplate() {
            try {
                SSLContext context = SSLContexts.custom().loadTrustMaterial((chain, authType) -> true).build();
                SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
                CloseableHttpClient client = HttpClients.custom()
                        .setSSLSocketFactory(csf)
                        .build();

                HttpComponentsClientHttpRequestFactory cf = new HttpComponentsClientHttpRequestFactory();
                cf.setHttpClient(client);
                return new RestTemplate(cf);

            } catch (GeneralSecurityException e) {
                throw new BeanInitializationException("failed to create unsafe rest template, reason: " + e.getMessage(), e);
            }
        }
    }
}