package cbp.clients;

import cbp.clients.exceptions.InvalidBotCategoryException;
import cbp.clients.exceptions.InvalidPasswordException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Michael Mitchell
 */
public class Bot extends Client {
    /**
     * The Bots file name, category, and Author data fields
     */
    private String botFileName,
        category,
        createdBy;

    /**
     * Object used to store the creation date of the bot
     * @see java.util.GregorianCalendar
     */
    private GregorianCalendar dateUpdated;

    /**
     * A list of valid categories that the bot can be a part of
     * @see java.util.List
     * @see java.util.Arrays#asList(Object[])
     */
    public static List<String> validCategories = Arrays.asList("IDS", "SysAdm", "HelpDesk");

    // Expose how this class reports its toString data
    /**
     * Utility fields to expose to the public the headers of the toString methods return value.
     * This allows a user to get the categories of the toString columns without having to call
     * toString
     */
    public static final String toStringHeaders[] = {"Bot Name", "Category",
            "Creation Date", "Author"};
    public static final String toStringFormat = "%-" + maxFieldLength + "s\t%-" +
            maxFieldLength + "s\t%-" + maxFieldLength + "s\t%-" + maxFieldLength + "s";

    /**
     * Default constructor
     */
    public Bot() {
        setBotFileName("");
        this.category = "IDS";
        setCreatedBy("");
        setDate("01/01/1970");
    }

    /**
     * Paramerterized constructor to create a Bot
     * @throws InvalidBotCategoryException For when the category field is invalid
     * @throws InvalidPasswordException For when the password field is invalid
     * @throws NumberFormatException For when the date field is invalid
     * @param clearPassword The cleartext password for the bot
     * @param key The key to use to encode the password for the bot
     * @param botFileName The filename that the bot is stored in
     * @param category The category that corresponds to the bot
     * @param createdBy Who created the bot
     * @param date The date that the bot was created in DD/MM/YYYY
     */
    public Bot(String clearPassword, String key, String botFileName, 
            String category, String createdBy, String date) throws InvalidPasswordException,
            InvalidBotCategoryException, NumberFormatException {
        super(clearPassword, key);
        setBotFileName(botFileName);
        setCategory(category);
        setCreatedBy(createdBy);
        setDate(date);
    }

    // Utility

    /**
     * @param date The string date representation
     * @return A Calendar object to represent the date.
     * @see java.util.GregorianCalendar
     */
    private GregorianCalendar makeDate(String date) throws NumberFormatException {
        int day = Integer.parseInt(date.substring(0, 2)),
                month = Integer.parseInt(date.substring(3, 5)),
                year = Integer.parseInt(date.substring(6));

//        System.out.format("Day: %d Month: %d Year: %d", day, month, year);
        return new GregorianCalendar(year, month, day);
    }

    // Mutators

    /**
     * Mutator to set the bot's file name
     * @param botFileName The new file name of the bot
     */
    public void setBotFileName(String botFileName) {
        this.botFileName = botFileName;
    }

    /**
     * Mutator to set a new bot category
     * @param category The category to assign to the bot
     * @throws InvalidBotCategoryException For if the category passed is invalid
     */
    public void setCategory(String category) throws InvalidBotCategoryException {
        if (validCategories.contains(category))
            this.category = category;
        else
            throw new InvalidBotCategoryException("Category: " + category +
                    " is not a valid category!");
    }

    /**
     * Mutator to set the Author of the bot
     * @param createdBy The new author of the bot
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Public mutator to set the Creation date of the bot
     * @param date The new date
     * @throws NumberFormatException For if the date passed is not in the valid format
     */
    public void setDate(String date) throws NumberFormatException {
        this.dateUpdated = makeDate(date);
    }

    // Accessors

    /**
     * Public accessor for the bot's file name
     * @return The file name of the bot
     */
    public String getBotFileName() {
        return this.botFileName;
    }

    /**
     * Public accessor for the bot's category
     * @return The category of the bot
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Public accessor to get the bot's Author
     * @return The author of the bot
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Public accessor to return the creation date of the bot
     * @return The string representation of the bot's creation date
     */
    public String getDate() {
        return this.dateUpdated.get(Calendar.DAY_OF_MONTH) + "/" +
                this.dateUpdated.get(Calendar.MONTH) + "/" +
                this.dateUpdated.get(Calendar.YEAR);
    }

    /**
     * toString to return the bot's fields nicely formatted.
     * @see cbp.clients.Bot#toStringHeaders for more information about what the toString columns are.
     * @return The string representation of the bot
     */
    public String toString() {
        return String.format(toStringFormat, this.botFileName, this.category,
                getDate(), this.createdBy) + super.toString();
    }
}