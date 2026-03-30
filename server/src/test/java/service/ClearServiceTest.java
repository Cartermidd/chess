package service;

import dataaccess.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClearServiceTest {

    private ClearService service;
    private MemoryDataAccess dao;

    @BeforeEach
    public void setup() {
        dao = new MemoryDataAccess();
        service = new ClearService(dao, dao, dao);
    }

    @Test
    public void clearPositive() {
        try {
            assertDoesNotThrow(()->service.clearDB());
        } catch (Exception ex){
            throw new RuntimeException(ex.toString());
        }
    }

    @Test
    public void clearNegative() {
        assertThrows(Exception.class, ()->{
            service = new ClearService(null, null, null);
            service.clearDB();
        }, "Throw an error when you try to clear using null DAO");
    }

}
