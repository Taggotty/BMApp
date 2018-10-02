import core.Ownership;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OwnershipTest {

    @Test
    public void createOwnershipTest() {
        // when
        Ownership ownership = new Ownership();

        // then
        assertEquals(Ownership.State.UNOWNED, ownership.getState());
        assertNull(ownership.getLend());
    }
}
