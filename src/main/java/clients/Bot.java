package clients;

import clients.exceptions.InvalidPasswordException;

import java.util.Calendar;
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
        setBotFileName("");
        setCategory("");
        setCreatedBy("");
        setDate("01/01/1970");
    }

    /**
     * @param clearPassword The cleartext password for the bot
     * @param key The key to use to encode the password for the bot
     * @param botFileName The filename that the bot is stored in
     * @param category The category that corresponds to the bot
     * @param createdBy Who created the bot
     * @param date The date that the bot was created in DD/MM/YYYY
     */
    public Bot(String clearPassword, String key, String botFileName, 
            String category, String createdBy, String date) throws InvalidPasswordException {
        super(clearPassword, key);
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
        int day = Integer.parseInt(date.substring(0, 2)),
                month = Integer.parseInt(date.substring(3, 5)),
                year = Integer.parseInt(date.substring(6));

//        System.out.format("Day: %d Month: %d Year: %d", day, month, year);
        return new GregorianCalendar(year, month, day);
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
        return this.dateUpdated.get(Calendar.DAY_OF_MONTH) + "/" +
                this.dateUpdated.get(Calendar.MONTH) + "/" +
                this.dateUpdated.get(Calendar.YEAR);
    }

    public String toString() {
        return this.botFileName + "\t" + this.category + 
            "\t" + getDate() + "\t" + this.createdBy + super.toString();
    }
}