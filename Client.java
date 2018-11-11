import java.lang.IllegalArgumentException;
import java.lang.StringBuilder;
import java.util.Arrays;
import java.util.List;

// Maybe create own IllegalArgumentException?

/**
 * @author Michael Mitchell
 */
public class Client {
    private String clearPassword,
        encryptedPassword,
        key;
    private int clientId;
    private static int nextIDNum = 1000;
    private static final int upperAsciiTableBound = 255;
    private static final List<Character> validSpecialCharacters = 
            Arrays.asList('#', '$', '%', '&');

    public Client() {
        setKey("abcd");
        setClearPassword("P@ssw0rd!");
        setClientId();
    }

    public Client(String clearPassword, String key) throws IllegalArgumentException {
        setKey(key);
        setClearPassword(clearPassword); // Depends on key
        setClientId();
    }

    // Utility
    /**
     * @param clearPassword The password to see if it meets the password requirment
     * @return True if the password meets the requirement, else false
     * 
     * This method is used to determine if the password contains at least one Digit,
     * at least one Alphanumeric Character, and at least one of the following:
     * `#`, `$`, `%`, `&`.
     *
     * If the password contains a character that does not match the criteria above,
     * it is also considered a bad password and the method will return false.
     */
    private boolean goodPassword(String clearPassword) {
        if (clearPassword.length() < 8)
            return false;

        for (final char ch : clearPassword.toCharArray()) {
            if (! Character.isAlphabetic(ch) && ! Character.isDigit(ch) &&
                        ! validSpecialCharacters.stream().anyMatch( c -> c == ch ))
                return false;
        }

        return true;
    }

    /** 
     * @param clearPassword The password to `encrypt` as plaintext
     * @return The `encrypted` string using a rotation cipher derived from a key
     * 
     * This method returns the `encrypted` form of clear password. This `encrypted`
     * string is made by deriving a rotation factor from the key, then rotating
     * each of the characters in the plain text string.
     */
    private String encryptPassword(String clearPassword) {
        StringBuilder encryptedPassword = new StringBuilder(clearPassword.length());
        int rotationNumber = 0;

        for (final char ch : this.key.toCharArray()) {
            rotationNumber += (int) ch;
        }

        rotationNumber = rotationNumber % upperAsciiTableBound;

        for (final char ch : clearPassword.toCharArray()) {
            char charToAppend = (char) ((int) ch + rotationNumber);
            encryptedPassword.append(charToAppend);
        }

        return encryptedPassword.toString();
    }

    // Mutators
    public void setClearPassword(String clearPassword) throws IllegalArgumentException {
        // Will short circuit before causing any trouble in goodPassword
        if (clearPassword != null && goodPassword(clearPassword)) {
            this.clearPassword = clearPassword;
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