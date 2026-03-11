package dataaccess;

import models.AuthData;
import org.junit.jupiter.api.*;
import server.Server;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MySqlDataAccessTests {

    private static MySqlDataAccess mySqlDataAccess;


    @BeforeAll
    public static void initializeSQL() {
        try {
            mySqlDataAccess = new MySqlDataAccess();
        } catch (Exception ex){
            throw new RuntimeException(ex.toString());
        }
    }


    @BeforeEach
    public void clearAll() {
        try {
            mySqlDataAccess.clear();
        } catch (Exception ex){
            throw new RuntimeException(ex.toString());
        }
    }


    @Test
    @DisplayName("Add AuthToken object to Auth database")
    public void addAuth() {
        var auth = new AuthData("random username","");

        assertDoesNotThrow(() -> dataAccess.addAuth())
    }

    @Test
    @DisplayName("Reject null object insert into AuthToken database")
    public void rejectNullAuth(){



    }

}
