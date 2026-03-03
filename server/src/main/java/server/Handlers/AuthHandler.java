package server.Handlers;

import Models.UserData;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import server.exceptions.IncorrectPasswordException;
import server.exceptions.UnauthorizedException;
import server.exceptions.UserDoesNotExistException;
import service.*;


public class AuthHandler {
    Service service;

    public AuthHandler(Service service){
        this.service = service;
    }

    public void logout(Context ctx) {
        try {
            ctx.result(service.logout().toString());
        }catch (DataAccessException _){
            ctx.status(400);
            ctx.result("Data Access Error");
        } catch (UnauthorizedException e){
            ctx.status(401);
            ctx.result("Error: unauthorized");
        }
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