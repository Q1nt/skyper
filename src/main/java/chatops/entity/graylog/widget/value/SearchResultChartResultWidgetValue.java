package chatops.entity.graylog.widget.value;

import chatops.entity.graylog.widget.ComputationTimeRange;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @author Serhii Solohub
 *         created on: 04.11.16
 */
public class SearchResultChartResultWidgetValue extends WidgetValue<Map<String, Long>> {
    @JsonProperty("computation_time_range")
    private ComputationTimeRange computationComputationTimeRange;

    public ComputationTimeRange getComputationComputationTimeRange() {
        return computationComputationTimeRange;
    }

    public void setComputationComputationTimeRange(ComputationTimeRange computationComputationTimeRange) {
        this.computationComputationTimeRange = computationComputationTimeRange;
    }


}
