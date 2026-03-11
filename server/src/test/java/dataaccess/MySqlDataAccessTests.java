package dataaccess;

import models.AuthData;
import org.junit.jupiter.api.*;
import server.Server;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MySqlDataAccessTests {

    private static MySqlDataAccess dataAccess;


    @BeforeAll
    public static void initializeSQL() {
        try {
            dataAccess = new MySqlDataAccess();
        } catch (Exception ex){
            throw new RuntimeException(ex.toString());
        }
    }


    @BeforeEach
    public void clearAll() {
        try {
            dataAccess.clear();
        } catch (Exception ex){
            throw new RuntimeException(ex.toString());
        }
    }


    @Test
    @DisplayName("Add AuthToken object to Auth database")
    public void addAuth() {
        var auth = new AuthData("random username","abcd");
        assertDoesNotThrow(() -> dataAccess.create(auth));
    }

    @Test
    @DisplayName("Reject null object insert into AuthToken database")
    public void rejectNullAuth(){
        assertThrows(DataAccessException.class, ()->{
            String token = null;
            var auth = new AuthData("user",token);
            dataAccess.create(auth);
        }, "Database shouldn't accept NULL as an AuthToken");
    }


    @Test
    @DisplayName("find saved authtoken object")
    public void findAuth(){
        var auth = new AuthData("random username","abcd");
        try{
            dataAccess.create(auth);
            AuthData data = dataAccess.findByAuth("abcd");
            Assertions.assertTrue(data.userName().equals("random username"));
        }catch (Exception e){
            throw new RuntimeException(e);}
    }


    @Test
    @DisplayName("search for authtoken not in table")
    public void invalidAuthSearch(){
        var auth = new AuthData("random username","abcd");
        try{
            AuthData data = dataAccess.findByAuth("abcd");
            Assertions.assertNull(data);
        }catch (Exception e){
            throw new RuntimeException(e);}
    }








}
