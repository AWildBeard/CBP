import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Optional;
import java.util.stream.IntStream;

public class Accounts {
    private String companyName,
        companyAddress;

    private static final int NOTFOUND = -1;

    private ArrayList<Client> clients;

    public Accounts() {
        setCompanyName(null);
        setCompanyAddress(null);
        clients = null;
    }

    public Accounts(String companyName, String companyAddress) {
        setCompanyName(companyName);
        setCompanyAddress(companyAddress);
        this.clients = null;
    }

    public Accounts(String companyName, String companyAddress, Client... clients) {
        setCompanyName(companyName);
        setCompanyAddress(companyAddress);
        this.clients = new ArrayList<>(Arrays.asList(clients));
    }

    public Accounts(String companyName, String companyAddress, Stream<Client> clients) {
        setCompanyName(companyName);
        setCompanyAddress(companyAddress);
        this.clients = new ArrayList<>(clients.collect(Collectors.toList()));
    }

    public Accounts(String companyName, String companyAddress, ArrayList<Client> clients) {
        setCompanyName(companyName);
        setCompanyAddress(companyAddress);
        this.clients = clients;
    }

    // Utility
    private boolean clientIsAUser(Client client) {
        return client instanceof User;
    }

    private boolean clientIsABot(Client client) {
        return client instanceof Bot;
    }

    /**
     * @param userName The username of the Client to find the index for
     * @return the index of that client if found. NOTFOUND otherwise.
     */
    private int getIndex(final String userName) { // I am determined not to write a for loop
        // Generate an intstream that iterates from 0 to the number of clients we have...
        // I just really don't want to write a for loop today :D
        OptionalInt index = IntStream.range(0, this.clients.size()).filter(num -> {
                // Gonna need this in a bit
                final Client client = this.clients.get(num);

                // Proven we have a User to call getUserName() on
                return clientIsAUser(client) && ((User) client).getUserName().equals(userName);
            }).findFirst();

        if (index.isPresent())
            // Awww this casts if for us! ^.^
            return index.getAsInt();

        return NOTFOUND;
    }

    // Mutators
    public void setCompanyName(String newName) {
        this.companyName = newName;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    // Does Prof Pinto mean User or Client?
    public void addClient(Client client) {
        this.clients.add(client);
    }

    /**
     * @param userName the Username of the user to delete
     * @return True if the user was successfully deleted. False if the user wasn't
     */
    public boolean deleteUser(String userName) {
        int index;
        if ((index = getIndex(userName)) != NOTFOUND) {
            this.clients.remove(index);
            return true;
        }

        return false;
    }

    // Accessors
    public String getCompanyName() {
        return this.companyName;
    }

    public String getCompanyAddress() {
        return this.companyAddress;
    }

    /**
     * @param userName The username of the user to find
     * @return The user if the user was found. null otherwise.
     */
    public User getUser(final String userName) {
        Optional<Client> user = (this.clients.stream().filter(client -> {
            return clientIsAUser(client) && ((User) client).getUserName().equals(userName);
        }).findFirst());

        if (user.isPresent()) 
            // We already proved above that this is a User :D
            return (User) user.get();
        
        System.out.println(userName + " does not exist.");
        return null;
    }

    public String toString() {
        StringBuilder userString = new StringBuilder(),
            botString = new StringBuilder();

        userString.append("Username\tEncryPass\tClearPass\tKey\n");
        botString.append("FileName\tEncyPass\tClearPass\tKey\n");
        // Fine I'll use a for loop gosh!
        for (final Client client : this.clients) {
            if (clientIsAUser(client)) {
                final User user = (User) client;
                userString.append(user.getUserName() + "\t" +
                        user.getEncryptedPassword() + "\t" + 
                        user.getClearPassword() + "\t" + 
                        user.getKey());
            } else {
                final Bot bot = (Bot) client;
                botString.append(bot.getBotFileName() + "\t" +
                        bot.getEncryptedPassword() + "\t" +
                        bot.getClearPassword() + "\t" + 
                        bot.getKey());
            }
        }

        return userString + "\n\n" + botString;
    }
}