package chatops.entity.graylog;

import java.util.Collection;

/**
 * @author Serhii Solohub
 *         created on: 03.11.16
 */
public class Dashboards {
    private Collection<Dashboard> dashboards;
    private int total;

    public Collection<Dashboard> getDashboards() {
        return dashboards;
    }

    public void setDashboards(Collection<Dashboard> dashboards) {
        this.dashboards = dashboards;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
