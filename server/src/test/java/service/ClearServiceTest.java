package service;

import dataaccess.*;
import org.junit.jupiter.api.*;
import service.*;

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

    }

    @Test
    public void clearNegative() {

    }

}
