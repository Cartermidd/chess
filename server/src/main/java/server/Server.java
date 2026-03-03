package server;

import com.google.gson.*;
import dataaccess.DataAccessException;
import io.javalin.*;
import dataaccess.MemoryDataAccess;
import io.javalin.http.Context;
import service.Service;
import server.Handlers.*;

public class Server {
    private final Service service;
    private final Javalin javalin;
    private final RegisterHandler RegisterHandler;
    private final LoginHandler LoginHandler;
    private final LogoutHandler LogoutHandler;
    private final ListGamesHandler ListGamesHandler;
    private final CreateGameHandler CreateGameHandler;
    private final JoinGameHandler JoinGameHandler;
    private final ClearHandler ClearHandler;


    public Server() { this(new Service(new MemoryDataAccess()));}

    public Server(Service service) {
        this.service = service;
        this.RegisterHandler = RegisterHandler(service);
        this.LoginHandler = LoginHandler(service);
        this.LogoutHandler = LogoutHandler(service);
        this.ListGamesHandler = ListGamesHandler(service);
        this.CreateGameHandler = CreateGameHandler(service);
        this.JoinGameHandler = JoinGameHandler(service);
        this.ClearHandler = ClearHandler(service);

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
