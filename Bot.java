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
    private GregorianCalendar makeDate(String date) {
        // TODO: Implement
        return null;
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
        // TODO: Create date obj and assign
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
        // TODO: Return date?
        return null;
    }

    public String toString() {
        return this.botFileName + "\t" + this.category + 
            "\t" + getDate() + "\t" + this.createdBy + super.toString();
    }
}