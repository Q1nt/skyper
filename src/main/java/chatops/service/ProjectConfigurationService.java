package chatops.service;

import chatops.entity.ProjectConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Serhii Solohub
 *         created on: 13.04.17
 */
@Component
public class ProjectConfigurationService {

    private static final Logger LOG = Logger.getLogger(ProjectConfigurationService.class);

    private final Map<String, ProjectConfiguration> configurations = new HashMap<>();
    @Value("${maestro2.project.key}")
    String maestro2ProjectKey;
    @Value("${maestro3.project.key}")
    String maestro3ProjectKey;
    @Value("${maestro2.chat.id}")
    String maestro2ChatId;
    @Value("${maestro3.chat.id}")
    String maestro3ChatId;

    @PostConstruct
    private void init(){
        LOG.info("initializing configuration projects configuration");
        addConfiguration(maestro2ProjectKey, maestro2ChatId);
        addConfiguration(maestro3ProjectKey, maestro3ChatId);
        LOG.info("projects configuration initialized");
    }

    private void addConfiguration(String key, String chatId){
        LOG.info(String.format("adding configuration for project %s chat id %s", key, chatId));
        ProjectConfiguration configuration = new ProjectConfiguration(key, chatId);
        configurations.put(key, configuration);
    }

    public String getProjectChatId(String projectKey) {
        if (StringUtils.isBlank(projectKey)) {
            throw new IllegalArgumentException("got blank project key");
        }

        if (!configurations.containsKey(projectKey)) {
            throw new IllegalStateException("no project configuration for key " + projectKey);
        } else {
            return configurations.get(projectKey).getSkypeChatId();
        }
    }

}
