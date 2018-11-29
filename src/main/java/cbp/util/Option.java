package cbp.util;

public class Option<T> {

    private T value;
    private boolean set;

    public Option(T value) {
        if (value != null) {
            this.value = value;
            this.set = true;
        } else {
            this.set = false;
        }
    }

    public boolean hasValue() { return this.set; }

    public T getValue() { return this.value; }

    public void setValue(T value) { this.value = value; }
}
