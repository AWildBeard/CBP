import java.lang.IllegalArgumentException;

// Maybe create own IllegalArgumentException?

public class Client {

    private String clearPassword, 
        encryptedPassword,
        key;
    private int clientId;
    private static int nextIDNum = 1000;

    public Client() {
        clearPassword = encryptedPassword = key = null;
        clientId = 0;
    }

    public Client(String clearPassword, String key) throws IllegalArgumentException {
        setKey(key);
        setClearPassword(clearPassword); // Depends on key
        setClientId();
    }

    // Utility
    private boolean goodPassword(String clearPassword) {
        // TODO: Determine if the password is a good password
        return false;
    }

    private String encryptPassword(String clearPassword) {
        String encryptedPassword = null;
        // TODO: Encrypt;
        // TODO: Return an 'encrypted' password
        return encryptedPassword;
    }

    // Mutators
    public void setClearPassword(String clearPassword) throws IllegalArgumentException {
        // Will short circuit before causing any trouble in goodPassword
        if (clearPassword != null && goodPassword(clearPassword)) {
            setClearPassword(clearPassword);
            setEncryptedPassword(clearPassword);
        } else {
            throw new IllegalArgumentException("Invalid password!");
        }
    }

    private void setEncryptedPassword(String clearPassword) {
        this.encryptedPassword = encryptPassword(clearPassword);
    }

    private void setKey(String key) {
        this.key = key;
    }

    public void setClientId() {
        this.clientId = nextIDNum++;
    }

    // Accessors
    public String getClearPassword() {
        return this.clearPassword;
    }

    public String getEncryptedPassword() {
        return this.encryptedPassword;
    }

    public String getKey() {
        return this.key;
    }

    public int getClientId() {
        return this.clientId;
    }

    public String toString() {
        return this.encryptedPassword + "\t" + this.clearPassword + "\t" + this.clientId;
    }

}