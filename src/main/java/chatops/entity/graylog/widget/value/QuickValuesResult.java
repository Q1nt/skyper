package chatops.entity.graylog.widget.value;

import java.util.Map;

/**
 * @author Serhii Solohub
 *         created on: 04.11.16
 */
public class QuickValuesResult {
    private long missing;
    private long other;
    private long total;
    private Map<String, Long> terms;

    public long getMissing() {
        return missing;
    }

    public void setMissing(long missing) {
        this.missing = missing;
    }

    public long getOther() {
        return other;
    }

    public void setOther(long other) {
        this.other = other;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Map<String, Long> getTerms() {
        return terms;
    }

    public void setTerms(Map<String, Long> terms) {
        this.terms = terms;
    }
}
