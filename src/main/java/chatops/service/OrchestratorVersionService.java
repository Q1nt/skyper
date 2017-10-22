package chatops.service;

import chatops.entity.orchcestrator.CheckStatus;
import chatops.entity.orchcestrator.OrchestratorCheckInfo;
import chatops.entity.orchcestrator.OrchestratorVersion;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Serhii Solohub
 *         created on: 15.03.17
 */
@Service
public class OrchestratorVersionService {

    private static final Logger LOG = Logger.getLogger(OrchestratorVersionService.class);

    private static final String MAESTRO2_VERSION_ENDPOINT = "/maestro2/version";
    private static final String SITE_VERSION_ENDPOINT = "/site/version";
    private static final String API_VERSION_ENDPOINT = "/api/version";
    private static final String ADMIN_VERSION_ENDPOINT = "/admin/version";

    private final RestTemplate http;
    private final String orchUrls;
    private final List<String> urls = new ArrayList<>();

    public OrchestratorVersionService(@Qualifier("unsafeRestTemplate") RestTemplate http, @Value("${orchestrator.monitor.urls}") String orchUrls) {
        this.http = http;
        this.orchUrls = orchUrls;
    }

    @PostConstruct
    private void init(){
        if (StringUtils.isBlank(orchUrls)) {
            throw new IllegalStateException("please specify orch urls to check versions");
        }

        if (orchUrls.contains(",")) {
            String[] urls = orchUrls.split(",");
            this.urls.addAll(Arrays.asList(urls));
        } else {
            this.urls.add(orchUrls);
        }

        LOG.info("initialized orchestrator urls: " + orchUrls);
    }


    public Collection<OrchestratorCheckInfo> checkAllOrchestrators(){
        return urls.stream()
                .map(this::checkOrchestrator)
                .collect(Collectors.toList());
    }

    public OrchestratorCheckInfo checkOrchestrator(String host) {
        OrchestratorCheckInfo result = new OrchestratorCheckInfo();
        result.setHost(host);

        populateVersion(host + MAESTRO2_VERSION_ENDPOINT, result);
        populateVersion(host + SITE_VERSION_ENDPOINT, result);
        populateVersion(host + API_VERSION_ENDPOINT, result);
        populateVersion(host + ADMIN_VERSION_ENDPOINT, result);

        result.setStatus(CollectionUtils.isEmpty(result.getProblems())? CheckStatus.GREEN : CheckStatus.RED );

        return result;
    }

    public void populateVersion(String uri, OrchestratorCheckInfo info) {
        try {
            info.getVersions().add(getVersion(uri));
        } catch (Exception e) {
            String message = "failed to get version from: " + uri + ", reason: " + e.getClass().getSimpleName();
            info.getProblems().add(message);
            LOG.error(message, e);
        }
    }

    public OrchestratorVersion getVersion(String uri) {
        ResponseEntity<OrchestratorVersion> responseEntity = http.getForEntity(uri, OrchestratorVersion.class);
        return responseEntity.getBody();
    }
}
