package server;

import com.google.gson.*;
import dataaccess.DataAccessException;
import io.javalin.*;
import dataaccess.MemoryDataAccess;
import io.javalin.http.Context;
import service.Service;
import Models.*;

import static java.lang.IO.print;

public class Server {
    private final Service service;
    private final Javalin javalin;

    public Server() { this(new Service(new MemoryDataAccess()));}

    public Server(Service service) {
        this.service = service;

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", this::register)
                .post("/session", this::login)
                .delete("session", this::logout)
                .get("/game", this::listGames)
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

//    private void exceptionHandler(DataAccessException ex, Context ctx) {
//        ctx.status(ex.toHttpStatusCode());
//        ctx.result(ex.toJson());
//    }

    private void register(Context ctx) throws DataAccessException {
        try {
            UserData user = new Gson().fromJson(ctx.body(), UserData.class);
            AuthData result = service.register(user);
            ctx.result(new Gson().toJson(result));
        } catch(Exception e) {
            print(e); //what am I actually supposed to do?
        }
        }

    private void login(Context ctx) throws DataAccessException {
        UserData user = new Gson().fromJson(ctx.body(), UserData.class);
        user = service.login(user);
        ctx.result(new Gson().toJson(user));
    }

    private void logout(Context ctx) throws DataAccessException{
        ctx.result(service.logout().toString());
    }

    private void listGames(Context ctx) throws DataAccessException {
        ctx.result(service.listGames().toString());
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
