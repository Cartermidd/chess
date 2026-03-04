package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import exceptions.AlreadyTakenException;
import exceptions.ImproperRequestException;
import exceptions.NoGameException;
import exceptions.UnauthorizedException;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import results.CreateGameResult;
import results.GenericSuccessfulResult;
import service.GameService;
import io.javalin.http.Context;

public class JoinGameHandler {
    GameService gameService;

    public JoinGameHandler(GameService gameService) {
        this.gameService = gameService;
    }


    public void joinGame(Context ctx){
        try{
            String authToken = ctx.header("Authorization");
            JoinGameRequest request = new Gson().fromJson(ctx.body(), JoinGameRequest.class);
            GenericSuccessfulResult result = gameService.joinGame(authToken, request);
            ctx.status(200);
            ctx.result(new Gson().toJson(result));
            ctx.contentType("application/json");
        }catch(DataAccessException e) {
            ctx.status(400);
            ctx.result(new Gson().toJson(new ErrorResponse(e.toString())));
            ctx.contentType("application/json");
        } catch (UnauthorizedException e){
            ctx.status(401);
            ctx.result(new Gson().toJson(new ErrorResponse("Error: Unauthorized")));
            ctx.contentType("application/json");
        } catch(ImproperRequestException e){
            ctx.status(400);
            ctx.result(new Gson().toJson(new ErrorResponse("Error: Misformatted Request")));
            ctx.contentType("application/json");
        } catch(NoGameException e){
            ctx.status(400);
            ctx.result(new Gson().toJson(new ErrorResponse(e.toString())));
            ctx.contentType("application/json");
        } catch (AlreadyTakenException e){
            ctx.status(403);
            ctx.result(new Gson().toJson(new ErrorResponse(e.toString())));
            ctx.contentType("application/json");
        }
    }

}
