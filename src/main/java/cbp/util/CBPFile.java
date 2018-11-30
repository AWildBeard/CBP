package cbp.util;

import cbp.clients.Bot;
import cbp.clients.Client;
import cbp.clients.User;

import java.io.File;
import java.io.IOException;
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

    private static boolean verifyLine(String line) {
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

    private static boolean isUser(String line) { return line.charAt(0) == 'u'; }

    private static boolean isBot(String line) { return line.charAt(0) == 'b'; }
    // File format "u,"mmitc","Michael Mitchell","P#ssw0rd","secret",10
    // File format "b,"ReeBoi.py","Michael Mitchell","P#ssw0rd","secret","IDS","01/01/1970"

    /**
     * This function will return the name field for both a Bot and a User
     * @param line
     * @return
     */
    private static String getName(String line) {
        int firstComma = line.indexOf(',');
        return line.substring(firstComma + 2, line.indexOf(',', firstComma + 1) - 1);

    }

    /**
     * This function will return the Full Name for a User or the Author field for a Bot
     * @param line
     * @return
     */
    private static String getFullName(String line) {
        // #lazy
        int firstComma = line.indexOf(',', line.indexOf(',') + 1);
        return line.substring(firstComma + 2, line.indexOf(',', firstComma + 1) - 1);
    }

    /**
     * This function will return the clear text password for a Bot or a User
     * @param line
     * @return
     */
    private static String getClearPassword(String line) {
        // #lazy
        int firstComma = line.indexOf(',', line.indexOf(',', line.indexOf(',') + 1) + 1);
        return line.substring(firstComma + 2 , line.indexOf(',', firstComma + 1) - 1);
    }

    /**
     * This function will return the key for a Bot or a User
     * @param line
     * @return
     */
    private static String getKey(String line) {
        // #lazy
        int firstComma = line.indexOf(',',
                line.indexOf(',',
                        line.indexOf(',',
                                line.indexOf(',') + 1) + 1) + 1);
        return line.substring(firstComma + 2, line.indexOf(',', firstComma + 1) - 1);
    }

    /**
     * This function will only return the department code for a User
     * @param line
     * @return
     */
    private static int getDeptCode(String line) {
        int firstComma = line.indexOf(',',
                line.indexOf(',',
                        line.indexOf(',',
                                line.indexOf(',',
                                        line.indexOf(',') + 1) + 1) + 1) + 1);
        return Integer.valueOf(line.substring(firstComma + 1));

    }

    /**
     * This function will return the category field for a Bot entry
     * @param line
     * @return
     */
    private static String getCategory(String line) {
        int firstComma = line.indexOf(',',
                line.indexOf(',',
                        line.indexOf(',',
                                line.indexOf(',',
                                        line.indexOf(',') + 1) + 1) + 1) + 1);
        return line.substring(firstComma + 2, line.indexOf(',', firstComma + 1) - 1);
    }

    /**
     * This function will return the creation date field for a Bot entry
     * @param line
     * @return
     */
    private static String getDate(String line) {
        int firstComma = line.indexOf(',',
                line.indexOf(',',
                        line.indexOf(',',
                                line.indexOf(',',
                                        line.indexOf(',',
                                                line.indexOf(',') + 1) + 1) + 1) + 1) + 1);
        return line.substring(firstComma + 2, line.length() - 1);
    }

    /**
     *
     * @return
     * @throws IOException If there was a problem opening the file
     * @throws RuntimeException If there was a problem with the contents of the files entries
     */
    public Stream<Option<Client>> clients() throws IOException {
        return Files.lines(FileSystems.getDefault().getPath(this.getPath())).map(CBPFile::parseLine);
    }

    static private Option<Client> parseLine(String line) {
        if (!verifyLine(line))
            return new Option<>(null);
        else {
            try {
                if (isUser(line)) {
                    return new Option<>(new User(getClearPassword(line), getKey(line),
                            getName(line), getFullName(line), getDeptCode(line)));
                } else {
                    return new Option<>(new Bot(getClearPassword(line), getKey(line),
                            getName(line), getCategory(line), getFullName(line), getDate(line)));
                }
            } catch(Exception e) {
                // Don't throw exceptions because we want to parse the whole file, not stop early.
                e.printStackTrace();
                System.out.println(e.getMessage());
                return new Option<>(null);
            }
        }

    }
}
