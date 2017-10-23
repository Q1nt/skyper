package chatops.graylog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Serhii Solohub
 *         created on: 04.11.16
 */
public class SearchResultChartConfig {
    private String interval;
    private String query;
    @JsonProperty("stream_id")
    private String streamId;
    @JsonProperty("timerange")
    private TimeRange timeRange;

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }
}
