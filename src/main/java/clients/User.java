package clients;

import clients.exceptions.InvalidPasswordException;

/**
 * @author Michael Mitchell
 */
public class User extends Client {
    private String userName,
        fullName;
    private int deptCode;

    // Expose how this class reports its toString data
    public static final String toStringHeaders[] = {"Full Name", "User Name", "Dept Code"};
    public static final String toStringFormat = "%-" + maxFieldLength + "s\t%-" +
            maxFieldLength + "s\t%-" + maxFieldLength + "s";

    public User() {
        // Super is already called
        setUserName("");
        setFullName("");
        setDeptCode(0);
    }

    public User(String clearPassword, String key, String userName,
            String fullName, int deptCode) throws InvalidPasswordException {
        super(clearPassword, key);
        setUserName(userName);
        setFullName(fullName);
        setDeptCode(deptCode);
    }

    // Utility

    // Mutators
    public void setUserName(String newName) {
        this.userName = newName;
    }

    public void setFullName(String newName) {
        this.fullName = newName;
    }

    public void setDeptCode(int newDeptCode) {
        this.deptCode = newDeptCode;
    }

    // Accessors
    public String getUserName() {
        return this.userName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public int getDeptCode() {
        return this.deptCode;
    }

    public String toString() {
        return String.format(toStringFormat, this.fullName,
                this.userName, this.deptCode) + super.toString() + "\n";
    }
}