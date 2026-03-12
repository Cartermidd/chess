package server.handlers;

import exceptions.ImproperRequestException;
import models.UserData;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import exceptions.IncorrectPasswordException;
import exceptions.UserDoesNotExistException;
import org.eclipse.jetty.util.log.Log;
import requests.LoginRequest;
import results.LoginResult;
import service.UserService;


public class LoginHandler {
    UserService service;

    public LoginHandler(UserService service){
        this.service = service;
    }


    public void login(Context ctx) {
        try {
            LoginRequest request = new Gson().fromJson(ctx.body(), LoginRequest.class);
            LoginResult result = service.login(request);
            ctx.status(200);
            ctx.result(new Gson().toJson(result));
            ctx.contentType("application/json");
        } catch(DataAccessException e) {
            ctx.status(500);
            ctx.result(new Gson().toJson(new ErrorResponse("Error" + e)));
            ctx.contentType("application/json");
        } catch (UserDoesNotExistException e){
            ctx.status(401);
            ctx.result(new Gson().toJson(new ErrorResponse("Error: User Does Not Exist")));
            ctx.contentType("application/json");
        } catch (IncorrectPasswordException e){
            ctx.status(401);
            ctx.result(new Gson().toJson(new ErrorResponse("Error: Incorrect Password")));
            ctx.contentType("application/json");
        } catch (ImproperRequestException e) {
            ctx.status(400);
            ctx.result(new Gson().toJson(new ErrorResponse("Error: Misformatted Request")));
            ctx.contentType("application/json");
        }
    }


}
