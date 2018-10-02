import core.Ownership;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OwnershipTest {

    @Test
    public void createOwnershipTest() {
        // when
        Ownership ownership = new Ownership();

        // then
        assertEquals(Ownership.State.UNOWNED, ownership.getState());
        assertEquals("", ownership.getLend());
    }

    @Test
    public void setOwnershipLendFrom() {
        // given
        Ownership ownership = new Ownership();
        String lend = "Philipp";

        // when
        ownership.setState(Ownership.State.LEND, lend);

        // then
        assertEquals(ownership.getState(), Ownership.State.LEND);
        assertEquals(ownership.getLend(), lend);
    }
}
