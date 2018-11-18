package clients;
import clients.exceptions.InvalidPasswordException;

import java.lang.StringBuilder;
import java.util.Arrays;
import java.util.List;

/**
 * @author Michael Mitchell
 */
public class Client {
    private String clearPassword = null,
        encryptedPassword = null,
        key = null;
    private int clientId;
    private static int nextIDNum = 1000;
    private static final int upperAsciiTableBound = 255;
    private static final List<Character> validSpecialCharacters = 
            Arrays.asList('#', '$', '%', '&');

    // Expose how this class reports its toString data
    public static final int maxFieldLength = 25;
    public static final String toStringHeaders[] = {"Enc Pass", "Clear Pass", "Client ID"};
    public static final String toStringFormat = "%-" + maxFieldLength + "s\t%-" +
            maxFieldLength + "s\t%-" + maxFieldLength + "s";

    public Client() {
        this.clearPassword = "P#ssw0rd";
        setKey("abcd");
        setClientId();
    }

    public Client(String clearPassword, String key) throws InvalidPasswordException {
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
    private void goodPassword(String clearPassword) throws InvalidPasswordException {
        if (clearPassword.length() < 8)
            throw new InvalidPasswordException("The password length must be " +
                    "greater than 8 characters!");

        for (final char ch : clearPassword.toCharArray()) {
            if (! Character.isAlphabetic(ch) && ! Character.isDigit(ch) &&
                        ! validSpecialCharacters.stream().anyMatch( c -> c == ch ))
                throw new InvalidPasswordException("Invalid character in password: " + ch);
        }
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

        int rotationNumber = generateRotationNumber();

        for (final char ch : clearPassword.toCharArray()) {
            char charToAppend = (char) ((int) ch + rotationNumber);
            encryptedPassword.append(charToAppend);
        }

        return encryptedPassword.toString();
    }

    // Make this a method so that it can be re-used in reversing the 
    // rotation `encryption` above.
    private int generateRotationNumber() {
        int rotationNumber = 0;

        if (this.key == null) 
            return rotationNumber;

        for (final char ch : this.key.toCharArray()) {
            rotationNumber += (int) ch;
        }

        // Incrementally Bitshift. Darn you binary mafs.
        for (int i = 1; rotationNumber % upperAsciiTableBound == 0 ; i++)
            // Need to get rotation number away from being a multiple of 255
            rotationNumber = (rotationNumber + i) >> i; 

        rotationNumber = rotationNumber % upperAsciiTableBound;

        return rotationNumber;
    }

    // Mutators
    public void setClearPassword(String clearPassword) throws InvalidPasswordException {
        // Will short circuit before causing any trouble in goodPassword
        if (clearPassword != null) {
            goodPassword(clearPassword);
            this.clearPassword = clearPassword;
            setEncryptedPassword(clearPassword);
        }
    }

    private void setEncryptedPassword(String clearPassword) {
        this.encryptedPassword = encryptPassword(clearPassword);
    }

    public void setKey(String key) {
        this.key = key;

        // Check to see if the password has been set yet
        if (this.clearPassword != null)
            setEncryptedPassword(this.clearPassword);
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
        return String.format(toStringFormat, this.encryptedPassword,
                this.clearPassword, this.clientId);
    }

}