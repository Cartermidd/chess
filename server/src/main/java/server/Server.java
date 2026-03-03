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
    private final UserHandler UserHandler;
    private final AuthHandler AuthHandler;
    private final GameHandler GameHandler;

    public Server() { this(new Service(new MemoryDataAccess()));}

    public Server(Service service) {
        this.service = service;
        this.UserHandler = new UserHandler(service);
        this.AuthHandler = new AuthHandler(service);
        this.GameHandler = new GameHandler(service);


        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", UserHandler::register)
                .post("/session", UserHandler::login)
                .delete("session", AuthHandler::logout)
                .get("/game", AuthHandler::listGames)
                .post("/game", this::createGame)
                .put("/game", this::joinGame)
                .delete("/db", this::clear);

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
