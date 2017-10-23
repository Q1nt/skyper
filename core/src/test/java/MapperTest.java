import chatops.graylog.dto.QuickValuesResult;
import chatops.graylog.dto.QuickValuesWidgetConfiguration;
import chatops.graylog.dto.QuickValuesWidgetValue;
import chatops.graylog.dto.SearchResultChartConfiguration;
import chatops.graylog.dto.SearchResultChartResultWidgetValue;
import chatops.graylog.dto.SearchResultCountConfiguration;
import chatops.graylog.dto.SearchResultCountResult;
import chatops.graylog.dto.SearchResultCountWidgetValue;
import chatops.graylog.dto.WidgetConfiguration;
import chatops.graylog.dto.WidgetValue;
import chatops.jira.dto.JiraEvent;
import chatops.skype.dto.SkypeEvent;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Serhii Solohub
 *         created on: 21.09.16
 */
public class MapperTest {

    String createdEvent;
    String updatedEvent;
    ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        createdEvent = loadAsString("created.json");
        updatedEvent = loadAsString("updated.json");
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


//        mapper.setDefaultTyping(typeResolverBuilder);
    }

    @Test
    public void deserialize_config_collection() throws Exception {
        String json = loadAsString("graylog/collection_of_configs.json");

        Collection<WidgetConfiguration> result = mapper.readValue(json, new TypeReference<Collection<WidgetConfiguration>>() {});
        for (WidgetConfiguration widgetConfiguration : result) {
            System.err.println(widgetConfiguration.getClass().getSimpleName());
        }

    }

    @Test
    public void deserialize_values_collection() throws Exception {
        String json = loadAsString("graylog/collection_of_widget_values.json");

        Collection<WidgetValue> result = mapper.readValue(json, new TypeReference<Collection<WidgetValue>>() {});
        for (WidgetValue widgetConfiguration : result) {
            System.err.println(widgetConfiguration.getClass().getSimpleName());
        }

    }

    @Test
    public void verifyCreatedEventJsonCouldBeParsed() throws Exception {
        JiraEvent event = mapper.readValue(createdEvent, JiraEvent.class);

        assertNotNull(event.getIssue());
        assertNotNull(event.getUser());
        assertNotNull(event.getWebhookEvent());
        assertNotNull(event.getIssue().getFields());
        assertNotNull(event.getIssue().getFields().getProject());

        System.out.println(event);
    }

    @Test
    public void verifyUpdatedEventJsonCouldBeParsed() throws Exception {
        JiraEvent event = mapper.readValue(updatedEvent, JiraEvent.class);

        System.out.println(event.getIssue().getFields().getIssueType().getName());
        assertNotNull(event.getIssue());
        assertNotNull(event.getUser());
        assertNotNull(event.getWebhookEvent());
        assertNotNull(event.getIssue().getFields());
        assertNotNull(event.getIssue().getFields().getProject());

        System.out.println(event);
    }

    @Test
    public void regular_message_deserialize() throws Exception {
        String json = loadAsString("events/regular_message.json");
        SkypeEvent skypeEvent = mapper.readValue(json, SkypeEvent.class);
        assertNotNull(skypeEvent);

    }

    @Test
    public void group_message_deserialize() throws Exception {
        String json = loadAsString("events/group_message.json");
        SkypeEvent skypeEvent = mapper.readValue(json, SkypeEvent.class);
        assertNotNull(skypeEvent);
        System.out.println(skypeEvent);
    }

    @Test
    public void deserialize_quick_value() throws Exception {
        String json = loadAsString("graylog/quick_values_widget.json");
        QuickValuesWidgetValue widgetValue = mapper.readValue(json, QuickValuesWidgetValue.class);
        assertNotNull(widgetValue);
        assertNotNull(widgetValue.getCalculationTime());
        assertNotNull(widgetValue.getCalculatedAt());
        QuickValuesResult result = widgetValue.getResult();
        assertThat(result.getMissing(), is(0L));
        assertThat(result.getOther(), is(0L));
        assertThat(result.getTotal(), is(66L));
        assertNotNull(result.getTerms());
        assertThat(result.getTerms().size(), is(10));
    }

    @Test
    public void deserialize_quick_value_config() throws Exception {
        String json = loadAsString("graylog/quick_values_widget_config.json");
        QuickValuesWidgetConfiguration widgetConfiguration = mapper.readValue(json, QuickValuesWidgetConfiguration.class);
        assertNotNull(widgetConfiguration);
        assertNotNull(widgetConfiguration.getDescription());
        assertNotNull(widgetConfiguration.getCacheTime());
        assertNotNull(widgetConfiguration.getConfig());
    }

    @Test
    public void deserialize_count_value() throws Exception {
        String json = loadAsString("graylog/search_result_count_widget.json");
        SearchResultCountWidgetValue widgetValue = mapper.readValue(json, SearchResultCountWidgetValue.class);
        assertNotNull(widgetValue);
        assertNotNull(widgetValue.getCalculationTime());
        assertNotNull(widgetValue.getCalculatedAt());
        SearchResultCountResult result = widgetValue.getResult();
        assertNotNull(result);
        assertThat(result.getNow(), is(70L));
        assertThat(result.getPrevious(), is(741L));
    }

    @Test
    public void deserialize_count_config() throws Exception {
        String json = loadAsString("graylog/search_result_count_widget_config.json");
        SearchResultCountConfiguration widgetConfiguration = mapper.readValue(json, SearchResultCountConfiguration.class);
        assertNotNull(widgetConfiguration);
        assertNotNull(widgetConfiguration.getDescription());
        assertNotNull(widgetConfiguration.getCacheTime());
        assertNotNull(widgetConfiguration.getConfig());
    }

    @Test
    public void deserialize_chart_value() throws Exception {
        String json = loadAsString("graylog/search_result_chart_widget.json");
        SearchResultChartResultWidgetValue widgetValue = mapper.readValue(json, SearchResultChartResultWidgetValue.class);
        assertNotNull(widgetValue);
        assertNotNull(widgetValue.getCalculationTime());
        assertNotNull(widgetValue.getCalculatedAt());
        assertNotNull(widgetValue.getComputationComputationTimeRange());
        assertNotNull(widgetValue.getComputationComputationTimeRange().getFrom());
        assertNotNull(widgetValue.getComputationComputationTimeRange().getTo());
        assertNotNull(widgetValue.getResult());

    }

    @Test
    public void deserialize_chart_config() throws Exception {
        String json = loadAsString("graylog/search_result_chart_widget_config.json");
        SearchResultChartConfiguration widgetConfiguration = mapper.readValue(json, SearchResultChartConfiguration.class);
        assertNotNull(widgetConfiguration);
        assertNotNull(widgetConfiguration.getDescription());
        assertNotNull(widgetConfiguration.getCacheTime());
        assertNotNull(widgetConfiguration.getConfig());
    }

    protected String loadAsString(String resourceName) {
        StringWriter stringWriter = new StringWriter();
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
            IOUtils.copy(stream, stringWriter);
        } catch (IOException e) {
            throw new RuntimeException("failed to load resource " + resourceName, e);
        }
        return stringWriter.toString();
    }

    static class CustomTypeResolver extends TypeIdResolverBase {

        @Override
        public String idFromValue(Object value) {
            return null;
        }

        @Override
        public String idFromValueAndType(Object value, Class<?> suggestedType) {
            return null;
        }

        @Override
        public JsonTypeInfo.Id getMechanism() {
            return JsonTypeInfo.Id.CUSTOM;
        }
    }
}
