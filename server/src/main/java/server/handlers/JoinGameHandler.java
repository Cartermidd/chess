package server.handlers;

import service.GameService;
import io.javalin.http.Context;

public class JoinGameHandler {
    GameService gameService;

    public JoinGameHandler(GameService gameService) {
        this.gameService = gameService;
    }


    public void joinGame(Context ctx){
//        try{
//            ctx.
//        }catch (){
//
//        }
    }

}
