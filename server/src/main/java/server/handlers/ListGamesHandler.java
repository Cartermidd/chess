package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import exceptions.UnauthorizedException;
import results.ListGamesResult;
import service.GameService;

import java.util.Map;

public class ListGamesHandler {
    GameService service;

    public ListGamesHandler(GameService service){
        this.service = service;
    }


    public void listGames(Context ctx) {
        try {
            String authToken = ctx.header("Authorization");
            ListGamesResult result = service.listGames(authToken);
            ctx.status(200);
            ctx.result(new Gson().toJson(result));
            ctx.contentType("application/json");
        } catch (DataAccessException e){
            ctx.status(400);
            ctx.result("Data Access Error");
            ctx.contentType("application/json");
        }catch (UnauthorizedException e){
            ctx.status(401);
            ctx.contentType("application/json");
            ctx.result(new Gson().toJson(Map.of("message","Error: unauthorized")));
        }
    }


}
