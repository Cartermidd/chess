package server;

import com.google.gson.*;
import dataaccess.DataAccessException;
import io.javalin.*;
import dataaccess.MemoryDataAccess;
import io.javalin.http.Context;
import server.handlers.*;
import service.*;

public class Server {
    private final UserService userService;
    private final GameService gameService;
    private final ClearService clearService;
    private final Javalin javalin;
    private final RegisterHandler RegisterHandler;
    private final LoginHandler LoginHandler;
    private final LogoutHandler LogoutHandler;
    private final ListGamesHandler ListGamesHandler;
    private final CreateGameHandler CreateGameHandler;
    private final JoinGameHandler JoinGameHandler;
    private final ClearHandler ClearHandler;


    public Server() {
        MemoryDataAccess dao = new MemoryDataAccess();

        UserService userService = new UserService(dao, dao);
        GameService gameService = new GameService(dao, dao);
        ClearService clearService = new ClearService(dao);

        this.userService = userService;
        this.gameService = gameService;
        this.clearService = clearService;
    }

    public Server(Service service) {
        this.service = service;
        this.RegisterHandler = new RegisterHandler(service);
        this.LoginHandler = new LoginHandler(service);
        this.LogoutHandler = new LogoutHandler(service);
        this.ListGamesHandler = new ListGamesHandler(service);
        this.CreateGameHandler =  new CreateGameHandler(service);
        this.JoinGameHandler = new JoinGameHandler(service);
        this.ClearHandler = new ClearHandler(service);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", RegisterHandler::register)
                .post("/session", LoginHandler::login)
                .delete("session", LogoutHandler::logout)
                .get("/game", ListGamesHandler::listGames)
                .post("/game", CreateGameHandler::createGame)
                .put("/game", JoinGameHandler::joinGame)
                .delete("/db", ClearHandler::clear);

    }

    public Server run(int desiredPort) {
        javalin.start(desiredPort);
        return this;
    }

    public int port(){
        return javalin.port();
    }

    public void stop(){
        javalin.stop();
    }



    private void createGame(Context ctx) throws DataAccessException {
        GameObject game = new Gson().fromJson(ctx.body(), GameObject.class);
        game = service.addGame(game);
        ctx.result(new Gson().toJson(game));
    }

    private void joinGame(Context ctx) throws DataAccessException {
        ctx.result(service.joinGame().toString());
    }

    private void clear(Context ctx) throws DataAccessException{
        service.clearDB();
        ctx.status(204);
    }

}
