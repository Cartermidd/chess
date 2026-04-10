package server;

import com.google.gson.*;
import dataaccess.*;
import io.javalin.*;
import server.handlers.*;
import server.websocket.WebSocketHandler;
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
    private final WebSocketHandler webSocketHandler;


    public Server() {
//        MemoryDataAccess dao = new MemoryDataAccess(); Use this line of code to run a server on local memory (not through mySQL)
        UserDAO userDAO = new MySqlDataAccess();
        GameDAO gameDAO = new MySqlDataAccess();
        AuthDAO authDAO = new MySqlDataAccess();

        this.userService = new UserService(userDAO, authDAO);
        this.gameService = new GameService(authDAO, gameDAO);
        this.clearService = new ClearService(userDAO, gameDAO, authDAO);
        this.registerHandler = new RegisterHandler(userService);
        this.loginHandler = new LoginHandler(userService);
        this.logoutHandler = new LogoutHandler(userService);
        this.listGamesHandler = new ListGamesHandler(gameService);
        this.createGameHandler =  new CreateGameHandler(gameService);
        this.joinGameHandler = new JoinGameHandler(gameService);
        this.clearHandler = new ClearHandler(clearService);
        this.webSocketHandler = new WebSocketHandler(authDAO, gameDAO);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", registerHandler::register)
                .post("/session", loginHandler::login)
                .delete("session", logoutHandler::logout)
                .get("/game", listGamesHandler::listGames)
                .post("/game", createGameHandler::createGame)
                .put("/game", joinGameHandler::joinGame)
                .delete("/db", clearHandler::clear)
                .ws("/ws", ws -> {
                    ws.onConnect(webSocketHandler);
                    ws.onMessage(webSocketHandler);
                    ws.onClose(webSocketHandler);
                });
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
