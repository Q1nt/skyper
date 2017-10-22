package chatops.entity.graylog.widget.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Serhii Solohub
 *         created on: 04.11.16
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, visible = true, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = QuickValuesWidgetConfiguration.class, name = "QUICKVALUES"),
    @JsonSubTypes.Type(value = SearchResultCountConfiguration.class, name = "SEARCH_RESULT_COUNT"),
    @JsonSubTypes.Type(value = SearchResultCountConfiguration.class, name = "STREAM_SEARCH_RESULT_COUNT"),
    @JsonSubTypes.Type(value = SearchResultChartConfiguration.class, name = "SEARCH_RESULT_CHART"),
})
public class WidgetConfiguration<T> {
    @JsonProperty("cache_time")
    private long cacheTime;
    @JsonProperty("creator_user_id")
    private String createdByUser;
    private String description;
    private String id;
    private String type;
    private T config;

    public long getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getConfig() {
        return config;
    }

    public void setConfig(T config) {
        this.config = config;
    }
}
