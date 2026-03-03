package server.handlers;

import io.javalin.http.Context;
import service.*;


public class GameHandler {
    Service service;

    public GameHandler(Service service){
        this.service = service;
    }

    public createGame(Context ctx){
        service.createGame(ctx);
    }
}
