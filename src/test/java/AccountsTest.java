import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;
import java.util.NoSuchElementException;

public class AccountsTest {
    // @Rule
    // public ExpectedException exception = ExpectedException.none();

    @Test
    public void getUserReturnsCorrectUser() {
        User user1 = new User("P#ssw0rd", "abcd", "mmitc", "Michael Mitchell", 1),
            user2 = new User("Hash#t4g", "defg", "User2", "Blah Blah", 2);
        Bot bottyBoy = new Bot("Imm4B#t", "hijk", "ReeBot.py", "INeedToDoTHis", "Michael Mitchell", "01/01/1970");
        Accounts accounts = new Accounts("ACME", "1234 Lost Blvd", user1, user2, bottyBoy);
        assertEquals(user2, accounts.getUser("User2"));
        assertEquals(user1, accounts.getUser("mmitc"));
        assertTrue(accounts.deleteUser("mmitc"));
        assertFalse(accounts.deleteUser("mmitc"));
        assertNull(accounts.getUser("mmitc"));
        assertTrue(accounts.deleteUser("User2"));
        assertFalse(accounts.deleteUser("User2"));
        assertNull(accounts.getUser("User2"));

        // Test for exception handling
        // exception.expect(NoSuchElementException.class);
        assertNull(accounts.getUser("FAIL!"));
    }
}