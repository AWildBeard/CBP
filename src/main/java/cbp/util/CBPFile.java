package cbp.util;

import cbp.clients.Bot;
import cbp.clients.Client;
import cbp.clients.User;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.stream.Stream;

public class CBPFile extends File {

    private enum Quote {
        FIRST,
        SECOND,
        NONE;

        static Quote getInstance(int n) {
            if (n == 1)
                return FIRST;
            else if (n == 2)
                return SECOND;
            else
                return NONE;
        }

        Quote next() { return next(this); }

        static Quote next(Quote x) {
            if (x == Quote.SECOND) {
                return NONE;
            } else if (x == Quote.FIRST) {
                return SECOND;
            } else {
                return FIRST;
            }
        }
    }

    public CBPFile(String path) {
        super(path);
    }

    private boolean verifyLine(String line) {
        if (! isUser(line) && ! isBot(line))
            return false;

        char[] characters = line.toCharArray();
        Quote quoteCounter = Quote.NONE;
        for (int index = 0 ; index < characters.length ; index++) {
            char ch = characters[index];
            if (ch == '"') {

                quoteCounter = quoteCounter.next();

                // Quote counter is SECOND by this point;
                if (quoteCounter == Quote.SECOND && index + 1 < characters.length - 1 && characters[index + 1] == ',') {
                    quoteCounter = quoteCounter.next(); // Go back to none.
                    continue;
                } else if (quoteCounter == Quote.SECOND){
                    return (index + 1 > characters.length - 1); // True if we are at the end of the line, else false
                }
            }

            if (ch == ' ' && quoteCounter == Quote.NONE) {
                return false;
            }
        }
        return true;
    }

    private boolean isUser(String line) { return line.charAt(0) == 'u'; }

    private boolean isBot(String line) { return line.charAt(0) == 'b'; }
    // File format "u,"mmitc","Michael Mitchell","P#ssw0rd","secret",10
    // File format "b,"ReeBoi.py","Michael Mitchell","P#ssw0rd","secret","IDS","01/01/1970"

    /**
     * This function will return the name field for both a Bot and a User
     * @param line
     * @return
     */
    private String getName(String line) {
        int firstComma = line.indexOf(',');
        return line.substring(firstComma + 2, line.indexOf(',', firstComma + 1) - 1);

    }

    /**
     * This function will return the Full Name for a User or the Author field for a Bot
     * @param line
     * @return
     */
    private String getFullName(String line) {
        int firstComma = line.indexOf(',', line.indexOf(',') + 1);
        return line.substring(firstComma + 2, line.indexOf(',', firstComma + 1) - 1);
    }

    /**
     * This function will return the clear text password for a Bot or a User
     * @param line
     * @return
     */
    private String getClearPassword(String line) {
        return "UNIMPLEMENTED";
    }

    /**
     * This function will return the key for a Bot or a User
     * @param line
     * @return
     */
    private String getKey(String line) {
        return "UNIMPLEMENTED";
    }

    /**
     * This function will only return the department code for a User
     * @param line
     * @return
     */
    private int getDeptCode(String line) {
        return 0;
    }

    /**
     * This function will return the creation date field for a Bot entry
     * @param line
     * @return
     */
    private String getDate(String line) {
        return "UNIMPLEMENTED";
    }

    /**
     * This function will return the category field for a Bot entry
     * @param line
     * @return
     */
    private String getCategory(String line) {
        return "UNIMPLEMENTED";
    }

    /**
     *
     * @return
     * @throws IOException If there was a problem opening the file
     * @throws RuntimeException If there was a problem with the contents of the files entries
     */
    public Stream<Client> clients() throws IOException, RuntimeException {
        return Files.lines(FileSystems.getDefault().getPath(this.getPath())).map(line -> {
            if (!verifyLine(line))
                throw new RuntimeException(new IOException("Invalid line format for CBP File!"));
            else {
                try {
                    System.out.println(getName(line));
                    System.out.println(getFullName(line));
                    if (isUser(line))
                        return new User();
                    else
                        return new Bot();
                } catch(Exception e) {
                    // Don't throw checked exceptions because we want to parse the whole file, not stop early.
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
