package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import exceptions.UnauthorizedException;
import requests.AuthorizedRequest;
import requests.RegisterRequest;
import results.GenericSuccessfulResult;
import service.UserService;

public class LogoutHandler {
    UserService userService;

    public LogoutHandler(UserService service){
        this.userService = service;
    }

    public void logout(Context ctx) {
        try {
            AuthorizedRequest auth = new Gson().fromJson(ctx.body(), AuthorizedRequest.class);
            ctx.result(userService.logout(auth).toString());
        }catch (DataAccessException _){
            ctx.status(400);
            ctx.result("Data Access Error");
        } catch (UnauthorizedException e){
            ctx.status(401);
            ctx.result("Error: unauthorized");
        }
    }
}
