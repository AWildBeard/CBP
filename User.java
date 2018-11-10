public class User extends Client {
    private String userName,
        fullName;
    private int deptCode;

    public User() {
        // Super is already called
        setUserName(null);
        setFullName(null);
        setDeptCode(0);
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
        return this.fullName + "\t" + this.userName + "\t" + this.deptCode + super.toString();
    }
}