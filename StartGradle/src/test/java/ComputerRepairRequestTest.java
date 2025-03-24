import model.ComputerRepairRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComputerRepairRequestTest {
    @Test
    @DisplayName("First Test")
    public void testExample()
    {
        ComputerRepairRequest crr = new ComputerRepairRequest();
        assertEquals("",crr.getOwnerName());
        assertEquals("",crr.getModel());
    }

    @Test
    @DisplayName("Second test")
    public void testExample2()
    {
        ComputerRepairRequest crr = new ComputerRepairRequest();
        assertEquals(2,2,"Numere ar trebui sa fie egale");
    }
      
}
