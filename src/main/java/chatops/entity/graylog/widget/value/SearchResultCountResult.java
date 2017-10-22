package chatops.entity.graylog.widget.value;

/**
 * @author Serhii Solohub
 *         created on: 04.11.16
 */
public class SearchResultCountResult {
    private long now;
    private long previous;

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getPrevious() {
        return previous;
    }

    public void setPrevious(long previous) {
        this.previous = previous;
    }
}
