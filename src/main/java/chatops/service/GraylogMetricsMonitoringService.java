package chatops.service;

import chatops.Constants;
import chatops.entity.graylog.Dashboard;
import chatops.entity.graylog.widget.WidgetType;
import chatops.entity.graylog.widget.configuration.SearchResultCountConfig;
import chatops.entity.graylog.widget.configuration.SearchResultCountConfiguration;
import chatops.entity.graylog.widget.configuration.WidgetConfiguration;
import chatops.entity.graylog.widget.value.SearchResultCountWidgetValue;
import chatops.entity.graylog.widget.value.TimeRange;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

/**
 * @author Serhii Solohub
 *         created on: 04.11.16
 */
@Component
@ConditionalOnExpression(value = "${graylog.monitor}")
public class GraylogMetricsMonitoringService {
    private static final Logger LOG = Logger.getLogger(GraylogMetricsMonitoringService.class);
    private static final long DELAY = 3600 * 1000;

    private final GraylogService graylogService;
    private final SkypeService skypeService;

    @Value("${graylog.monitor.envs}")
    private String envsToMonitor;
    @Value("${errors.threshold}")
    private long errorsThreshold;



    private MultiValueMap<Dashboard, WidgetConfiguration> interestingWidgets;
    private Set<String> alreadyAlertedWidgets = new ConcurrentSkipListSet<>();

    public GraylogMetricsMonitoringService(GraylogService graylogService, SkypeService skypeService) {
        this.graylogService = graylogService;
        this.skypeService = skypeService;
    }

    @PostConstruct
    public void init() {
        LOG.info("start metrics monitoring");
        String[] envs = StringUtils.split(envsToMonitor, ",");
        if (envs.length == 0) {
            LOG.info("no environments to monitor specified");
            return;
        }

        LOG.info("following envs are specified for monitoring: " + Arrays.toString(envs));


        Collection<Dashboard> dashboards = graylogService.listDashboards();
        Set<String> titles = new HashSet<>();
        Collection<Dashboard> interesting = new LinkedList<>();

        for (Dashboard dashboard : dashboards) {
            titles.add(dashboard.getTitle());
            for (String env : envs) {
                if (StringUtils.equalsIgnoreCase(dashboard.getTitle(), env)) {
                    interesting.add(dashboard);
                }
            }
        }

        LOG.info("were following dashboards: " + titles);

        if (CollectionUtils.isEmpty(interesting)) {
            LOG.warn("not found dashboards for following envs: " + Arrays.toString(envs));
            return;
        }

        MultiValueMap<Dashboard, WidgetConfiguration> widgets = new LinkedMultiValueMap<>();

        for (Dashboard dashboard : interesting) {
            for (WidgetConfiguration configuration : dashboard.getWidgets()) {
                if (!isCount(configuration)) {
                    continue;
                }

                if (!(configuration instanceof SearchResultCountConfiguration)) {
                    continue;
                }

                if (isHourConfig((SearchResultCountConfiguration) configuration)){
                    widgets.add(dashboard, configuration);
                }
            }
        }


        LOG.info("found " + widgets.size() + " interesting widgets");
        for (Dashboard dashboard : widgets.keySet()) {
            LOG.info("-> for dashboard " + dashboard.getDescription());
            for (WidgetConfiguration configuration : widgets.get(dashboard)) {
                LOG.info("--> " + configuration.getDescription());
            }
        }

        this.interestingWidgets = widgets;
    }

    @Scheduled(fixedDelay = DELAY)
    public void checkCounts() {
        for (Dashboard dashboard : interestingWidgets.keySet()) {
            for (WidgetConfiguration widgetConfiguration : interestingWidgets.get(dashboard)) {
                process(dashboard, widgetConfiguration);
            }
        }
    }

    public void process(Dashboard dashboard, WidgetConfiguration widgetConfiguration) {
        try {
            String id = widgetConfiguration.getId();

            String dashboardTitle = dashboard.getTitle();
            String widgetDescription = widgetConfiguration.getDescription();

            LOG.info("dashboard: " + dashboardTitle + " , widget: " + widgetDescription);
            SearchResultCountWidgetValue countWidgetValue = graylogService.getCountWidgetValue(dashboard.getId(), widgetConfiguration.getId());

            if (countWidgetValue.getResult().getNow() >= errorsThreshold) {
                if (alreadyAlertedWidgets.contains(id)) {
                    LOG.info("still exceeding threshold, but already alerted ");
                } else {
                    alert(widgetConfiguration, countWidgetValue);
                    alreadyAlertedWidgets.add(id);
                }
            } else {
                if (alreadyAlertedWidgets.contains(id)) {
                    LOG.info("everything is ok now! errors number decreased");
                    alreadyAlertedWidgets.remove(id);
                }
            }
        } catch (Exception e) {
            LOG.error("something went wrong", e);
        }
    }

    private void alert(WidgetConfiguration configuration, SearchResultCountWidgetValue countWidgetValue) {
        String message = String.format("(bomb) alarm! errors count for %s exceeded threshold %d, current value: %d (bomb)", configuration.getDescription(), errorsThreshold, countWidgetValue.getResult().getNow());
        skypeService.sendMessage(Constants.TEST_CHAT_ID, message);
    }

    private boolean isCount(WidgetConfiguration configuration) {
        String type = configuration.getType();
        return WidgetType.SEARCH_RESULT_COUNT.toString().equals(type) || WidgetType.STREAM_SEARCH_RESULT_COUNT.toString().equals(type);
    }

    private boolean isHourConfig(SearchResultCountConfiguration configuration) {
        SearchResultCountConfig config = configuration.getConfig();
        if (config == null) {
            return false;
        }
        TimeRange timeRange = config.getTimeRange();
        if (timeRange == null) {
            return false;
        }
        return timeRange.getRange() == TimeUnit.HOURS.toSeconds(1);
    }
}
