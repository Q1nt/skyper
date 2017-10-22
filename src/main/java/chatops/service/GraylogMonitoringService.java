package chatops.service;

import chatops.Constants;
import chatops.entity.graylog.GraylogInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Serhii Solohub
 *         created on: 04.11.16
 */
@Component
@ConditionalOnExpression(value = "${graylog.monitor}")
public class GraylogMonitoringService {
    private static final Logger LOG = Logger.getLogger(GraylogMonitoringService.class);
    private final long MONITOR_DELAY = 10 * 60 * 1000; // 10 minutes

    private final GraylogService graylogService;
    private final SkypeService skypeService;

    public GraylogMonitoringService(GraylogService graylogService, SkypeService skypeService) {
        this.graylogService = graylogService;
        this.skypeService = skypeService;
    }


    @Scheduled(fixedDelay = MONITOR_DELAY)
    public void monitorGraylog() {
        if (isGraylogAvailable()) {
            LOG.info("everything ok, graylog is ok");
        } else {
            skypeService.sendMessage(Constants.TEST_CHAT_ID, "(bomb) alarm! graylog was found unavailable! (bomb)");
        }
    }

    public boolean isGraylogAvailable() {
        try {
            GraylogInfo graylogInfo = graylogService.getGraylogInfo();
            return StringUtils.isNoneBlank(graylogInfo.getVersion());
        } catch (Exception e) {
            LOG.error("error while verifying graylog availability, " + e.getMessage());
            return false;
        }
    }
}
