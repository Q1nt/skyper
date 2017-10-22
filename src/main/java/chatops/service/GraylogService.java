package chatops.service;

import chatops.entity.graylog.Dashboard;
import chatops.entity.graylog.Dashboards;
import chatops.entity.graylog.GraylogInfo;
import chatops.entity.graylog.widget.configuration.WidgetConfiguration;
import chatops.entity.graylog.widget.value.SearchResultCountWidgetValue;
import chatops.entity.graylog.widget.value.WidgetValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.wiring.ClassNameBeanWiringInfoResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * @author Serhii Solohub
 *         created on: 03.11.16
 */
@Component
public class GraylogService {
    private static final String API_ENDPOINT = "/api";

    private final RestTemplate http;
    private final String graylogHost;


    public GraylogService(RestTemplate http, @Value("${graylog.host}") String graylogHost) {
        this.http = http;
        this.graylogHost = graylogHost;
    }

    public GraylogInfo getGraylogInfo() {
        return http.getForEntity(graylogHost + "/api/", GraylogInfo.class).getBody();
    }

    public Collection<Dashboard> listDashboards() {
        ResponseEntity<Dashboards> responseEntity = http.getForEntity(graylogHost + "/api/dashboards", Dashboards.class);
        return responseEntity.getBody().getDashboards();
    }

    public Dashboard getDashboard(String dashboardId) {
        ResponseEntity<Dashboard> responseEntity = http.getForEntity(graylogHost + "/api/dashboards/" + dashboardId, Dashboard.class);
        return responseEntity.getBody();
    }

    public WidgetConfiguration getWidgetConfiguration(String dashboardId, String widgetId) {
        return http.getForObject(graylogHost + "/api/dashboards/{dashboardId}/widgets/{widgetId}", WidgetConfiguration.class, dashboardId, widgetId);
    }

    public <T extends WidgetValue> T getWidgetValue(String dashboardId, String widgetId, Class<T> type) {
        return http.getForObject(graylogHost + "/api/dashboards/{dashboardId}/widgets/{widgetId}/value", type, dashboardId, widgetId);
    }

    public SearchResultCountWidgetValue getCountWidgetValue(String dashboardId, String widgetId) {
        return getWidgetValue(dashboardId, widgetId, SearchResultCountWidgetValue.class);
    }


}
