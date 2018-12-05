package cbp.clients;

import cbp.clients.exceptions.InvalidPasswordException;

/**
 * @author Michael Mitchell
 */
public class User extends Client {
    /**
     * User name and Full name data fields
     */
    private String userName,
        fullName;

    /**
     * The department code the user is apart of
     */
    private int deptCode;

    // Expose how this class reports its toString data
    /**
     * Public headers to illustrate which data are in which columns
     */
    public static final String toStringHeaders[] = {"Full Name", "User Name", "Dept Code"};
    public static final String toStringFormat = "%-" + maxFieldLength + "s\t%-" +
            maxFieldLength + "s\t%-" + maxFieldLength + "s";

    /**
     * Default constructor
     */
    public User() {
        // Super is already called
        setUserName("");
        setFullName("");
        setDeptCode(0);
    }

    /**
     * Parameterized constructor to allow creating custom Users
     * @param clearPassword The clear text password
     * @param key The key used to create an encrypted password
     * @param userName The username of the User
     * @param fullName The full name of the User
     * @param deptCode The Department Code of the User
     * @throws InvalidPasswordException For if the password does not meet the password requirements
     */
    public User(String clearPassword, String key, String userName,
            String fullName, int deptCode) throws InvalidPasswordException {
        super(clearPassword, key);
        setUserName(userName);
        setFullName(fullName);
        setDeptCode(deptCode);
    }

    /**
     * Public mutator to set the username of the user
     * @param newName The new username for the user
     */
    public void setUserName(String newName) {
        this.userName = newName;
    }

    /**
     * Public mutator to set the full name of the user
     * @param newName The new full name for the user
     */
    public void setFullName(String newName) {
        this.fullName = newName;
    }

    /**
     * Public mutator to set the Department Code for the user
     * @param newDeptCode The new department code for the user
     */
    public void setDeptCode(int newDeptCode) {
        this.deptCode = newDeptCode;
    }

    /**
     * Public accessor to get the username of the User
     * @return The username for the user
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Public accessor to get the full name of the User
     * @return The full name for the user
     */
    public String getFullName() {
        return this.fullName;
    }

    /**
     * Public accessor to get the department code for the User
     * @return The department code for the user
     */
    public int getDeptCode() {
        return this.deptCode;
    }

    /**
     * The String representation of the User
     * @return The String representation of the User
     */
    public String toString() {
        return String.format(toStringFormat, this.fullName,
                this.userName, this.deptCode) + super.toString() + "\n";
    }
}