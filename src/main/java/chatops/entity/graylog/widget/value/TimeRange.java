package chatops.entity.graylog.widget.value;

/**
 * @author Serhii Solohub
 *         created on: 04.11.16
 */
public class TimeRange {
    private String type;
    private long range;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getRange() {
        return range;
    }

    public void setRange(long range) {
        this.range = range;
    }
}
