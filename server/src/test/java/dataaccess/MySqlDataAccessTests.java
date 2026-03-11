package dataaccess;

import models.AuthData;
import models.UserData;
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

    // AUTHTOKEN TESTS

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


    @Test
    @DisplayName("delete auth token from table")
    public void deleteAuth(){
        var auth = new AuthData("random username","abcd");
        try{
            dataAccess.create(auth);
            AuthData data = dataAccess.findByAuth("abcd");
            Assertions.assertTrue(data.userName().equals("random username"));
            dataAccess.deleteAuth("abcd");
            AuthData newData = dataAccess.findByAuth("abcd");
            Assertions.assertNull(newData);
        }catch (Exception e){
            throw new RuntimeException(e);}
    }

    @Test
    @DisplayName("fail to delete")
    public void failDeleteAuth(){
        assertThrows(DataAccessException.class, ()->{
            dataAccess.deleteAuth("abcd");
        }, "Database should throw error when you try to delete a non-existent AuthToken");
    }

    //USER TESTS

    @Test
    @DisplayName("Register User")
    public void registerUser(){
        var user = new UserData("random username","abcd", "this is not an email");
        assertDoesNotThrow(() -> dataAccess.create(user));
    }

    @Test
    @DisplayName("Fail to register user")
    public void registerFail(){
        assertThrows(DataAccessException.class, ()->{
            String token = null;
            var user = new UserData("user",token,"email");
            dataAccess.create(user);
        }, "Database shouldn't accept NULL as a password");
    }

    @Test
    @DisplayName("find saved user object")
    public void findUser(){
        var user = new UserData("user","password","email");
        try{
            dataAccess.create(user);
            UserData data = dataAccess.findByUsername("user");
            Assertions.assertTrue(data.email().equals("email"));
        }catch (Exception e){
            throw new RuntimeException(e);}
    }


    @Test
    @DisplayName("search for user not in table")
    public void invalidUserSearch(){
        try{
            UserData data = dataAccess.findByUsername("abcd");
            Assertions.assertNull(data);
        }catch (Exception e){
            throw new RuntimeException(e);}
    }




}
