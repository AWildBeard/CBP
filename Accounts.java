import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Accounts {
    private String companyName,
        companyAddress;

    private Stream<Client> clients;

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
        this.clients = Stream.of(clients);
    }

    public Accounts(String companyName, String companyAddress, Stream<Client> clients) {
        setCompanyName(companyName);
        setCompanyAddress(companyAddress);
        this.clients = clients;
    }

    public Accounts(String companyName, String companyAddress, ArrayList<Client> clients) {
        setCompanyName(companyName);
        setCompanyAddress(companyAddress);
        this.clients = clients.stream();
    }

    // Utility

    // Mutators
    public void setCompanyName(String newName) {
        this.companyName = companyName;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    // Does Prof Pinto mean User or Client?
    public void addUser(Client client) {
        Stream.concat(this.clients, Stream.of(client));
    }

    // Accessors
    public String getCompanyName() {
        return this.companyName;
    }

    public String getCompanyAddress() {
        return this.companyAddress;
    }

    public User getUser(final String userName) {
        return (User) (clients.filter(client -> {
            if (client instanceof User) {
                if (((User) client).getUserName() == userName)
                    return true;
            }
            return false;
        }).findFirst().get());
    }

    public String toString() {
        return "";
    }
}