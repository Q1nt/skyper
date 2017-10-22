package chatops.entity.graylog.widget.configuration;

import chatops.entity.graylog.widget.value.TimeRange;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Serhii Solohub
 *         created on: 04.11.16
 */
public class QuickValuesConfig {
    private String field;
    private String query;
    @JsonProperty("show_data_table")
    private boolean showDataTable;
    @JsonProperty("show_pie_chart")
    private boolean showPieChart;
    @JsonProperty("stream_id")
    private String streamId;
    @JsonProperty("timerange")
    private TimeRange timeRange;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isShowDataTable() {
        return showDataTable;
    }

    public void setShowDataTable(boolean showDataTable) {
        this.showDataTable = showDataTable;
    }

    public boolean isShowPieChart() {
        return showPieChart;
    }

    public void setShowPieChart(boolean showPieChart) {
        this.showPieChart = showPieChart;
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
