package chatops.graylog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Serhii Solohub
 *         created on: 04.11.16
 */
public class WidgetValue<T> {
    private T result;
    @JsonProperty("calculated_at")
    private String calculatedAt;
    @JsonProperty("took_ms")
    private String calculationTime;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getCalculatedAt() {
        return calculatedAt;
    }

    public void setCalculatedAt(String calculatedAt) {
        this.calculatedAt = calculatedAt;
    }

    public String getCalculationTime() {
        return calculationTime;
    }

    public void setCalculationTime(String calculationTime) {
        this.calculationTime = calculationTime;
    }
}
