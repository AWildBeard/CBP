package cbp.clients;
import cbp.clients.exceptions.InvalidPasswordException;

import java.lang.StringBuilder;
import java.util.Arrays;
import java.util.List;

/**
 * @author Michael Mitchell
 */
public class Client {
    /**
     * The clear, encrypted, and key data fields
     */
    private String clearPassword = null,
        encryptedPassword = null,
        key = null;

    /**
     * The ID of the client
     */
    private int clientId;

    /**
     * Static iterating number to assign unique ID's to new Clients
     */
    private static int nextIDNum = 1000;

    private static final int upperAsciiTableBound = 255;

    /**
     * A list of valid special characters for the password. Characters that are special characters that are not
     * in this list will be considered invalid.
     */
    private static final List<Character> validSpecialCharacters = 
            Arrays.asList('#', '$', '%', '&');

    // Expose how this class reports its toString data
    /**
     * Constant to control how far apart each data item is from the other
     */
    public static final int maxFieldLength = 25;

    /**
     * Public headers for the toString method.
     */
    public static final String toStringHeaders[] = {"Enc Pass", "Clear Pass", "Client ID"};
    public static final String toStringFormat = "%-" + maxFieldLength + "s\t%-" +
            maxFieldLength + "s\t%-" + maxFieldLength + "s";

    /**
     * Default constructor
     */
    public Client() {
        this.clearPassword = "P#ssw0rd";
        setKey("abcd");
        setClientId();
    }

    /**
     * Paramerterized constructor to create an custom Client.
     * @throws InvalidPasswordException For if the password does not meet the password requirements
     */
    public Client(String clearPassword, String key) throws InvalidPasswordException {
        setKey(key);
        setClearPassword(clearPassword); // Depends on key
        setClientId();
    }

    // Utility
    /**
     * @param clearPassword The password to see if it meets the password requirment
     * @throws InvalidPasswordException if the password does not meet the requirement
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

    /**
     * Private internal method to generate the offset number for the characters in the clear password
     * @return The number to rotate the characters by
     */
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

    /**
     * Public mutator to set the password for the client
     * @param clearPassword The clear text password to set for the client
     * @throws InvalidPasswordException For if the password does not meet the password requirements
     * @see Client#goodPassword(String) For a better description of what the password requirements are.
     */
    public void setClearPassword(String clearPassword) throws InvalidPasswordException {
        // Will short circuit before causing any trouble in goodPassword
        if (clearPassword != null) {
            goodPassword(clearPassword);
            this.clearPassword = clearPassword;
            setEncryptedPassword(clearPassword);
        }
    }

    /**
     * Internal mutator to set the encrypted password
     * @param clearPassword The <i>clear text</i> password
     */
    private void setEncryptedPassword(String clearPassword) {
        this.encryptedPassword = encryptPassword(clearPassword);
    }

    /**
     * The key to derive the symmetric rotation number from. This allows generating a symmetric encryption for
     * passwords.
     * @param key The key to derive the rotation number from
     * @see Client#generateRotationNumber()
     */
    public void setKey(String key) {
        this.key = key;

        // Check to see if the password has been set yet
        if (this.clearPassword != null)
            setEncryptedPassword(this.clearPassword);
    }

    /**
     * Public mutator to re-generate the clients ID
     */
    public void setClientId() {
        this.clientId = nextIDNum++;
    }

    /**
     * Public accessor to get the clear text password for the Client
     * @return The clear text password for the client
     */
    public String getClearPassword() {
        return this.clearPassword;
    }

    /**
     * Public accessor to get the encrypted password for the Client
     * @return The encrypted password for the client
     */
    public String getEncryptedPassword() {
        return this.encryptedPassword;
    }

    /**
     * Public accessor to get the key for the symmetric encryption
     * @return The plaintext key used to create the symmetric encryption
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Public accessor to get the current clientID
     * @return The ID number for the Client
     */
    public int getClientId() {
        return this.clientId;
    }

    /**
     * toString method to convert the Client to a String
     * @return The String representation of the Client
     */
    public String toString() {
        return String.format(toStringFormat, this.encryptedPassword,
                this.clearPassword, this.clientId);
    }

}