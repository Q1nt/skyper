package chatops.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

/**
 * @author Serhii Solohub
 *         created on: 27.09.16
 */
@SpringBootApplication
@EnableScheduling
@Import(value = SkyperApp.AppConfiguration.class)
public class SkyperApp {

    public static void main(String[] args) {
        SpringApplication.run(SkyperApp.class, args);
    }

    @Configuration
    public static class AppConfiguration {

        @Value("${graylog.host}")
        private String graylogHost;
        @Value("${graylog.user}")
        private String graylogUser;
        @Value("${graylog.pass}")
        private String graylogPassword;

        @Bean
        ObjectMapper objectMapper() {
            return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        @Bean
        @Primary
        public RestTemplate restTemplate() {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(httpClientFactory());
            return restTemplate;
        }

        @Bean
        public RestTemplate unsafeRestTemplate() {
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

        @Bean
        public ClientHttpRequestFactory httpClientFactory() {
            return new HttpComponentsClientHttpRequestFactory(httpClient());
        }

        @Bean
        public HttpClient httpClient() {
            return HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider()).build();
        }

        @Bean
        public CredentialsProvider credentialsProvider() {
            BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
            basicCredentialsProvider.setCredentials(
                    new AuthScope(new HttpHost(extractHost(graylogHost))),
                    new UsernamePasswordCredentials(graylogUser, graylogPassword)
            );
            return basicCredentialsProvider;
        }

        public static String extractHost(String url) {
            try {
                return new URI(url).getHost();
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
