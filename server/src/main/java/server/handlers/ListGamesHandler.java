package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import exceptions.ImproperRequestException;
import io.javalin.http.Context;
import exceptions.UnauthorizedException;
import requests.AuthorizedRequest;
import requests.CreateGameRequest;
import service.GameService;

public class ListGamesHandler {
    GameService service;

    public ListGamesHandler(GameService service){
        this.service = service;
    }


    public void listGames(Context ctx) {
        try {
            AuthorizedRequest request = new Gson().fromJson(ctx.body(), AuthorizedRequest.class);
            ctx.result(service.listGames(request).toString());
        }catch (DataAccessException _){
            ctx.status(400);
            ctx.result("Data Access Error");
        }catch (UnauthorizedException e){
            ctx.status(401);
            ctx.result("Error: unauthorized");
        } catch (ImproperRequestException e) {
            ctx.status(400);
            ctx.result("Improper Request");
        }
    }


}
