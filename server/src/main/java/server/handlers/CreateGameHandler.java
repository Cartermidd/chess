package server.handlers;

import com.google.gson.Gson;
import requests.CreateGameRequest;
import requests.RegisterRequest;
import service.GameService;

import io.javalin.http.Context;

public class CreateGameHandler {
    GameService gameService;

    public CreateGameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public void createGame(Context ctx){
        try{
            CreateGameRequest request = new Gson().fromJson(ctx.body(), CreateGameRequest.class);
            ctx.result(gameService.createGame(request).toString());
            ctx.status(200);
        }catch(Exception e){
            ctx.status();
            ctx.result();
        }
    }
}
