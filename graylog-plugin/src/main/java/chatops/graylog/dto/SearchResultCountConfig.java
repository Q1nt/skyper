package chatops.graylog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Serhii Solohub
 *         created on: 04.11.16
 */
public class SearchResultCountConfig {
    @JsonProperty("lower_is_better")
    private boolean lowerIsBetter;
    private String query;
    private boolean trend;
    @JsonProperty("timerange")
    private TimeRange timeRange;
    @JsonProperty("stream_id")
    private String streamId;

    public boolean isLowerIsBetter() {
        return lowerIsBetter;
    }

    public void setLowerIsBetter(boolean lowerIsBetter) {
        this.lowerIsBetter = lowerIsBetter;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isTrend() {
        return trend;
    }

    public void setTrend(boolean trend) {
        this.trend = trend;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }
}
