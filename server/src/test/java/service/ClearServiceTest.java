package service;

import dataaccess.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClearServiceTest {

    private static ClearService service;
    private MemoryDataAccess dao;

    @BeforeEach
    public void setup() {
        dao = new MemoryDataAccess();
        service = new ClearService(dao, dao, dao);
    }

    @AfterAll
    public static void finalClearDB() {
        try {
            service.clearDB();
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
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
            ClearService nullService = new ClearService(null, null, null);
            nullService.clearDB();
        }, "Throw an error when you try to clear using null DAO");
    }

}
