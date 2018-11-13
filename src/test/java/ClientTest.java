import clients.Client;
import clients.exceptions.InvalidPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    private static Client cli1, cli2;

    public ClientTest() {
        try {
            cli1 = new Client("P#ssw0rd", "abcde");
            cli2 = new Client("Hell0W#rld", "abcde");
        } catch (InvalidPasswordException invalidPassword) {
            // Do nothing
        }
    }

    @BeforeEach
    public void prepare() {
        cli1.setKey("abcde");
        cli2.setKey("abcde");
    }

    @Test
    public void checkPasswordEncodingCollisionsDueToKey() {
        StringBuilder keyBuilder = new StringBuilder();
        for (int first = 0, second = 1, third = 2,
             fourth = 3, fith = 4; fith <= 255 ; first++, second++, third++,
                fourth++, fith++) {

            keyBuilder.append(new char[] {(char) first, (char) second,
                    (char) third, (char) fourth, (char) fith});

            cli1.setKey(keyBuilder.toString());
            cli2.setKey(keyBuilder.toString());

            assertNotEquals(cli1.getClearPassword(), cli1.getEncryptedPassword());
            assertNotEquals(cli2.getClearPassword(), cli2.getEncryptedPassword());

            keyBuilder = new StringBuilder();
        }
    }

    @Test
    public void checkGenericEncoding() {
        // Check to make sure that different passwords with the same key do not end up
        // having the same encoded values
        assertNotEquals(cli1.getEncryptedPassword(), cli2.getClearPassword());
    }

    @Test
    public void checkSpecialKeyPasswordRotationOk() {
        cli1.setKey("defgh");
        cli2.setKey("defgh");

        // Check to make sure that the special case of a key being defgh
        assertNotEquals(cli1.getClearPassword(), cli1.getEncryptedPassword());
        assertNotEquals(cli2.getClearPassword(), cli2.getEncryptedPassword());
    }

    @Test
    public void throwsExceptionOnInvalidPassword() {
        assertThrows(InvalidPasswordException.class, () -> cli1.setClearPassword("Imm4B#t"));
    }
}
