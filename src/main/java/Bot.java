import java.util.GregorianCalendar;

/**
 * @author Michael Mitchell
 */
public class Bot extends Client {
    private String botFileName,
        category,
        createdBy;
    private GregorianCalendar dateUpdated;

    public Bot() {
        setBotFileName(null);
        setCategory(null);
        setCreatedBy(null);
        setDate("01/01/1970");
    }

    public Bot(String clearPassword, String key, String botFileName, 
            String category, String createdBy, String date) {
        setBotFileName(botFileName);
        setCategory(category);
        setCreatedBy(createdBy);
        setDate(date);
    }

    // Utility

    /**
     * @param date The string date representation
     * @return A @see java.util.GregorianCalendar object to represent the date.
     */
    private GregorianCalendar makeDate(String date) {
        return null /*new GregorianCalendar(Integer.parseInt(date.substring(0, 3)),
                Integer.parseInt(date.substring(4, 6)),
                Integer.parseInt(date.substring(7)))*/;
    }

    // Mutators
    public void setBotFileName(String botFileName) {
        this.botFileName = botFileName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setDate(String date) {
        this.dateUpdated = makeDate(date);
    }

    // Accessors
    public String getBotFileName() {
        return this.botFileName;
    }

    public String getCategory() {
        return this.category;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getDate() {
        return this.dateUpdated.toString();
    }

    public String toString() {
        return this.botFileName + "\t" + this.category + 
            "\t" + getDate() + "\t" + this.createdBy + super.toString();
    }
}