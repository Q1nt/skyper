package chatops.entity.graylog;

import chatops.entity.graylog.widget.configuration.WidgetConfiguration;

import java.util.Collection;
import java.util.Map;

/**
 * @author Serhii Solohub
 *         created on: 03.11.16
 */
public class Dashboard {
    private String id;
    private String title;
    private String description;
    private Collection<WidgetConfiguration> widgets;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<WidgetConfiguration> getWidgets() {
        return widgets;
    }

    public void setWidgets(Collection<WidgetConfiguration> widgets) {
        this.widgets = widgets;
    }
}
