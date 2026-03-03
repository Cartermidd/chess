package server.handlers;

import dataaccess.DataAccessException;
import io.javalin.http.Context;
import exceptions.UnauthorizedException;
import service.GameService;

public class ListGamesHandler {
    GameService service;

    public ListGamesHandler(GameService service){
        this.service = service;
    }


    public void listGames(Context ctx) {
        try {
            ctx.result(service.listGames().toString());
        }catch (DataAccessException _){
            ctx.status(400);
            ctx.result("Data Access Error");
        }catch (UnauthorizedException e){
            ctx.status(401);
            ctx.result("Error: unauthorized");
        }
    }


}
