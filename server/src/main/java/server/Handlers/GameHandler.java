package server.Handlers;

import Models.UserData;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import server.exceptions.IncorrectPasswordException;
import server.exceptions.UserDoesNotExistException;
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
