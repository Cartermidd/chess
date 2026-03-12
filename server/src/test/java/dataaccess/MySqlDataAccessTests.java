package dataaccess;

import chess.ChessGame;
import models.AuthData;
import models.GameData;
import models.UserData;
import org.junit.jupiter.api.*;
import results.CreateGameResult;
import results.ListGamesResult;
import server.Server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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

 //Game tests
     @Test
     @DisplayName("Create Game")
     public void createGame(){
         assertDoesNotThrow(() -> dataAccess.createGame("this game"));
     }

    @Test
    @DisplayName("Fail to register user")
    public void createGameFail(){
        assertThrows(DataAccessException.class, ()->{
            String user = null;
            dataAccess.createGame(user);
        }, "Database shouldn't accept NULL as a password");
    }

    @Test
    @DisplayName("find saved game object")
    public void findGame(){
        try{
            CreateGameResult game = dataAccess.createGame("user");
            int id = game.getGameID();
            GameData data = dataAccess.findByID(id);
            Assertions.assertTrue(data.gameName().equals("user"));
        }catch (Exception e){
            throw new RuntimeException(e);}
    }

    @Test
    @DisplayName("search for game not in table")
    public void invalidGameSearch(){
        try{
            GameData data = dataAccess.findByID(30);
            Assertions.assertNull(data);
        }catch (Exception e){
            throw new RuntimeException(e);}
    }

    @Test
    @DisplayName("update game test")
    public void updateBlackUser(){
        try{
            CreateGameResult game = dataAccess.createGame("user");
            int id = game.getGameID();
            GameData data = dataAccess.findByID(id);
            Assertions.assertTrue(data.gameName().equals("user"));
            dataAccess.updateGame(id, ChessGame.TeamColor.BLACK, "blackusername");
            GameData data2 = dataAccess.findByID(id);
            Assertions.assertTrue(data2.blackUsername().equals("blackusername"));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("update name to null")
    public void updateGameFail(){
        assertThrows(DataAccessException.class, ()->{
            CreateGameResult game = dataAccess.createGame("user");
            int id = game.getGameID();
            dataAccess.updateGame(id, ChessGame.TeamColor.BLACK, null);
        }, "Database shouldn't accept NULL as a username");
    }

    @Test
    @DisplayName("list all saved game objects")
    public void listGames(){
        try{
            CreateGameResult game = dataAccess.createGame("user");
            GameData data = dataAccess.findByID(game.getGameID());
            ListGamesResult games = dataAccess.listGames();
            Collection<GameData> gamesList = games.getGames();
            Assertions.assertTrue(gamesList.contains(data));
        }catch (Exception e){
            throw new RuntimeException(e);}
    }

    @Test
    @DisplayName("empty list shouldn't contain games")
    public void invalidListGames(){
        try{
            GameData data = new GameData(2, null, null, "string", new ChessGame());
            ListGamesResult games = dataAccess.listGames();
            Collection<GameData> gamesList = games.getGames();
            Assertions.assertFalse(gamesList.contains(data));
        }catch (Exception e){
            throw new RuntimeException(e);}
    }

    @Test
    @DisplayName("Clear test")
    public void clearTest(){
        try {
            dataAccess.clear();
        } catch (Exception ex){
            throw new RuntimeException(ex.toString());
        }
    }
}
