import clients.Bot;
import clients.exceptions.InvalidPasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BotTest {
    private static Bot bottyBoy;

    public BotTest() {
        try {
            bottyBoy = new Bot("Imm4B#ta", "hijk",
                    "ReeBot.py", "INeedToDoTHis",
                    "Michael Mitchell", "01/04/1970");
        } catch (InvalidPasswordException invalidPassword) {
            // Do nothing
        }
    }

    @Test
    public void testDateIsCorrect() {
        assertEquals("1/4/1970", bottyBoy.getDate());
    }
}
