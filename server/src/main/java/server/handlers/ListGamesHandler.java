package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import exceptions.ImproperRequestException;
import io.javalin.http.Context;
import exceptions.UnauthorizedException;
import requests.AuthorizedRequest;
import requests.CreateGameRequest;
import results.GenericSuccessfulResult;
import results.ListGamesResult;
import service.GameService;

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
            ctx.result(new Gson().toJson(new GenericSuccessfulResult()));
            ctx.contentType("application/json");
        }catch (DataAccessException _){
            ctx.status(400);
            ctx.result("Data Access Error");
        }catch (UnauthorizedException e){
            ctx.status(401);
            ctx.result("Error: unauthorized");
        }
    }


}
