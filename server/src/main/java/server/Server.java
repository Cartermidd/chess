package server;

import com.google.gson.*;
import io.javalin.*;
import dataaccess.MemoryDataAccess;
import server.handlers.*;
import service.*;

public class Server {
    private final UserService userService;
    private final GameService gameService;
    private final ClearService clearService;
    private final Javalin javalin;
    private final RegisterHandler registerHandler;
    private final LoginHandler loginHandler;
    private final LogoutHandler logoutHandler;
    private final ListGamesHandler listGamesHandler;
    private final CreateGameHandler createGameHandler;
    private final JoinGameHandler joinGameHandler;
    private final ClearHandler clearHandler;


    public Server() {
        MemoryDataAccess dao = new MemoryDataAccess();

        this.userService = new UserService(dao, dao);
        this.gameService = new GameService(dao, dao);
        this.clearService = new ClearService(dao, dao, dao);
        this.registerHandler = new RegisterHandler(userService);
        this.loginHandler = new LoginHandler(userService);
        this.logoutHandler = new LogoutHandler(userService);
        this.listGamesHandler = new ListGamesHandler(gameService);
        this.createGameHandler =  new CreateGameHandler(gameService);
        this.joinGameHandler = new JoinGameHandler(gameService);
        this.clearHandler = new ClearHandler(clearService);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", registerHandler::register)
                .post("/session", loginHandler::login)
                .delete("session", logoutHandler::logout)
                .get("/game", listGamesHandler::listGames)
                .post("/game", createGameHandler::createGame)
                .put("/game", joinGameHandler::joinGame)
                .delete("/db", clearHandler::clear);

    }

    public Server(UserService userService, GameService gameService, ClearService clearService) {
        this.userService = userService;
        this.gameService = gameService;
        this.clearService = clearService;
        this.registerHandler = new RegisterHandler(userService);
        this.loginHandler = new LoginHandler(userService);
        this.logoutHandler = new LogoutHandler(userService);
        this.listGamesHandler = new ListGamesHandler(gameService);
        this.createGameHandler =  new CreateGameHandler(gameService);
        this.joinGameHandler = new JoinGameHandler(gameService);
        this.clearHandler = new ClearHandler(clearService);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", registerHandler::register)
                .post("/session", loginHandler::login)
                .delete("/session", logoutHandler::logout)
                .get("/game", listGamesHandler::listGames)
                .post("/game", createGameHandler::createGame)
                .put("/game", joinGameHandler::joinGame)
                .delete("/db", clearHandler::clear);

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return this.port();
    }

    public void stop(){
        javalin.stop();
    }

    public int port() {
        return javalin.port();
    }
}
