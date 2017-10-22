package chatops.entity.jokes;

/**
 * @author Serhii Solohub
 *         created on: 22.10.16
 */
public class Joke {
    private String type;
    private JokeValue value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JokeValue getValue() {
        return value;
    }

    public void setValue(JokeValue value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Joke{");
        sb.append("type='").append(type).append('\'');
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
