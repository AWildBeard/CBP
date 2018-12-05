package cbp.util;

/**
 * @author Michael Mitchell
 * @param <T> The type to wrap
 */
public class Option<T> {

    private T value;
    private boolean set;

    /**
     * Constructor to wrap a Type
     * @param value The object to wrap
     */
    public Option(T value) {
        if (value != null) {
            this.value = value;
            this.set = true;
        } else {
            this.set = false;
        }
    }

    /**
     * Public accessor to determine if the Option is wrapping an object
     * @return True if the Option is wrapping an object, False otherwise
     */
    public boolean hasValue() { return this.set; }

    /**
     * Public accessor to retrieve the Object being wrapped by Option
     * @return The object being wrapped by Option
     */
    public T getValue() { return this.value; }

    /**
     * Public mutator to reset the Options value
     * @param value The value to re-wrap
     */
    public void setValue(T value) { this.value = value; }
}
