package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import exceptions.ImproperRequestException;
import exceptions.UnauthorizedException;
import requests.CreateGameRequest;
import results.CreateGameResult;
import service.GameService;

import io.javalin.http.Context;

public class CreateGameHandler {
    GameService gameService;

    public CreateGameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public void createGame(Context ctx){
        try{
            String authToken = ctx.header("Authorization");
            CreateGameRequest gameRequest = new Gson().fromJson(ctx.body(), CreateGameRequest.class);
            CreateGameResult result = gameService.createGame(authToken, gameRequest);
            ctx.status(200);
            ctx.result(new Gson().toJson(result));
            ctx.contentType("application/json");
        } catch (UnauthorizedException e){
            ctx.status(401);
            ctx.result(new Gson().toJson(new ErrorResponse("Error: Unauthorized")));
            ctx.contentType("application/json");
        } catch(DataAccessException e) {
            ctx.status(500);
            ctx.result(new Gson().toJson(new ErrorResponse("Error" + e)));
            ctx.contentType("application/json");
        } catch(ImproperRequestException e){
            ctx.status(400);
            ctx.result(new Gson().toJson(new ErrorResponse("Error: Misformatted Request")));
            ctx.contentType("application/json");
        }
    }
}
