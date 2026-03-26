package client;

import chess.ChessGame;
import exceptions.RegisterRequest;
import org.junit.jupiter.api.*;
import requests.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @BeforeEach
    void clearDB(){
        try{
            facade.clear();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }



    @Test
    void register() throws Exception {
        var result = facade.register(new RegisterRequest(new String[]{"player1", "password", "p1@email.com"}));
        assertNotNull(facade.getAuthToken());
        assertEquals("player1", result.getUsername());
    }

    @Test
    void doubleRegister() throws Exception{
        assertThrows(Exception.class, () -> {
            facade.register(new RegisterRequest(new String[]{"player1", "password", "p1@email.com"}));
            facade.register(new RegisterRequest(new String[]{"player1", "password", "p1@email.com"}));
        });
    }


    @Test
    void login() throws Exception {
        var result = facade.register(new RegisterRequest(new String[]{"player1", "password", "p1@email.com"}));
        facade.logout(new AuthorizedRequest(result.getAuthToken()));
        var loginResult = facade.login(new LoginRequest(new String[]{"player1", "password"}));
        assertNotNull(facade.getAuthToken());
        assertEquals("player1", result.getUsername());
    }

    @Test
    void incorrectLogin() throws Exception{
        assertThrows(Exception.class, () -> {
            var result = facade.register(new RegisterRequest(new String[]{"player1", "password", "p1@email.com"}));
            facade.logout(new AuthorizedRequest(result.getAuthToken()));
            facade.login(new LoginRequest(new String[]{"player1", "badpassword"}));
        });
    }


    @Test
    void logout() throws Exception {
        var result = facade.register(new RegisterRequest(new String[]{"player1", "password", "p1@email.com"}));
        facade.logout(new AuthorizedRequest(result.getAuthToken()));
        assertNull(facade.getAuthToken());
    }

    @Test
    void logoutFail() throws Exception{
        assertThrows(Exception.class, () -> {
            facade.logout(new AuthorizedRequest("whoops"));
        });
    }

    @Test
    void listGames() throws Exception {
        var result = facade.register(new RegisterRequest(new String[]{"player1", "password", "p1@email.com"}));
        var listGamesResult = facade.listGames(new AuthorizedRequest(result.getAuthToken()));
        assertNotNull(listGamesResult);
    }

    @Test
    void listGamesFail() throws Exception{
        assertThrows(Exception.class, () -> {
            facade.listGames(new AuthorizedRequest("whoops"));
        });
    }


    @Test
    void createGame() throws Exception {
        var result = facade.register(new RegisterRequest(new String[]{"player1", "password", "p1@email.com"}));
        var createGameResult = facade.createGame(new CreateGameRequest("gameName"));
        assertNotNull(createGameResult.getGameID());
    }

    @Test
    void createGameFail() throws Exception{
        assertThrows(Exception.class, () -> {
            facade.createGame(new CreateGameRequest("gameName"));
        });
    }

    @Test
    void joinGame() throws Exception {
        assertDoesNotThrow(() ->{
            var result = facade.register(new RegisterRequest(new String[]{"player1", "password", "p1@email.com"}));
            var createGameResult = facade.createGame(new CreateGameRequest("gameName"));
            facade.joinGame(new JoinGameRequest(ChessGame.TeamColor.BLACK, 1));
            facade.joinGame(new JoinGameRequest(ChessGame.TeamColor.WHITE, 1));
        }
        );
    }

    @Test
    void joinGameFail() throws Exception{
        assertThrows(Exception.class,() ->{
                    var result = facade.register(new RegisterRequest(new String[]{"player1", "password", "p1@email.com"}));
                    var createGameResult = facade.createGame(new CreateGameRequest("gameName"));
                    facade.joinGame(new JoinGameRequest(ChessGame.TeamColor.BLACK, 1));
                    facade.joinGame(new JoinGameRequest(ChessGame.TeamColor.BLACK, 1));
                }
        );
    }

}