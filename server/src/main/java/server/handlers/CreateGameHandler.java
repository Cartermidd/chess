package server.handlers;

import com.google.gson.Gson;
import requests.CreateGameRequest;
import requests.RegisterRequest;
import service.GameService;
import service.UserService;

import io.javalin.http.Context;

public class CreateGameHandler {
    GameService gameService;
    UserService userService;

    public CreateGameHandler(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    public void createGame(Context ctx){
        try{
            String authToken = ctx.header("Authorization");
            userService.findByAuth





            CreateGameRequest request = new Gson().fromJson(ctx.body(), CreateGameRequest.class);
            ctx.result(gameService.createGame(request).toString());
            ctx.status(200);
        }catch(Exception e){
            ctx.status();
            ctx.result();
        }
    }
}
