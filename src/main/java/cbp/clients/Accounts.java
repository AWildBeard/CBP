package cbp.clients;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @author Michael Mitchell
 */
public class Accounts {
    /**
     * Company name and Company address data
     */
    private String companyName,
        companyAddress;

    /**
     * Constant to indicate if a client was found
     */
    private static final int NOTFOUND = -1;

    /**
     * The inner data structure to hold the clients.
     */
    private ArrayList<Client> clients;

    /**
     * Default constructor
     */
    public Accounts() {
        setCompanyName("");
        setCompanyAddress("");
        clients = new ArrayList<>();
    }

    /**
     * Simple constructor to establish an empty accounts class
     * @param companyName The name of the company
     * @param companyAddress The address of the company
     */
    public Accounts(String companyName, String companyAddress) {
        setCompanyName(companyName);
        setCompanyAddress(companyAddress);
        this.clients = new ArrayList<>();
    }

    /**
     * Constructor that allows taking in a relatively unlimited number of Clients
     * @see cbp.clients.Client
     * @param companyName The name of the company
     * @param companyAddress The address of the company
     * @param clients The @see cbp.clients.Client to add as initial clients to the accounts object
     */
    public Accounts(String companyName, String companyAddress, Client... clients) {
        setCompanyName(companyName);
        setCompanyAddress(companyAddress);
        this.clients = new ArrayList<>(Arrays.asList(clients));
    }

    /**
     * Constructor that allows taking in a stream of clients to create add clients to the Accounts object
     * @see cbp.clients.Client
     * @see java.util.stream.Stream
     * @param companyName The name of the company
     * @param companyAddress The address of the company
     * @param clients A stream of clients
     */
    public Accounts(String companyName, String companyAddress, Stream<Client> clients) {
        setCompanyName(companyName);
        setCompanyAddress(companyAddress);
        this.clients = new ArrayList<>(clients.collect(Collectors.toList()));
    }

    /**
     * Constructor that allows taking in an already formed java.util.ArrayList to create the Accounts object
     * @see cbp.clients.Client
     * @see java.util.ArrayList
     * @param companyName The name of the company
     * @param companyAddress The address of the company
     * @param clients An already formed arraylist to base the Accounts class off of
     */
    public Accounts(String companyName, String companyAddress, ArrayList<Client> clients) {
        setCompanyName(companyName);
        setCompanyAddress(companyAddress);
        this.clients = clients;
    }

    /**
     * Private utility method to make code semantics better
     * @param client The client to test
     * @return True if the Client is a User
     */
    private boolean clientIsAUser(Client client) {
        return client instanceof User;
    }

    /**
     * Private utility method to make code semantics better
     * @param client The client to test
     * @return True if the Client is a Bot
     */
    private boolean clientIsABot(Client client) {
        return client instanceof Bot;
    }

    /**
     * Public accessor to used to determine if a Client is in the Accounts store
     * @param name The name of the Client to test for. This will be the file name field for a Bot
     * @return True if the Client was found. False otherwise.
     */
    public boolean hasClient(final String name) {
        return getClient(name) != null;
    }

    /**
     * @param name The username of the Client to find the index for
     * @return the index of that client if found. NOTFOUND otherwise.
     */
    private int getIndex(final String name) { // I am determined not to write a for loop
        // Generate an intstream that iterates from 0 to the number of cbp.clients we have...
        // I just really don't want to write a for loop today :D
        OptionalInt index = IntStream.range(0, this.clients.size()).filter(num -> {
                final Client client = this.clients.get(num);

                // Proven we have a cbp.clients.User to call getUserName() on
                return clientIsAUser(client) && ((User) client).getUserName().equals(name) ||
                        clientIsABot(client) && ((Bot) client).getBotFileName().equals(name);
            }).findAny();

        if (index.isPresent())
            // Awww this casts it for us! ^.^
            return index.getAsInt();

        return NOTFOUND;
    }

    /**
     * Add a client
     * @see cbp.clients.Client
     * @param client The client to add to the account store
     */
    public void addClient(Client client) {
        if (clientIsAUser(client) && !hasClient(((User)client).getUserName())) // Is the client a user?
            this.clients.add(client);

        else if (clientIsABot(client) && !hasClient(((Bot) client).getBotFileName())) // Client is a bot
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

    /**
     * Mutator to set the company name
     * @param newName The new name of the company
     */
    public void setCompanyName(String newName) {
        this.companyName = newName;
    }

    /**
     * Mutator to set the company Address
     * @param companyAddress The new company Address
     */
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    // Accessors

    /**
     * Accessor for the companies name
     * @return The string representation of the company
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * Public accessor for the companies Address
     * @return The string representation of the companies address
     */
    public String getCompanyAddress() {
        return this.companyAddress;
    }

    /**
     * @see cbp.clients.Client
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
            // We already proved above that this is a cbp.clients.User :D
            return client.get();
        
        System.out.println(name + " does not exist.");
        return null;
    }

    /**
     * @return The string representation of the accounts class. Includes Clients and Bots nicely formatted.
     */
    public String toString() {
        StringBuilder userString = new StringBuilder(),
            botString = new StringBuilder();

        userString.append(String.format(User.toStringFormat, (Object[]) User.toStringHeaders));
        userString.append(String.format(Client.toStringFormat, (Object[]) Client.toStringHeaders));
        userString.append("\n");

        botString.append(String.format(Bot.toStringFormat, (Object[]) Bot.toStringHeaders));
        botString.append(String.format(Client.toStringFormat, (Object[]) Client.toStringHeaders));
        botString.append("\n");

        // Fine I'll use a for loop gosh!
        for (final Client client : this.clients) {
            if (clientIsAUser(client)) {
                final User user = (User) client;
                userString.append(user.toString());
                userString.append("\n");
            } else {
                final Bot bot = (Bot) client;
                botString.append(bot.toString());
                botString.append("\n");
            }
        }

        return userString + "\n\n" + botString;
    }
}