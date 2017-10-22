package chatops.web;

import chatops.service.GraylogMetricsMonitoringService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Serhii Solohub
 *         created on: 04.11.16
 */
@RestController
@ConditionalOnExpression(value = "${graylog.monitor}")
public class GraylogHookController {

    private final GraylogMetricsMonitoringService metricsMonitoringService;

    public GraylogHookController(GraylogMetricsMonitoringService metricsMonitoringService) {
        this.metricsMonitoringService = metricsMonitoringService;
    }

    @GetMapping("/graylog/counts")
    public void recalculate() {
        metricsMonitoringService.checkCounts();
    }
}
