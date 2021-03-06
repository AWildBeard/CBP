import cbp.clients.Accounts;
import cbp.clients.Bot;
import cbp.clients.User;
import cbp.clients.exceptions.InvalidBotCategoryException;
import cbp.clients.exceptions.InvalidPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountsTest {
    // @Rule
    // public ExpectedException exception = ExpectedException.none();

    private static User user1, user2;

    private static Bot bottyBoy;

    private static Accounts accounts = new Accounts("ACME",
            "1234 Lost Blvd");

    public AccountsTest() {
        try {
            bottyBoy = new Bot("Imm4B#ta", "hijk",
                    "ReeBot.py", "IDS",
                    "Michael Mitchell", "01/01/1970");

            user1 = new User("P#ssw0rd", "abcd",
            "mmitc", "Michael Mitchell", 1);

            user2 = new User("Hash#t4g", "defg",
                    "User2", "Blah Blah", 2);
        } catch (InvalidPasswordException | InvalidBotCategoryException e) {
            // Do nothing
        }

    }

    @BeforeEach
    public void prepare() {
        if (! accounts.hasClient(user1.getUserName()) &&
                ! accounts.hasClient(user2.getUserName()) &&
                ! accounts.hasClient(bottyBoy.getBotFileName())) {
            accounts.addClient(user1);
            accounts.addClient(user2);
            accounts.addClient(bottyBoy);
        }
    }

    @Test
    public void getClientSuccessful() {
        // Find those users
        assertEquals(user1, accounts.getClient(user1.getUserName()));
        assertEquals(user2, accounts.getClient(user2.getUserName()));
        assertEquals(bottyBoy, accounts.getClient(bottyBoy.getBotFileName()));

        // Remove the cbp.clients to test the null return feature of getValue()
        deleteClientSuccessful();

        // Test that the user can't be found
        assertNull(accounts.getClient(user1.getUserName()));
        assertNull(accounts.getClient(user2.getUserName()));
        assertNull(accounts.getClient(bottyBoy.getBotFileName()));

        // Test for a user that never existed
        assertNull(accounts.getClient("FAIL!"));
    }

    @Test
    public void deleteClientSuccessful() {
        // Test deleting users
        assertTrue(accounts.deleteClient(user1.getUserName()));
        assertTrue(accounts.deleteClient(user2.getUserName()));
        assertTrue(accounts.deleteClient(bottyBoy.getBotFileName()));

        // Test that the user was deleted
        assertFalse(accounts.deleteClient(user1.getUserName()));
        assertFalse(accounts.deleteClient(user2.getUserName()));
        assertFalse(accounts.deleteClient(bottyBoy.getBotFileName()));

    }
}