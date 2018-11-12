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
        setCompanyName("");
        setCompanyAddress("");
        clients = new ArrayList<>();
    }

    public Accounts(String companyName, String companyAddress) {
        setCompanyName(companyName);
        setCompanyAddress(companyAddress);
        this.clients = new ArrayList<>();
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

    public boolean hasClient(final String name) {
        return getClient(name) != null;
    }

    /**
     * @param name The username of the Client to find the index for
     * @return the index of that client if found. NOTFOUND otherwise.
     */
    private int getIndex(final String name) { // I am determined not to write a for loop
        // Generate an intstream that iterates from 0 to the number of clients we have...
        // I just really don't want to write a for loop today :D
        OptionalInt index = IntStream.range(0, this.clients.size()).filter(num -> {
                final Client client = this.clients.get(num);

                // Proven we have a User to call getUserName() on
                return clientIsAUser(client) && ((User) client).getUserName().equals(name) ||
                        clientIsABot(client) && ((Bot) client).getBotFileName().equals(name);
            }).findAny();

        if (index.isPresent())
            // Awww this casts it for us! ^.^
            return index.getAsInt();

        return NOTFOUND;
    }

    // Does Prof Pinto mean User or Client?
    public void addClient(Client client) {
        this.clients.add(client);
    }

    /**
     * @param name the Username of the user to delete
     * @return True if the user was successfully deleted. False if the user wasn't
     */
    public boolean deleteClient(String name) {
        int index;
        if ((index = getIndex(name)) != NOTFOUND) {
            this.clients.remove(index);
            return true;
        }

        return false;
    }

    // Mutators
    public void setCompanyName(String newName) {
        this.companyName = newName;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    // Accessors
    public String getCompanyName() {
        return this.companyName;
    }

    public String getCompanyAddress() {
        return this.companyAddress;
    }

    /**
     * @param name The username of the user to find
     * @return The user if the user was found. null otherwise.
     */
    public Client getClient(final String name) {
        // Names are unique so it doesn't matter how we do the comparison
        Optional<Client> client = (this.clients.stream().filter(cli ->
                clientIsAUser(cli) && ((User) cli).getUserName().equals(name) ||
                        clientIsABot(cli) && ((Bot) cli).getBotFileName().equals(name)
        ).findAny());

        if (client.isPresent())
            // We already proved above that this is a User :D
            return client.get();
        
        System.out.println(name + " does not exist.");
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