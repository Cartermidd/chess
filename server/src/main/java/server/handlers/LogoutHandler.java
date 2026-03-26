package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import exceptions.UnauthorizedException;
import results.GenericSuccessfulResult;
import service.UserService;

public class LogoutHandler {
    UserService userService;

    public LogoutHandler(UserService service){
        this.userService = service;
    }

    public void logout(Context ctx) {
        try {
            String authToken = ctx.header("Authorization");
            userService.logout(authToken);
            ctx.status(200);
            ctx.result(new Gson().toJson(new GenericSuccessfulResult()));
            ctx.contentType("application/json");
        } catch(DataAccessException e) {
            ctx.status(500);
            ctx.result(new Gson().toJson(new ErrorResponse("Error" + e)));
            ctx.contentType("application/json");
        } catch (UnauthorizedException e){
            ctx.status(401);
            ctx.result(new Gson().toJson(new ErrorResponse("Error: Unauthorized")));
            ctx.contentType("application/json");
        }
    }
}
