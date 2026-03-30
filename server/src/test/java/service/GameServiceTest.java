package service;

import chess.ChessGame;
import dataaccess.MemoryDataAccess;
import exceptions.RegisterRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import results.CreateGameResult;
import results.LoginResult;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameServiceTest {

    private static GameService gameService;
    private static ClearService clearService;
    private static UserService userService;
    private static MemoryDataAccess dao;

    @BeforeAll
    public static void setup() {
        try {
            dao = new MemoryDataAccess();
            gameService = new GameService(dao, dao);
            clearService = new ClearService(dao, dao, dao);
            userService = new UserService(dao, dao);
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @BeforeEach
    public void clearDB() {
        try {
            clearService.clearDB();
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }


    @Test
    public void listGamesPositive() {
        try {
            assertDoesNotThrow(() -> {
                LoginResult result = userService.register(new RegisterRequest("username", "password", "email"));
                String authToken = result.getAuthToken();
                gameService.listGames(authToken);
            });
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @Test
    public void listGamesNegative() {
        assertThrows(Exception.class, () -> {
            String fakeAuth = "thisShouldntWork";
            gameService.listGames(fakeAuth);
        });
    }

    @Test
    public void createGamePositive() {
        try {
            assertDoesNotThrow(() -> {
                LoginResult result = userService.register(new RegisterRequest("username", "password", "email"));
                String authToken = result.getAuthToken();
                gameService.createGame(authToken, new CreateGameRequest("gameName"));
            });
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }

    }

    @Test
    public void createGameNegative() {
        assertThrows(Exception.class, () -> {
            String fakeAuth = "thisShouldntWork";
            gameService.createGame(fakeAuth, new CreateGameRequest("gameName"));
        });
    }

    @Test
    public void joinGamePositive() {
        try {
            assertDoesNotThrow(() -> {
                LoginResult result = userService.register(new RegisterRequest("username", "password", "email"));
                String authToken = result.getAuthToken();
                CreateGameResult gameResult = gameService.createGame(authToken, new CreateGameRequest("gameName"));
                gameService.joinGame(authToken, new JoinGameRequest(ChessGame.TeamColor.BLACK, gameResult.getGameID()));
            });
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @Test
    public void joinGameNegative() {
        assertThrows(Exception.class, () -> {
            LoginResult result = userService.register(new RegisterRequest("username", "password", "email"));
            String authToken = result.getAuthToken();
            gameService.joinGame(authToken, new JoinGameRequest(ChessGame.TeamColor.BLACK, 1));
        });
    }


}
