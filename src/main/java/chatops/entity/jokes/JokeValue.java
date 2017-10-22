package chatops.entity.jokes;

import java.util.Collection;

/**
 * @author Serhii Solohub
 *         created on: 22.10.16
 */
public class JokeValue {
    private Collection<String> categories;
    private long id;
    private String joke;

    public Collection<String> getCategories() {
        return categories;
    }

    public void setCategories(Collection<String> categories) {
        this.categories = categories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JokeValue{");
        sb.append("categories=").append(categories);
        sb.append(", id=").append(id);
        sb.append(", joke='").append(joke).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
