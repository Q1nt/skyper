package chatops.service;

import chatops.Constants;
import chatops.entity.orchcestrator.CheckStatus;
import chatops.entity.orchcestrator.OrchestratorCheckInfo;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author Serhii Solohub
 *         created on: 15.03.17
 */
@Component
@ConditionalOnExpression(value = "${orchestrator.monitor}")
public class OrchestratorSimpleMonitoringService {

    private static final Logger LOG = Logger.getLogger(OrchestratorSimpleMonitoringService.class);
    private final OrchestratorVersionService orchestratorService;
    private final SkypeService skypeService;

    public OrchestratorSimpleMonitoringService(OrchestratorVersionService orchestratorService, SkypeService skypeService) {
        this.orchestratorService = orchestratorService;
        this.skypeService = skypeService;
    }

    @Scheduled(fixedDelayString = "${orchestrator.monitor.delay}")
    public void monitorOrchestrator() {
        Collection<OrchestratorCheckInfo> infos = orchestratorService.checkAllOrchestrators();

        for (OrchestratorCheckInfo info : infos) {
            if (info.getStatus() == CheckStatus.RED) {
                LOG.error("found orchestrator with RED status, host: " + info.getHost() + " , problems: " + info.getProblems());
                skypeService.sendMessage(Constants.TEST_CHAT_ID, "(ghost) some problems with orchestrator on " + info.getHost() + " (ghost)");
                skypeService.sendMessage(Constants.TEST_CHAT_ID, "details: " + String.join("\n", info.getProblems()));
            } else {
                LOG.info("with orchestrator " + info.getHost() + " everything is ok :)");
            }
        }

    }



}
