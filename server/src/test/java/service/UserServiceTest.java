package service;

import chess.ChessGame;
import dataaccess.MemoryDataAccess;
import exceptions.RegisterRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LoginRequest;
import results.CreateGameResult;
import results.LoginResult;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private static UserService userService;
    private static ClearService clearService;
    private static MemoryDataAccess dao;

    @BeforeAll
    public static void setup() {
        try {
            dao = new MemoryDataAccess();
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

    @AfterAll
    public static void finalClearDB() {
        try {
            clearService.clearDB();
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @Test
    public void registerPositive() {
        try {
            assertDoesNotThrow(() -> {
                LoginResult result = userService.register(new RegisterRequest("username", "password", "email"));
                assertNotNull(result.getAuthToken());
                assertEquals("username", result.getUsername());
            });
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @Test
    public void registerNegative() {
        assertThrows(Exception.class, () -> {
            userService.register(new RegisterRequest("user", "pass", null));
        });
    }

    @Test
    public void loginPositive() {
        try {
            assertDoesNotThrow(() -> {
                LoginResult regResult = userService.register(new RegisterRequest("username", "password", "email"));
                userService.logout(regResult.getAuthToken());
                LoginResult logResult = userService.login(new LoginRequest("username", "password"));

                assertNotNull(logResult.getAuthToken());
                assertEquals("username", logResult.getUsername());
            });
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @Test
    public void loginNegative() {
        assertThrows(Exception.class, () -> {
            userService.login(new LoginRequest("user", "pass"));
        });
    }

    @Test
    public void logoutPositive() {
        try {
            assertDoesNotThrow(() -> {
                LoginResult result = userService.register(new RegisterRequest("username", "password", "email"));
                assertNotNull(result.getAuthToken());
                assertEquals("username", result.getUsername());
                userService.logout(result.getAuthToken());
            });
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @Test
    public void logoutNegative() {
        assertThrows(Exception.class, () -> {
            userService.logout(null);
        });
    }


}



//
//
//    @Test
//    public void listGamesPositive() {
//        try {
//            assertDoesNotThrow(() -> {
//                LoginResult result = userService.register(new RegisterRequest("username", "password", "email"));
//                String authToken = result.getAuthToken();
//                gameService.listGames(authToken);
//            });
//        } catch (Exception ex) {
//            throw new RuntimeException(ex.toString());
//        }
//    }
//
//    @Test
//    public void listGamesNegative() {
//        assertThrows(Exception.class, () -> {
//            String fakeAuth = "thisShouldntWork";
//            gameService.listGames(fakeAuth);
//        });
//    }
//
//    @Test
//    public void createGamePositive() {
//        try {
//            assertDoesNotThrow(() -> {
//                LoginResult result = userService.register(new RegisterRequest("username", "password", "email"));
//                String authToken = result.getAuthToken();
//                gameService.createGame(authToken, new CreateGameRequest("gameName"));
//            });
//        } catch (Exception ex) {
//            throw new RuntimeException(ex.toString());
//        }
//
//    }
//
//    @Test
//    public void createGameNegative() {
//        assertThrows(Exception.class, () -> {
//            String fakeAuth = "thisShouldntWork";
//            gameService.createGame(fakeAuth, new CreateGameRequest("gameName"));
//        });
//    }
//
//    @Test
//    public void joinGamePositive() {
//        try {
//            assertDoesNotThrow(() -> {
//                LoginResult result = userService.register(new RegisterRequest("username", "password", "email"));
//                String authToken = result.getAuthToken();
//                CreateGameResult gameResult = gameService.createGame(authToken, new CreateGameRequest("gameName"));
//                gameService.joinGame(authToken, new JoinGameRequest(ChessGame.TeamColor.BLACK, gameResult.getGameID()));
//            });
//        } catch (Exception ex) {
//            throw new RuntimeException(ex.toString());
//        }
//    }
//
//    @Test
//    public void joinGameNegative() {
//        assertThrows(Exception.class, () -> {
//            LoginResult result = userService.register(new RegisterRequest("username", "password", "email"));
//            String authToken = result.getAuthToken();
//            gameService.joinGame(authToken, new JoinGameRequest(ChessGame.TeamColor.BLACK, 1));
//        });
//    }
//
//
//}
